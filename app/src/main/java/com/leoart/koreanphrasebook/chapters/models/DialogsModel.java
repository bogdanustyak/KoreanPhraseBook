package com.leoart.koreanphrasebook.chapters.models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by khrystyna on 11/24/16.
 */

public class DialogsModel {

    private String name;
    private HashMap<String, Boolean> replics;

    public DialogsModel() {
    }

    public HashMap<String, Boolean> getReplics() {
        return replics;
    }

    public void setReplics(HashMap<String, Boolean> replics) {
        this.replics = replics;
    }

    public DialogsModel(String name, List<Boolean> replics) {
        this.name = name;
       // this.replics = replics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
