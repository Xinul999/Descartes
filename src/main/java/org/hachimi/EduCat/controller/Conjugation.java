package org.hachimi.EduCat.controller;

import org.hachimi.EduCat.Exceptions.InformationsException;
import org.hachimi.EduCat.repository.DataServiceConjugaison;
import org.hachimi.EduCat.service.ConjugaisonService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Conjugation {

    private final ConjugaisonService conjugaisonService;
    @Autowired
    public Conjugation(ConjugaisonService conjugaisonService){
        this.conjugaisonService= conjugaisonService;
    }

    @PostMapping(path = "/conjugation")
    public String conjugaison(@RequestBody String body){
        JSONObject ret = new JSONObject();
        JSONObject json_body = new JSONObject(body);
        try{
            String group;
            try{
                group = json_body.getString("group");
                String verb = conjugaisonService.generateWord(group,  "present");
                ret.put("verb" , verb);

            }catch (JSONException e){
                throw new InformationsException();
            }
        }catch (Exception e){
            ret.put("error", e.getMessage());
        }
        return  ret.toString();
    }
}
