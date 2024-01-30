package com.goonok;

import com.goonok.driver.HospitalDAO;
import com.goonok.hospital.Doctor;
import com.goonok.hospital.Patient;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ///TODO - https://www.youtube.com/watch?v=ECoIdyfcObE
        ///TIME - 44:47

        HospitalDAO hospitalDAO = new HospitalDAO();
        hospitalDAO.databaseConnect();
        Patient patient = new Patient();
        Doctor doctor = new Doctor();

    }
}