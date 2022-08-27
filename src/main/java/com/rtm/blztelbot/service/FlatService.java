package com.rtm.blztelbot.service;

import com.rtm.blztelbot.entity.FlatEntity;
import com.rtm.blztelbot.entity.FlatStatusEntity;
import com.rtm.blztelbot.repository.FlatRepository;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlatService {

    private static final Logger log = LoggerFactory.getLogger(FlatService.class);

    private final FlatRepository flatRepository;

    private final BlzTelBotService blzTelBotService;

    public FlatService(FlatRepository flatRepository, BlzTelBotService blzTelBotService) {
        this.flatRepository = flatRepository;
        this.blzTelBotService = blzTelBotService;
    }

    /**
     * 1) Считать из БД данные по квартирам в мапку (берем последний статус квартиры)
     * 2) Перейти по урлу каждой квартиры
     *    2.1) Если последний статус по квартире в БД "активна", а на сайте "неактивна",
     *         то пометить квартиру как "неактивна", сохранить в список changed
     *    2.2) Если последний статус по квартире в БД "неактивна", а на сайте "активна",
     *         то пометить квартиру как "активную", пропарсить данные с урла, сохранить в БД, сохранить в список changed
     *    2.3) Если последний статус по квартире в БД "активна", и на сайте "активна", то пропарсить данные с урла
     *         2.3.1) Если хоть какие-то данные по квартире изменились, то создать новый статус квартиры, сохранить в список changed
     *         2.3.2) Если никакие данные по квартире не изменились, то прекратить обработку по данной квартире
     * 3) Пропарсить данные с урла по всем квартирам, наполнив список с id квартир
     * 4) Исключить из списка с id квартир те квартиры, которые уже были считаны и обработканы в п.1 и п.2
     * 5) Для каждой квартиры, которые остались в списке, пропарсить данные с урла и создать новые записи для них в БД,
     *    сохранить в список changed
     */
    public void refreshFlats() throws IOException {
        List<FlatEntity> dbFlats = flatRepository.findAll();

        List<String> changes = new ArrayList<>();

        for (FlatEntity dbFlat : dbFlats) {
            FlatStatusEntity dbFlatLastStatus = getFlatLastStatusEntity(dbFlat);

            boolean siteActive = true;
            Document flatPage = null;
            try {
                flatPage = Jsoup.connect(dbFlat.getUrl()).get();
            } catch (HttpStatusException ex) {
                if (ex.getStatusCode() == 404) {
                    siteActive = false;
                } else {
                    throw ex;
                }
            }

            if (dbFlatLastStatus.getActive() && !siteActive) {
                saveNewNotActiveFlatStatus(dbFlat);
                changes.add("Квартира пропала с сайта: " + dbFlat.getUrl());
            } else if (!dbFlatLastStatus.getActive() && siteActive) {
                saveNewActiveFlatStatus(dbFlat, flatPage);
                changes.add("Квартира появилась на сайте: " + dbFlat.getUrl());
            } else if (dbFlatLastStatus.getActive() && siteActive) {
                boolean hasChanges = saveNewActiveFlatStatusIfChanged(dbFlat, dbFlatLastStatus, flatPage);
                if (hasChanges) {
                    changes.add("По квартире есть изменения: " + dbFlat.getUrl());
                }
            }
//            break;
        }

        List<Long> dbFlatIdsFromSite = dbFlats.stream().map(FlatEntity::getIdFromSite).collect(Collectors.toList());

        Document housePage = Jsoup.connect("https://www.pik.ru/search/s16/chessplan?bulk_id=9119&currentBenefit=gospodderzhka").get();
        String flatRelativePathBegin = "/s16/flats/";
        Elements flatLinks = housePage.select("a[href^=" + flatRelativePathBegin + "]");

        for (Element flatLink : flatLinks) {
            String flatUrl = null;
            try {
                String flatRelativePath = flatLink.attr("href");
                long flatIdFromSite = Long.parseLong(StringUtils.substringBetween(flatRelativePath, flatRelativePathBegin, "?"));
                flatUrl = "https://www.pik.ru" + flatRelativePath;
                if (!dbFlatIdsFromSite.contains(flatIdFromSite)) {
                    saveNewFlat(flatUrl, flatIdFromSite);
                    changes.add("Появилась новая квартира: " + flatUrl);
                    log.info("Квартира успешно обработана " + flatUrl);
                }
            } catch (Exception ex) {
                log.error("Ошибка при обработке квартиры " + flatUrl);
                throw ex;
            }
//            break;
        }

        blzTelBotService.sendMessageToMe(StringUtils.join(changes, "\n"));
    }

    @Transactional(readOnly = true)
    FlatStatusEntity getFlatLastStatusEntity(FlatEntity dbFlat) {
        FlatStatusEntity dbFlatLastStatus = dbFlat.getFlatStatuses().stream().max(Comparator.comparing(FlatStatusEntity::getCreateDatetime)).get();
        return dbFlatLastStatus;
    }

    @Transactional
    void saveNewNotActiveFlatStatus(FlatEntity dbFlat) {
        FlatStatusEntity newFlatStatusEntity = new FlatStatusEntity();
        newFlatStatusEntity.setFlat(dbFlat);
        newFlatStatusEntity.setActive(false);
        dbFlat.getFlatStatuses().add(newFlatStatusEntity);
        flatRepository.save(dbFlat);
    }

    @Transactional
    void saveNewActiveFlatStatus(FlatEntity dbFlat, Document flatPage) {
        String priceStr = flatPage.select("div[class^=styles__Price-sc]").text();
        long price = Long.parseLong(priceStr.replaceAll("\\D+", ""));
        Elements reserveButton = flatPage.select("div[class^=styles__SubscribeBookingButtonText]");
        boolean reserve = reserveButton.size() > 0;

        FlatStatusEntity newFlatStatusEntity = new FlatStatusEntity();
        newFlatStatusEntity.setFlat(dbFlat);
        dbFlat.getFlatStatuses().add(newFlatStatusEntity);

        newFlatStatusEntity.setActive(true);
        newFlatStatusEntity.setPrice(price);
        newFlatStatusEntity.setReserve(reserve);

        flatRepository.save(dbFlat);
    }

    @Transactional
    boolean saveNewActiveFlatStatusIfChanged(FlatEntity dbFlat, FlatStatusEntity dbFlatLastStatus, Document flatPage) {
        String priceStr = flatPage.select("div[class^=styles__Price-sc]").text();
        long price = Long.parseLong(priceStr.replaceAll("\\D+", ""));
        Elements reserveButton = flatPage.select("div[class^=styles__SubscribeBookingButtonText]");
        boolean reserve = reserveButton.size() > 0;

        if (!Objects.equals(dbFlatLastStatus.getPrice(), price) || !Objects.equals(dbFlatLastStatus.getReserve(), reserve)) {
            FlatStatusEntity newFlatStatusEntity = new FlatStatusEntity();
            newFlatStatusEntity.setFlat(dbFlat);
            dbFlat.getFlatStatuses().add(newFlatStatusEntity);

            newFlatStatusEntity.setActive(true);
            newFlatStatusEntity.setPrice(price);
            newFlatStatusEntity.setReserve(reserve);

            flatRepository.save(dbFlat);
            return true;
        }

        return false;
    }

    @Transactional
    void saveNewFlat(String flatUrl, long flatIdFromSite) throws IOException {
        FlatEntity flatEntity = new FlatEntity();
        FlatStatusEntity flatStatusEntity = new FlatStatusEntity();

        boolean active = true;
        Document flatPage = null;
        try {
            flatPage = Jsoup.connect(flatUrl).get();
        } catch (HttpStatusException ex) {
            int statusCode = ex.getStatusCode();
            if (statusCode == 404) {
                active = false;
            } else {
                throw ex;
            }
        }

        flatStatusEntity.setActive(active);

        if (active) {
            String priceStr = flatPage.select("div[class^=styles__Price-sc]").text();
            long price = Long.parseLong(priceStr.replaceAll("\\D+", ""));
            String title = flatPage.select("h1[class*=styles__Title-sc]").text();
            BigDecimal area = new BigDecimal(title.replaceAll("[^0-9.]", ""));
            String roomsNumberStr = title.split(" ", 2)[0];
            long roomsNumber;
            if ("Студия".equals(roomsNumberStr)) {
                roomsNumber = 0;
            } else {
                roomsNumber = Long.parseLong(roomsNumberStr.replaceAll("[^0-9.]", ""));
            }
            Elements reserveButton = flatPage.select("div[class^=styles__SubscribeBookingButtonText]");
            boolean reserve = reserveButton.size() > 0;

            flatEntity.setIdFromSite(flatIdFromSite);
            flatEntity.setUrl(flatUrl);
            flatEntity.setRoomsNumber(roomsNumber);
            flatEntity.setArea(area);

            flatStatusEntity.setFlat(flatEntity);
            flatEntity.getFlatStatuses().add(flatStatusEntity);
            flatStatusEntity.setPrice(price);
            flatStatusEntity.setReserve(reserve);

            Elements flatSpecs = flatPage.select("div[class^=styles__ListItemName-sc]");
            for (Element flatSpec : flatSpecs) {
                String text = flatSpec.text();
                Element nextElement = flatSpec.nextElementSibling();
                switch (text) {
                    case "Корпус" -> {
                        String corpus = nextElement.text();
                        flatEntity.setCorpus(corpus);
                    }
                    case "Этаж" -> {
                        long floor = Long.parseLong(nextElement.text().split(" ")[0]);
                        long floorMax = Long.parseLong(nextElement.text().split(" ")[2]);
                        flatEntity.setFloor(floor);
                        flatEntity.setFloorMax(floorMax);
                    }
                    case "Заселение" -> {
                        String checkIn = nextElement.text();
                        flatEntity.setCorpus(checkIn);
                    }
                    case "Номер на этаже" -> {
                        long roomOnTheFloor = Long.parseLong(nextElement.text());
                        flatEntity.setRoomOnTheFloor(roomOnTheFloor);
                    }
                    case "Секция" -> {
                        long section = Long.parseLong(nextElement.text());
                        flatEntity.setRoomOnTheFloor(section);
                    }
                    case "Тип" -> {
                        String flatType = nextElement.text();
                        flatEntity.setFlatType(flatType);
                    }
                }
            }
        } else {
            flatEntity.setIdFromSite(flatIdFromSite);
            flatEntity.setUrl(flatUrl);
        }

        flatRepository.save(flatEntity);
    }
}
