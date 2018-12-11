package com.wmjulio.projetoandroid2.model;

import java.util.HashMap;
import java.util.Map;

public class Obj {

    private String id;
    private String nome;
    private String desc;
    private String lat;
    private String lon;


    public Obj() {
    }

    public Obj(String nome, String desc, String lat, String lon) {
        this.nome = nome;
        this.desc = desc;
        this.lat = lat;
        this.lon = lon;
    }

    public Obj(String id, String nome, String desc, String lat, String lon) {
        this.id = id;
        this.nome = nome;
        this.desc = desc;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", this.id);
        result.put("nome", this.nome);
        result.put("desc", this.desc);
        result.put("lat", this.lat);
        result.put("lon", this.lon);

        return result;
    }

    public String toString(){
        return "ID: "+this.id+" Nome: "+this.nome+" Desc: "+this.desc+" Lat: "+this.lat+" Lon "+this.lon;
    }

}
