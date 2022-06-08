package com.alexllanas.jefitproject.util;

import com.alexllanas.jefitproject.features.city.City;

import java.util.ArrayList;
import java.util.Arrays;

public class StaticData {
    public static ArrayList<City> CITY_NAMES = new ArrayList<City>(
            Arrays.asList(
                    new City("San Francisco"),
                    new City("Los Angeles"),
                    new City("Montreal"),
                    new City("Chicago"),
                    new City("Milwaukee")
            )
    );
}

