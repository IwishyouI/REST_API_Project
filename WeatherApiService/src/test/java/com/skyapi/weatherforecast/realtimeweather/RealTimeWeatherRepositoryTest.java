package com.skyapi.weatherforecast.realtimeweather;


import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealTimeWeather;
import com.skyapi.weatherforecast.realtime.RealTimeWeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RealTimeWeatherRepositoryTest {

    @Autowired
    private RealTimeWeatherRepository repo;


    @Test
    public void createRealTimeWeather() {


        String locationCode = "NYC_USA";

        RealTimeWeather realTimeWeather = repo.findById(locationCode).get();

        realTimeWeather.setTemperature(-2);
        realTimeWeather.setHumidity(32);
        realTimeWeather.setPrecipitation(42);
        realTimeWeather.setStatus("Snowy");
        realTimeWeather.setWindSpeed(12);
        realTimeWeather.setLast_updated(new Date());

        RealTimeWeather updatedRealtimeWeather = repo.save(realTimeWeather);
        assertThat(updatedRealtimeWeather.getHumidity()).isEqualTo(32);
    }

}
