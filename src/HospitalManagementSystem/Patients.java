package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
    private Connection con;
    private Scanner sc;

    public Patients(Connection con,Scanner sc){
        this.con=con;
        this.sc=sc;
    }

    public void addPatient(){
        System.out.println("Enter patient's name: ");
        String name=sc.next();
        System.out.println("Enter patient's age: ");
        int age=sc.nextInt();
        System.out.println("Enter patient's gender: ");
        String gender=sc.next();

        try {
            String query1="Insert into patients(name,age,gender) values(?,?,?);";
            PreparedStatement ps=con.prepareStatement(query1);
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setString(3,gender);
            int affectedrows=ps.executeUpdate();
            if(affectedrows>0){
                System.out.println("Patient added successfully !");
            }
            else {
                System.out.println("Failed to add patient !");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        String query2="Select * from patients";
        try {
            PreparedStatement ps=con.prepareStatement(query2);
            ResultSet rs=ps.executeQuery();
            System.out.println("+------------+---------------------------+-------------+--------+");
            System.out.println("| Patient_id |        Patient_Name       | Patient_Age | Gender |");
            System.out.println("+------------+---------------------------+-------------+--------+");

            while (rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                int age=rs.getInt("age");
                String gender=rs.getString("gender");
                System.out.printf("| %-11s | %-26s | %-12s | %-7s |\n",id,name,age,gender);
                System.out.println("+------------+---------------------------+-------------+--------+");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean getPatientById(int id) {
        String query3 = "Select * from patients where id=?;";
        try {
            PreparedStatement ps = con.prepareStatement(query3);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
