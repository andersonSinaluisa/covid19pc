package com.today.covid_19puntoscriticos.Model;

public class Usuario {
    private String UID;
    private String name;
    private String email;
    private String photo_url;
    private String provider;

    public  Usuario(){

    }

    public Usuario(String UID, String name, String email, String photo_url, String provider) {
        this.UID = UID;
        this.name = name;
        this.email = email;
        this.provider=provider;
        this.photo_url = photo_url;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
