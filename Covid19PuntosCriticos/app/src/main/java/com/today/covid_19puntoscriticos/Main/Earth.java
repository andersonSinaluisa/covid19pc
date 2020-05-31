package com.today.covid_19puntoscriticos.Main;

public class Earth {
    private  String date;
    private  String id;
    private  String url;

    public Earth() {}

    public Earth(String date, String id, String dataset, String earth) {
        this.date = date;
        this.id = id;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
