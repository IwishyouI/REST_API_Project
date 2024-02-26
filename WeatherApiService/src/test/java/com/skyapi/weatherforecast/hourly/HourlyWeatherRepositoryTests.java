package com.skyapi.weatherforecast.hourly;

import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class HourlyWeatherRepositoryTests {


    @Autowired
    private HourlyWeatherRepository repo;


    @Test
    public void findByLocationCodeFound() {
        String locationCode = "MBMH_IN";
        int currentHour = 8;

        List<HourlyWeather> hourlyForecast = repo.findByLocationCode(locationCode, currentHour);
        System.out.println(hourlyForecast);
        assertThat(hourlyForecast).isNotEmpty();
    }
}
