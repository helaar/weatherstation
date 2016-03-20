package org.codehow.model;

import org.codehow.collector.WeatherMarshaller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 */
public class CurrentWeather {

    private final String sampleId;
    private final LocalDateTime timestamp;
    private final double currentTemp;
    private final double currentWindSpeed;
    private final int currentWindDirection;
    private final double currentHumidity;
    private final double currentAirpressure;

    public CurrentWeather(String sampleId, LocalDateTime timestamp, double currentTemp, double currentWindSpeed,
                          int currentWindDirection, double currentHumidity, double currentAirpressure) {
        this.sampleId = sampleId;
        this.timestamp = timestamp;
        this.currentTemp = currentTemp;
        this.currentWindSpeed = currentWindSpeed;
        this.currentWindDirection = currentWindDirection;
        this.currentHumidity = currentHumidity;
        this.currentAirpressure = currentAirpressure;
    }

    public String getSampleId() {
        return sampleId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public double getCurrentWindSpeed() {
        return currentWindSpeed;
    }

    public int getCurrentWindDirection() {
        return currentWindDirection;
    }

    public double getCurrentHumidity() {
        return currentHumidity;
    }

    public double getCurrentAirpressure() {
        return currentAirpressure;
    }
}
