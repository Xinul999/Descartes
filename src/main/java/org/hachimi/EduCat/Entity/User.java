package org.hachimi.EduCat.Entity;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String name;
    private String forename;
    private String  birthDay;
    private String email;
    private String mdp;

    public User(String name, String forename, String birthDay, String email, String mdp) {
        this.name = name;
        this.forename = forename;
        this.birthDay = birthDay;
        this.email = email;
        this.mdp = mdp;
    }

    public User(JSONObject infos) throws JSONException {
        this.name = infos.getString("user_name");
        this.forename = infos.getString("user_forename");
        this.birthDay = infos.getString("user_birthday");
        this.email = infos.getString("user_email");
        this.mdp = infos.getString("user_password");
    }

    public String getName() {
        return name;
    }

    public String getForename() {
        return forename;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }
}
