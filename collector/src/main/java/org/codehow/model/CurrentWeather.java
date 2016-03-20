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

    /*
    Klokken
    Datoprivate
    Temperaturer
      - nå
      - max
      - min
    Vindstyrke
      - nå
      - max
    Vindkast
    Vindretning
    Luftfuktighet
    Lufttrykk

     */


}
