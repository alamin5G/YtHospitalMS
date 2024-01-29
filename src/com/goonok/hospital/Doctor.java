package com.goonok.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }



    public void viewDoctors(){
        String query = "select * from doctors";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("============+=============================+==========+================");
            System.out.println("|Doctors ID | Name                        | Specialization            |");
            System.out.println("============+=============================+==========+================");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("gender");
                System.out.printf("|%-12s|%-20s|%-18s\n", id, name, specialization);
                System.out.println("============+=============================+==========+================");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getDoctorById(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (SQLException sqlException){
            throw new RuntimeException();
        }

    }

}
