package com.rtm.blztelbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Civ6Service {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getInfo() {
        Map<String, Object> select_x_from_test = jdbcTemplate.queryForMap("select x from test");
        return (String) select_x_from_test.get("x");
    }
}
