package org.hachimi.EduCat.service;

import org.hachimi.EduCat.Exceptions.GroupConjugationException;
import org.hachimi.EduCat.Exceptions.NoVerbFoundException;
import org.hachimi.EduCat.Exceptions.ServerException;
import org.hachimi.EduCat.Exceptions.TimeConjugationException;
import org.hachimi.EduCat.repository.DataServiceConjugaison;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ConjugaisonService {
    private final DataServiceConjugaison dataServiceConjugaison;
    ArrayList<String> times = new ArrayList<>(Arrays.asList("present", "imparfait", "futur_simple", "passe_simple"));
    ArrayList<String> groups = new ArrayList<>(Arrays.asList("premier_groupe", "second_groupe"));
    @Autowired
    public ConjugaisonService(DataServiceConjugaison dataServiceConjugaison) {
        this.dataServiceConjugaison = dataServiceConjugaison;
    }



    public String generateWord(String group, String time) throws TimeConjugationException, GroupConjugationException, ServerException, NoVerbFoundException {
        if(!VerifyTime(time)) throw new TimeConjugationException();
        if(!VerifyGroup(group)) throw new GroupConjugationException();
        String verb  = dataServiceConjugaison.get_verb(group).getString("verbe");


        return verb;
    }

    public boolean VerifyTime(String time) {
        return  times.contains(time);
    }

    public boolean VerifyGroup(String time) {
        return  groups.contains(time);
    }
}
