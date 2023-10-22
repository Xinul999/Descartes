package org.hachimi.EduCat.controller;

import org.hachimi.EduCat.Entity.User;
import org.hachimi.EduCat.service.DataService;
import org.hachimi.EduCat.service.JWTService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogSign {
    private DataService dataService;

    public LogSign(DataService dataService){
        this.dataService = dataService;
    }

    @PostMapping(path = "/login")
    public String login(@RequestBody String body){
        JSONObject json_body = new JSONObject(body);
        String user_name = json_body.getString("user_name");
        String password = json_body.getString("password");

        JSONObject payloadUser = new JSONObject();
        JSONObject ret = new JSONObject();
        JSONObject json_user_data = dataService.getUser(user_name, password);
        if(!json_user_data.isEmpty()){
            String idUser = json_user_data.getString("IdUser");
            payloadUser.put("id", idUser);
            String jws = JWTService.generateJWT(payloadUser);
            ret.put("jws" , jws);
        }else {
            ret.put("error", "USER NOT FOUND");
        }

        return ret.toString();
    }

    @PostMapping(path = "/signin")
    public  String signin(@RequestBody String body){
        JSONObject ret = new JSONObject();
        JSONObject json_body = new JSONObject(body);

        try{
            User user = new User(json_body);
            ret = dataService.insertUser(new User(json_body));
        }catch (JSONException e){
            ret.put("error", "Request body don't contain all infos");
        }


        return ret.toString();
    }
}
