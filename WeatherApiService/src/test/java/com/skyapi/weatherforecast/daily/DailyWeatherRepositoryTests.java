package com.skyapi.weatherforecast.daily;

import com.skyapi.weatherforecast.common.DailyWeather;
import com.skyapi.weatherforecast.common.DailyWeatherId;
import com.skyapi.weatherforecast.common.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class DailyWeatherRepositoryTests {


    @Autowired
    private DailyWeatherRepository repo;


    @Test
    public void testAdd() {


        String locationCode = "DANA_VN";

        Location location = new Location().code(locationCode);

        DailyWeather dailyWeather = new DailyWeather();

        DailyWeather forecast1 = new DailyWeather()
                .location(location)
                .dayOfMonth(16)
                .month(7)
                .maxTemp(46)
                .minTemp(10)
                .status("Rainy")
                .precipitation(30);

        DailyWeather addedForecast = repo.save(forecast1);

        assertThat(addedForecast.getId().getLocation().getCode()).isEqualTo(locationCode);
    }


    @Test
    public void testDelete() {
        Location location = new Location().code("DANA_VN");
        DailyWeatherId id = new DailyWeatherId(16, 7, location);

        repo.deleteById(id);

        Optional<DailyWeather> byId = repo.findById(id);
        assertThat(byId).isNotPresent();

    }

    @Test
    public void repositoryTests() {

        List<DailyWeather> delhi = repo.findByLocationCode("DELHI_IN");

        System.out.println(delhi);
    }

}
