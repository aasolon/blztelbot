package com.rtm.blztelbot.dao;

import com.rtm.blztelbot.model.TurnInfoRaw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class Civ6Dao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertNewTurnInfoRaw(String gameName, String playerName, int turnNumber, LocalDateTime startDate) {
        jdbcTemplate.update("insert into CIV_TURNINFO_RAW(" +
                        "GAME_NAME, PLAYER_NAME, TURN_NUMBER, START_DATE) " +
                        "values (?, ?, ?, ?)",
                gameName, playerName, turnNumber, startDate);
    }

    public TurnInfoRaw findLastTurnInfoRaw(String gameName) {
        try {
            return jdbcTemplate.queryForObject(
                    "select ID, END_DATE from CIV_TURNINFO_RAW " +
                            "where GAME_NAME = ? and ID = (select max(ID) from CIV_TURNINFO_RAW)",
                    new Object[]{gameName},
                    (rs, rowNum) -> {
                        LocalDateTime endDate = rs.getObject(2, LocalDateTime.class);
                        return new TurnInfoRaw(rs.getLong(1), endDate);
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void updateTurnInfoRawEndDate(long id, LocalDateTime endDate) {
        jdbcTemplate.update("update CIV_TURNINFO_RAW set END_DATE = ? where ID = ?",
                endDate, id);
    }

    public Long getGameId(String gameName) {
        return jdbcTemplate.queryForObject("select NAME from CIV_GAMEINFO where NAME = ?",
                new Object[]{gameName}, Long.class);
    }

    public Long getPlayerId(String playerName) {
        return jdbcTemplate.queryForObject("select NAME from CIV_PLAYERINFO where NAME = ?",
                new Object[]{playerName}, Long.class);
    }

    public int getTurnOrder(long gameId, long playerId) {
        return jdbcTemplate.queryForObject("select TURN_ORDER from CIV_GAMEPLAYER where GAME_ID = ? and PLAYER_ID = ?",
                new Object[]{gameId, playerId},
                int.class);
    }

    public void findPreviousTurnInfo() {
//        jdbcTemplate.queryForObject(
//                "select * from CIV_TURNINFO where GAME_ID = ? and TURN_NUMBER = ? and " +
//                        "PLAYER_ID = (select PLAYER_ID from CIV_GAMEPLAYER where GAME_ID = ? and " +
//                        "TURN_ORDER = (case when ? = 1 then )"
//        );
    }
}
