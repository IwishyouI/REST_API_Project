package com.skyapi.weatherforecast.location;


import com.skyapi.weatherforecast.common.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {


    private LocationRepository repo;

    public LocationService(LocationRepository repo) {
        this.repo = repo;
    }

    public Location add(Location location) {

        return repo.save(location);
    }

    public List<Location> list() {
        List<Location> locations =  repo.findUntrashed();
        return locations;
    }
}