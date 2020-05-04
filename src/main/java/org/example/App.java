package org.example;

import java.sql.SQLException;

public class App
{
    public static void main( String[] args )
    {
        try {
            CityDao cityDao = new CityDaoJDBC(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
