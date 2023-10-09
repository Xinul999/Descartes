package org.hachimi.EduCat.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataService {
    private final JdbcTemplate jdbcTemplate;
    public DataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> importerDonnees(){
        String sql = "SELECT * FROM infos";
        return  jdbcTemplate.queryForList(sql);
    }
}
