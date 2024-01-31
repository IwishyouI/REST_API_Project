package com.skyapi.weatherforecast.common;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {

}
