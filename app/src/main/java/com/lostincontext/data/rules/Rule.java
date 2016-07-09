package com.lostincontext.data.rules;


public class Rule {

    private final String name;
    private final FenceVM fenceVM;


    public Rule(String name, FenceVM fenceVM) {
        this.fenceVM = fenceVM;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public FenceVM getFenceVM() {
        return fenceVM;
    }

}
