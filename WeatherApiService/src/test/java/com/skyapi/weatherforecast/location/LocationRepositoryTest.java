package com.skyapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.skyapi.weatherforecast.common.DailyWeather;
import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.RealTimeWeather;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.skyapi.weatherforecast.common.Location;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class LocationRepositoryTest {


    @Autowired
    private LocationRepository repository;


    @Test
    public void testAddSuccess() {

        Location location = new Location();
        location.setCode("DELHI_IN");
        location.setCityName("Delhi");
        location.setRegionName("Delhi");
        location.setCountryCode("IN");
        location.setCountryName("India");
        location.setEnabled(true);
        location.setTrashed(false);
        Location savedLocation = repository.save(location);

        assertThat(savedLocation.getCode()).isEqualTo("DELHI_IN");
    }


    @Test
    @Disabled
    public void LocationListTest() {


        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United states of America");
        location.setEnabled(true);
        location.setTrashed(false);

        Location location2 = new Location();
        location2.setCode("WSC_USA");
        location2.setCityName("Wasington DC");
        location2.setRegionName("Wasington");
        location2.setCountryCode("US");
        location2.setCountryName("United states of America");
        location2.setEnabled(true);
        location2.setTrashed(false);

        List<Location> allLocations = (List<Location>) repository.saveAll(List.of(location, location2));

        assertThat(allLocations.get(1).getCode()).isEqualTo("WSC_USA");

    }


    @Test
    public void testListSuccess() {
        List<Location> locations = repository.findUntrashed();
        assertThat(locations).isNotEmpty();

        locations.forEach(System.out::println);
    }

    @Test
    public void testGetSuccess() {
        String code = "NYC_USA";
        Location location = repository.findByCode(code);
        assertThat(location.getCode()).isEqualTo("NYC_USA");
    }

    @Test
    public void testDeleteSuccess() {

        String code = "NYC_USA";
        repository.trashedByCode(code);

    }

    @Test
    public void testRealTimeWeatherRepository() {

        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United states of America");
        location.setEnabled(true);

        RealTimeWeather realTimeWeather = new RealTimeWeather();
        realTimeWeather.setHumidity(90);
        realTimeWeather.setTemperature(23);
        realTimeWeather.setPrecipitation(45);
        realTimeWeather.setWindSpeed(30);
        realTimeWeather.setStatus("Cloudy");
        realTimeWeather.setLastUpdated(new Date());

        location.setRealtimeWeather(realTimeWeather);
        realTimeWeather.setLocation(location);
        Location savedLocation = repository.save(location);

        assertThat(savedLocation.getRealtimeWeather().getHumidity()).isEqualTo(90);
    }

    @Test
    public void testRealTimeWeatherAddRepository() {
        Location location = new Location();
        location.setCode("DANA_VN");
        location.setCityName("Da nang");
        location.setCountryCode("VN");
        location.setCountryName("Vietnam");
        location.setEnabled(true);

        RealTimeWeather realTimeWeather = new RealTimeWeather();
        realTimeWeather.setHumidity(46);
        realTimeWeather.setTemperature(3);
        realTimeWeather.setPrecipitation(3);
        realTimeWeather.setWindSpeed(3);
        realTimeWeather.setStatus("Snowy");
        realTimeWeather.setLastUpdated(new Date());

        location.setRealtimeWeather(realTimeWeather);
        realTimeWeather.setLocation(location);
        Location savedLocation = repository.save(location);

        assertThat(savedLocation.getRealtimeWeather().getLocation().getCode()).isEqualTo("DANA_VN");
    }

    @Test
    public void testAddHourlyWeatherData() {

        Location location = repository.findById("DELHI_IN").get();
        List<HourlyWeather> listHourlyWeather = location.getListHourlyWeather();

        HourlyWeather forecast1 = new HourlyWeather().id(location, 10)
                .temperature(11)
                .precipitation(16)
                .status("Cloudy");

        HourlyWeather forecast2 = new HourlyWeather()
                .location(location)
                .hourOfDay(11)
                .temperature(20)
                .precipitation(60)
                .status("Cloudy");

        listHourlyWeather.add(forecast1);
        listHourlyWeather.add(forecast2);

        Location updatedLocation = repository.save(location);

        assertThat(updatedLocation.getListHourlyWeather()).isNotEmpty();

    }


    @Test
    public void testFindByCoutryCodeAndCityNameNotFound() {

        String countryCode = "UK";
        String cityName = "New York City";

        Location location = repository.findByCountryCodeAndCityName(countryCode, cityName);

        assertThat(location).isNull();

    }

    @Test
    public void testFindByCountryCodeAndCityNameFound() {

        String countryCode = "VN";
        String cityName = "Da nang";

        Location location = repository.findByCountryCodeAndCityName(countryCode, cityName);

        assertThat(location).isNotNull();
        assertThat(location.getCountryCode()).isEqualTo(countryCode);
        assertThat(location.getCityName()).isEqualTo(cityName);
    }

    @Test
    public void testAddDailyWeatherData() {
        Location location = repository.findById("DELHI_IN").get();

        List<DailyWeather> listdailyWeathers = location.getDailyWeathers();

        DailyWeather forecast1 = new DailyWeather()
                .location(location)
                .dayOfMonth(5)
                .maxTemp(42)
                .minTemp(-30)
                .month(12)
                .status("Rainy")
                .precipitation(30);

        DailyWeather forecast2 = new DailyWeather()
                .location(location)
                .dayOfMonth(17)
                .maxTemp(45)
                .minTemp(5)
                .month(7)
                .status("CLear")
                .precipitation(10);

        listdailyWeathers.add(forecast1);
        listdailyWeathers.add(forecast2);

        Location updatedLocation = repository.save(location);

        assertThat(updatedLocation.getListHourlyWeather()).isNotEmpty();

    }


    @Test
    public void testListFirstPage() {

        int pageSize = 2;
        int pageNum = 0;

        Pageable pageRequest = PageRequest.of(pageNum, pageSize);
        Page<Location> page = repository.findUntrashed(pageRequest);

        assertThat(page).size().isEqualTo(pageSize);
        page.forEach(System.out::println);
    }
}
