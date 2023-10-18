package org.hachimi.EduCat.controller;

import netscape.javascript.JSObject;
import org.hachimi.EduCat.service.DataService;
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
//    @GetMapping(path = "/data")
//    public String welcome(){
//        JSONArray data = dataService.getData("infos");
//        return data.toString();
//    }

    @PostMapping(path = "/login")
    public String login(@RequestBody String body){
        JSONObject json = new JSONObject(body);
        String user_name = json.getString("user_name");
        String password = json.getString("password");
        json = dataService.getUser(user_name, password);
        return json.toString();
    }

    @GetMapping(path = "/test")
    public String test(){
        return "GOAT";
    }


}