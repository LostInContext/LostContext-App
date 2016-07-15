package com.lostincontext.commons.list;


import java.util.List;

public class Section<Model> {

    private String title;
    private List<Model> models;


    public int size() {
        return models.size();
    }

    public Model get(int i) {
        return models.get(i);
    }

    public String getTitle() {
        return title;
    }


}
