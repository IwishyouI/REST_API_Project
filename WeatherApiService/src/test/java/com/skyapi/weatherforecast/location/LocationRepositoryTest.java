package com.skyapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United states of America");
        location.setEnabled(true);
        location.setTrashed(false);
        Location savedLocation = repository.save(location);

        assertThat(savedLocation.getCode()).isEqualTo("NYC_USA");
    }


    @Test
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


}
