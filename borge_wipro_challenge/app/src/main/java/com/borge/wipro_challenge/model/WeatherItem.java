package com.borge.wipro_challenge.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherItem implements Parcelable {

    private String mainCondition;
    private String descriptionCondition;
    private Double temperature;
    private Double windSpeed;
    private float pressure;
    private int humidity;
    private String dt_txt;
    private String cityName;

    public WeatherItem (String mainCondition, String descriptionCondition, Double temperature,
                        Double windSpeed, float pressure, int humidity, String dt_txt, String cityName) {
        this.mainCondition = mainCondition;
        this.descriptionCondition = descriptionCondition;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cityName = cityName;
        this.dt_txt = dt_txt;
    }

    public String getMainCondition() {
        return mainCondition;
    }

    public String getDescriptionCondition() {
        return descriptionCondition;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public float getPressure() { return pressure; }

    public int getHumidity() {
        return humidity;
    }

    public String getDt_txt() { return dt_txt; }

    public String getCityName() {
        return cityName;
    }

    protected WeatherItem(Parcel in) {
        mainCondition = in.readString();
        descriptionCondition = in.readString();
        temperature = in.readByte() == 0x00 ? null : in.readDouble();
        windSpeed = in.readByte() == 0x00 ? null : in.readDouble();
        pressure = in.readFloat();
        humidity = in.readInt();
        dt_txt = in.readString();
        cityName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mainCondition);
        dest.writeString(descriptionCondition);
        if (temperature == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(temperature);
        }
        if (windSpeed == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(windSpeed);
        }
        dest.writeFloat(pressure);
        dest.writeInt(humidity);
        dest.writeString(dt_txt);
        dest.writeString(cityName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WeatherItem> CREATOR = new Parcelable.Creator<WeatherItem>() {
        @Override
        public WeatherItem createFromParcel(Parcel in) {
            return new WeatherItem(in);
        }

        @Override
        public WeatherItem[] newArray(int size) {
            return new WeatherItem[size];
        }
    };
}