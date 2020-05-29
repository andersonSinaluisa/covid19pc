package com.today.covid_19puntoscriticos.Model;

public class Pages {
    private String id;
    private String url;
    private String description;
    private String img_url;


    public Pages(){

    }

    public Pages(String id, String url, String description, String img_url) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.img_url = img_url;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
