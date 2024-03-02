package com.skyapi.weatherforecast.hourly;

import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.HourlyWeatherId;
import com.skyapi.weatherforecast.common.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class HourlyWeatherRepositoryTests {


    @Autowired
    private HourlyWeatherRepository repo;


    @Test
    public void testAdd() {
        String locationCode = "DELHI_IN";
        int hourOfDay = 12;

        Location location = new Location().code(locationCode);

        HourlyWeather forecast1 = new HourlyWeather()
                .location(location)
                .hourOfDay(hourOfDay)
                .temperature(13)
                .precipitation(70)
                .status("Cloudy");

        HourlyWeather updatedForecast = repo.save(forecast1);

        assertThat(updatedForecast.getId().getHourOfDay()).isEqualTo(hourOfDay);
    }

    @Test
    public void testDelete() {
        Location location = new Location().code("DELHI_IN");

        HourlyWeatherId id = new HourlyWeatherId(0, location);

        repo.deleteById(id);

        Optional<HourlyWeather> result = repo.findById(id);
        assertThat(result).isNotPresent();
    }

    @Test
    public void findByLocationCodeFound() {


        String locationCode = "DELHI_IN";
        int currentHour = 10;

        List<HourlyWeather> hourlyForecast = repo.findByLocationCode(locationCode, currentHour);

        assertThat(hourlyForecast).isNotEmpty();
    }

    @Test
    public void findByLocationCodeNotFound() {
        String locationCode = "MBMHIN";
        int currentHour = 8;

        List<HourlyWeather> hourlyForecast = repo.findByLocationCode(locationCode, currentHour);
        System.out.println(hourlyForecast);
        assertThat(hourlyForecast).isEmpty();
    }
}
