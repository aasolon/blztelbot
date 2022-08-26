package com.rtm.blztelbot;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestJsoup {

    @Test
    void testJsoup() throws IOException {
        Document housePage;
        try {
            housePage = Jsoup.connect("https://www.pik.ru/search/s16/chessplan?bulk_id=9119&currentBenefit=gospodderzhka").get();
        } catch (HttpStatusException ex) {
            ex.getStatusCode();
            throw ex;
        }
        Elements flatLinks = housePage.select("a[href^=/s16/flats]");
        for (Element flatLink : flatLinks) {
            String href = flatLink.attr("href");
            processFlat(href);
        }
    }

    private void processFlat(String flatRelativePath) throws IOException {
        Document flatPage = Jsoup.connect("https://www.pik.ru" + flatRelativePath).get();
        String priceStr = flatPage.select("div[class^=styles__Price-sc]").text();
        String title = flatPage.select("h1[class*=styles__Title-sc]").text();
        String arr[] = title.split(" ", 2);
        String firstWord = arr[0];   //the
        Elements flatSpecs = flatPage.select("div[class^=styles__ListItemName-sc]");
        for (Element flatSpec : flatSpecs) {
            String text = flatSpec.text();
            Element nextElement = flatSpec.nextElementSibling();
            switch (text) {
                case "Этаж":
                    nextElement.text();
            }
        }

        int i = 0;
    }
}
