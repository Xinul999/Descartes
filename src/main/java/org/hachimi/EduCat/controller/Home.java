package org.hachimi.EduCat.controller;

import netscape.javascript.JSObject;
import org.hachimi.EduCat.service.DataService;
import org.hachimi.EduCat.service.JWTService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Home {
    private final DataService dataService;

    public Home(DataService dataService){
        this.dataService = dataService;
    }

    @GetMapping(path = "/test")
    public String test(){
        return "GOAT";
    }


}