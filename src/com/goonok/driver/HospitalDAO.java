package com.goonok.driver;

import com.goonok.hospital.Doctor;
import com.goonok.hospital.Patient;

import java.sql.*;
import java.util.Scanner;

public class HospitalDAO {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "252646";

    public HospitalDAO(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void databaseConnect(){

        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while (true){
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("==================================");
                System.out.print("Enter your choice: ");
                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                switch (choice){
                    case 1:
                        //Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //View Patient;
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        //view doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //book appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        //exit;
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice4s");
                        break;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        System.out.print("Enter patient id: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter doctor id: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter appointment date: (yyyy-mm-dd): ");
        String appointmentDate = scanner.nextLine();
        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if (checkDoctorAvailability(doctorId, appointmentDate, connection)){
                String appointmentsQuery = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentsQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0 ){
                        System.out.println("Appointment Booked");
                    }else {
                        System.out.println("Failed to book appointment");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else {
                System.out.println("Doctor not available on this date!");
            }
        }else {
            System.out.println("Either doctor or patient doesn't exist");
        }

    }

    private static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        String queryForAppointment = "SELECT COUNT(id) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryForAppointment);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt(1);
                if (count==0){
                    return true;
                }else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


}
