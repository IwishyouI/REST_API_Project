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

        location.setCode("DELHI_IN");
        location.setCityName("New Delhi");
        location.setRegionName("Delhi");
        location.setCountryCode("IN");
        location.setCountryName("India");
        location.setEnabled(true);
        location.setTrashed(false);
        Location savedLocation = repository.save(location);

        assertThat(savedLocation).isNotNull();
        assertThat(savedLocation.getCode()).isEqualTo("DELHI_IN");

    }


    @Test
    public void testListSuccess() {


        List<Location> locations = repository.findUntrashed();

        assertThat(locations).isNotEmpty();
        locations.forEach(System.out::println);
    }




}
