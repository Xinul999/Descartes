package org.hachimi.EduCat.controller;

import netscape.javascript.JSObject;
import org.hachimi.EduCat.service.DataService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class Home {
    private final DataService dataService;

    public Home(DataService dataService){
        this.dataService = dataService;
    }
    @GetMapping(path = "/data")
    public String welcome(){
        List<Map<String, Object>> data = dataService.importerDonnees();
        return new JSONArray(data).toString();
    }


}
