package com.goonok.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner input;

    public Patient(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    public void addPatient(){
        System.out.print("Enter Patient Name: ");
        String name = input.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = input.nextInt();
        input.nextLine();
        System.out.print("Enter Patient Gender: ");
        String gender = input.nextLine();

        try{
            String sql = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow>0){
                System.out.println("Patient Added Successfully");
            }else {
                System.out.println("Failed to add new Patient");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewPatient(){
        String query = "select * from patients";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("============+=============================+==========+================");
            System.out.println("|Patient ID | Name                        | Age      | Gender         |");
            System.out.println("============+=============================+==========+================");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-12s|%-20s|%-10s|%-12s\n", id, name, age, gender);
                System.out.println("============+=============================+==========+================");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";

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
