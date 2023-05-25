package com.codeup.codeupspringblog.models;

public class Coffee {
    private String origin;
    private String roast;
    private String name;


    public Coffee() {}
    public Coffee(String roast){
        this.roast = roast;
    }
    public Coffee(String origin, String roast) {
        this.origin = origin;
        this.roast = roast;
    }
    public Coffee(String origin, String roast, String name) {
        this.origin = origin;
        this.roast = roast;
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }
    public String getRoast() {
        return roast;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public void setRoast(String roast) {
        this.roast = roast;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
