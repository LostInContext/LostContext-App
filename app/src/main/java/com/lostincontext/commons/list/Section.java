package com.lostincontext.commons.list;


import java.util.List;

public class Section<Model> {


    public Section(String title, List<Model> models) {
        this.title = title;
        this.models = models;
    }

    private String title;
    private List<Model> models;


    public int size() {
        return models.size() + 1;
    }

    public Model get(int i) {
        return models.get(i);
    }

    public String getTitle() {
        return title;
    }


}
