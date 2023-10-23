package org.hachimi.EduCat.Entity;

import org.hachimi.EduCat.Exceptions.InformationsException;
import org.hachimi.EduCat.Exceptions.MailFormatException;
import org.hachimi.EduCat.Exceptions.ServerException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public User(JSONObject infos) throws Exception {
        try{
            this.name = infos.getString("user_name");
            this.forename = infos.getString("user_forename");
            this.birthDay = infos.getString("user_birthday");
            this.email = infos.getString("user_email");
            this.mdp = infos.getString("user_password");
        }catch (JSONException e) {
            throw new ServerException();
        }
        if (this.name == "" || this.forename == "" || this.birthDay == "" || this.email == "" ||
                this.mdp == "" ){
            throw new InformationsException();
        }

        if(!isValidEmail(this.email)){
            throw new MailFormatException();
        }
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+[.][A-Za-z]{2,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
