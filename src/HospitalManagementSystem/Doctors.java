package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private Connection con;

    public Doctors(Connection con){
        this.con=con;
    }



    public void viewDoctors(){
        String query1="Select * from doctors";
        try {
            PreparedStatement ps=con.prepareStatement(query1);
            ResultSet rs=ps.executeQuery();
            System.out.println("+------------+---------------------------+-------------------+");
            System.out.println("| Doctor_id |        Doctor_Name         | Specialization    |");
            System.out.println("+------------+---------------------------+-------------------+");

            while (rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String specialization=rs.getString("specialization");
                System.out.printf("| %-11s | %-27s | %-18s |\n",id,name,specialization);
                System.out.println("+------------+---------------------------+-------------------+");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean getDoctorById(int id) {
        String query2 = "Select * from doctors where id=?;";
        try {
            PreparedStatement ps = con.prepareStatement(query2);
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
