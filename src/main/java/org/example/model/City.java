package org.example.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class City {
    //names and stuff, you know
    public int id = -1;
    public String name;
    public String countryCode;
    public String district;
    public int population;

    public City(int id, String name, String countryCode, String district, int population) {
        this(name, countryCode, district, population);
        if (id < 1) throw new IllegalArgumentException("Illegal Id");
        this.id = id;
    }

    public City(String name, String countryCode, String district, int population) {
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }

    public static City constructCity(ResultSet resultSet) throws SQLException {
        return new City(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("countryCode"),
                resultSet.getString("district"),
                resultSet.getInt("population"));
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                '}';
    }
}
