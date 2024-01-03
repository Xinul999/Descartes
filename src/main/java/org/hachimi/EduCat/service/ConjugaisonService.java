package org.hachimi.EduCat.service;

import org.hachimi.EduCat.Exceptions.GroupConjugationException;
import org.hachimi.EduCat.Exceptions.NoVerbFoundException;
import org.hachimi.EduCat.Exceptions.ServerException;
import org.hachimi.EduCat.Exceptions.TimeConjugationException;
import org.hachimi.EduCat.repository.DataServiceConjugaison;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ConjugaisonService {
    private final DataServiceConjugaison dataServiceConjugaison;
    private final double[] time_easy_probablities = {0.60, 0.30, 0.07, 0.03};
    private final double[] time_medium_probablities = {0.25, 0.25, 0.25, 0.25};
    private final double[] time_hard_probablities = {0.05, 0.10, 0.35, 0.50};
    ArrayList<String> times = new ArrayList<>(Arrays.asList("present", "imparfait", "futur_simple", "passe_simple"));
    ArrayList<String> groups = new ArrayList<>(Arrays.asList("premier_groupe", "second_groupe"));
    ArrayList<String> pronoms = new ArrayList<>(Arrays.asList("je", "tu", "il/elle", "nous", "vous", "ils/elles"));
    @Autowired
    public ConjugaisonService(DataServiceConjugaison dataServiceConjugaison) {
        this.dataServiceConjugaison = dataServiceConjugaison;
    }


    public JSONObject generateVerb() throws TimeConjugationException, GroupConjugationException, ServerException, NoVerbFoundException {
        Random random = new Random();
        int random_group = random.nextInt(groups.size());
        String group = groups.get(random_group);

        String verb  = dataServiceConjugaison.get_verb(group).getString("verbe");
        JSONObject ret = new JSONObject();
        ret.put("verb", verb);


        int random_pronom = random.nextInt(pronoms.size());
        String pronom = pronoms.get(random_pronom);
        String time = getRandomElementWithProbabilities(times,time_easy_probablities);
        String conjuged_verb = conjugVerb(pronom, verb, time, group);

        if(!VerifyTime(time)) throw new TimeConjugationException(time);
        if(!VerifyGroup(group)) throw new GroupConjugationException(group);


        if(verb.startsWith("e") || verb.startsWith("a") || verb.startsWith("i") || verb.startsWith("u")
        || verb.startsWith("o") || verb.startsWith("é") || verb.startsWith("è")){
            if(pronom == "je") pronom = "j'";
        };
        ret.put("pronom", pronom);
        ret.put("time", time);
        ret.put("conjuged_verb", conjuged_verb);
        return ret;
    }

    public String conjugVerb(String pronom  ,String verb,String time,String  group){
        ArrayList<String> times = this.times;
        if(time.equals(times.get(0))) return  conjugVerbPresent(pronom, verb, group);
        if(time.equals(times.get(1))) return  conjugVerbImparfait(pronom, verb, group);
        if(time.equals(times.get(2))) return  conjugVerbFuturSimple(pronom, verb, group);
        if(time.equals(times.get(3))) return  conjugVerbPasseSimple(pronom, verb, group);
        return null;
    }

    public String conjugVerbPresent(String pronom , String verb,String  group){
        String conjugaison = null;
        System.out.println(group);
        if(group.equals(groups.get(0))){
            String radical = verb.substring(0, verb.length() - 2);
            String radical_y_replaced = radical;
            String terminaison = radical.substring(radical.length()- 2);
            if(terminaison.equals("oy") || terminaison.equals("uy") || terminaison.equals("ay")) radical_y_replaced = radical.substring(0, radical.length()- 1) + "i";
            conjugaison = switch (pronom) {
                case "je", "il/elle" -> radical_y_replaced + "e";
                case "tu" -> radical_y_replaced + "es";
                case "nous" -> radical + "ons";
                case "vous" -> radical + "ez";
                case "ils/elles" -> radical_y_replaced + "ent";
                default -> conjugaison;
            };
        }
        else if (group.equals(groups.get(1))) {
            String radical = verb.substring(0, verb.length() - 2);
            conjugaison = switch (pronom) {
                case "je", "tu" -> radical + "is";
                case "il/elle"-> radical + "it";
                case "nous" ->radical + "issons";
                case "vous"-> radical + "issez";
                case "ils/elles" -> radical + "issent";
                default -> conjugaison;
            };
        }
        return conjugaison;
    }

    public String conjugVerbImparfait(String pronom , String verb,String  group){
        String conjugaison = null;
        System.out.println(group);
        if(group.equals(groups.get(0))){
            String radical = verb.substring(0, verb.length() - 2);
            conjugaison = switch (pronom) {
                case "je", "tu" -> radical + "ais";
                case "il/elle" -> radical + "ait";
                case "nous" -> radical + "ions";
                case "vous" -> radical + "iez";
                case "ils/elles" -> radical + "aient";
                default -> conjugaison;
            };
        }
        else if (group.equals(groups.get(1))) {
            String radical = verb.substring(0, verb.length() - 2);
            conjugaison = switch (pronom) {
                case "je", "tu" -> radical + "issais";
                case "il/elle"-> radical + "issait";
                case "nous" ->radical + "issions";
                case "vous"-> radical + "issiez";
                case "ils/elles" -> radical + "issaient";
                default -> conjugaison;
            };
        }
        return conjugaison;
    }

    public String conjugVerbFuturSimple(String pronom , String verb,String  group){
        //ne marche pas pour certains mots : https://www.toutelaconjugaison.com/lecon-conjugaison-indicatif.futur.simple.html

        String conjugaison = null;
        System.out.println(group);
        if(group.equals(groups.get(0))){
            String radical = verb.substring(0, verb.length() - 2);
            conjugaison = switch (pronom) {
                case "je" -> radical + "erai";
                case "tu"-> radical + "eras";
                case "il/elle" -> radical + "era";
                case "nous" -> radical + "erons";
                case "vous" -> radical + "erez";
                case "ils/elles" -> radical + "eront";
                default -> conjugaison;
            };
        }
        else if (group.equals(groups.get(1))) {
            String radical = verb.substring(0, verb.length() - 2);
            conjugaison = switch (pronom) {
                case "je" -> radical + "irai";
                case  "tu" -> radical + "iras";
                case "il/elle"-> radical + "ira";
                case "nous" ->radical + "irons";
                case "vous"-> radical + "irez";
                case "ils/elles" -> radical + "iront";
                default -> conjugaison;
            };
        }
        return conjugaison;
    }

    public String conjugVerbPasseSimple(String pronom , String verb,String  group){
        String conjugaison = null;
        System.out.println(group);
        if(group.equals(groups.get(0))){
            String radical = verb.substring(0, verb.length() - 2);
            String radical_corrected = radical;
            if (radical.endsWith("g"))  radical_corrected = radical_corrected + 'e';
            if (radical.endsWith("c"))  radical_corrected = radical_corrected.substring(0, radical_corrected.length()-1) + 'ç';
            conjugaison = switch (pronom) {
                case "je" -> radical_corrected + "ai";
                case "tu"-> radical_corrected + "as";
                case "il/elle" -> radical_corrected + "a";
                case "nous" -> radical_corrected + "âmes";
                case "vous" -> radical_corrected + "âtes";
                case "ils/elles" -> radical + "èrent";
                default -> conjugaison;
            };
        }
        else if (group.equals(groups.get(1))) {
            String radical = verb.substring(0, verb.length() - 2);
            conjugaison = switch (pronom) {
                case "je", "tu" -> radical + "is";
                case "il/elle"-> radical + "it";
                case "nous" ->radical + "îmes";
                case "vous"-> radical + "îtes";
                case "ils/elles" -> radical + "irent";
                default -> conjugaison;
            };
        }
        return conjugaison;
    }




    public boolean VerifyTime(String time) {
        return  times.contains(time);
    }

    public boolean VerifyGroup(String time) {
        return  groups.contains(time);
    }

    public static String getRandomElementWithProbabilities(ArrayList<String> elements, double[] probabilities) {
        Random random = new Random();
        double randomValue = random.nextDouble(); // Valeur entre 0.0 et 1.0

        double cumulativeProbability = 0.0;
        for (int i = 0; i < elements.size(); i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return elements.get(i);
            }
        }
        return elements.get(elements.size() - 1);
    }

}
