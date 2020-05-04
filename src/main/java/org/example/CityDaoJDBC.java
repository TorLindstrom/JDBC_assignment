package org.example;

import org.example.model.City;
import static org.example.model.City.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CityDaoJDBC implements CityDao{

    Connection connection;

    public CityDaoJDBC(boolean useLogin) throws SQLException {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("AccessInfo"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (useLogin) {
            connection = DriverManager.getConnection(properties.getProperty("address"), properties.getProperty("user"), properties.getProperty("password"));
        } else {
            connection = DriverManager.getConnection(properties.getProperty("address"));
        }
    }

    //implement
    @Override
    public City findById(int id){
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from world.city WHERE id = ?")){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return constructCity(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> cityList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from world.city WHERE code = ?")){
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cityList.add(constructCity(resultSet));
            }
            return cityList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> cityList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from world.city WHERE name = ?")){
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cityList.add(constructCity(resultSet));
            }
            return cityList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<City> findAll() {
        List<City> cityList = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * from world.city");
            while (resultSet.next()){
                cityList.add(constructCity(resultSet));
            }
            return cityList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public City add(City city) {
        if (city.id != -1) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO world.city VALUES (?, ?, ?, ?, ?)")) {
                preparedStatement.setInt(1, city.id);
                preparedStatement.setString(2, city.name);
                preparedStatement.setString(3, city.countryCode);
                preparedStatement.setString(4, city.district);
                preparedStatement.setInt(5, city.population);
                preparedStatement.executeUpdate();
                return city;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO world.city (NAME, COUNTRYCODE, DISTRICT, POPULATION) VALUES (?, ?, ?, ?)")) {
                preparedStatement.setString(1, city.name);
                preparedStatement.setString(2, city.countryCode);
                preparedStatement.setString(3, city.district);
                preparedStatement.setInt(4, city.population);
                preparedStatement.executeUpdate();
                return city;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public City update(City city) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE world.city SET name = ?, countryCode = ?, district = ?, population = ? WHERE id = ?")){
            preparedStatement.setString(1, city.name);
            preparedStatement.setString(2, city.countryCode);
            preparedStatement.setString(3, city.district);
            preparedStatement.setInt(4, city.population);
            preparedStatement.setInt(5, city.id);
            int rowsAffected = preparedStatement.executeUpdate();
            return city;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(City city) {
        if (city.id != -1) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM world.city WHERE ID = ?")) {
                preparedStatement.setInt(1, city.id);
                return preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM world.city WHERE NAME = ?")) {
                preparedStatement.setString(1, city.name);
                return preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }
}
