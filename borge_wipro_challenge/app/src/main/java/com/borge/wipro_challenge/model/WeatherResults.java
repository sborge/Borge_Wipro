package com.borge.wipro_challenge.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResults {

    @SerializedName("list")
    @Expose
    private List<com.borge.wipro_challenge.model.List> list = null;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public List<com.borge.wipro_challenge.model.List> getList() {return list; }

    public void setList(List<com.borge.wipro_challenge.model.List> list) {this.list = list; }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
