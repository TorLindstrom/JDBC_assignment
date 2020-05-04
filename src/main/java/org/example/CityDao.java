package org.example;

import org.example.model.City;

import java.sql.SQLException;
import java.util.List;

public interface CityDao {
    City findById(int id) throws SQLException;
    List<City> findByCode(String code) throws SQLException;
    List<City> findByName(String name) throws SQLException;
    List<City> findAll() throws SQLException;
    City add(City city) throws SQLException;
    City update(City city) throws SQLException;
    int delete(City city) throws SQLException;

}
