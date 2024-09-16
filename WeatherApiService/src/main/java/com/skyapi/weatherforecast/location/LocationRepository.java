package com.skyapi.weatherforecast.location;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.skyapi.weatherforecast.common.Location;




public interface LocationRepository extends CrudRepository<Location, String>{




}
