package HospitalManagementSystem;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Anur@2003";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);
        try {
            Connection con= DriverManager.getConnection(url,username,password);
            Patients p= new Patients(con,sc);
            Doctors d= new Doctors(con);

            System.out.println();
            System.out.println("🏥 Welcome To The HOSPITAL MANAGEMENT SYSTEM 🏥!!");

            while(true){
                System.out.println();
                System.out.println("1. Add Patient 😷🥼️");
                System.out.println("2. View Patients 🧍‍‍🧍‍♂️♂️️");
                System.out.println("3. View Doctors 👨‍⚕️👩‍⚕️");
                System.out.println("4. Book Appointment 🩺💊💉");
                System.out.println("5. Exit");
                System.out.println();
                System.out.println("Enter your choice: ");
                int choice =sc.nextInt();

                switch (choice){
                    case 1:
                        p.addPatient();
                        System.out.println();
                        break;

                    case 2:
                        p.viewPatients();
                        System.out.println();
                        break;


                    case 3:
                        d.viewDoctors();
                        System.out.println();
                        break;


                    case 4:
                        bookAppointment(p,d,con,sc);
                        System.out.println();
                        break;


                    case 5:
                        return;

                    default:
                        System.out.println("Enter a valid choice !!");
                        break;

                }


            }
            }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public static void bookAppointment(Patients p, Doctors d, Connection con, Scanner sc){
        System.out.println("Enter Patient id: ");
        int patient_id= sc.nextInt();
        System.out.println("Enter Doctor id: ");
        int doctor_id= sc.nextInt();
        System.out.println("Enter the appointment date in YY--MM--DD :");
        String appointmentDate=sc.nextLine();
        if(p.getPatientById(patient_id) && d.getDoctorById(doctor_id)){
            if(checkDoctorAvailability(con, doctor_id, appointmentDate)){
                String query="Insert into Appointments(patient_id,doctor_id,appointment_date) values(?,?,?);";
                try {
                    PreparedStatement ps=con.prepareStatement(query);
                    ps.setInt(1,patient_id);
                    ps.setInt(2,doctor_id);
                    ps.setString(3,appointmentDate);
                    int rowsaffected=ps.executeUpdate();
                    if(rowsaffected>0){
                        System.out.println("Appointment Booked 👍");
                    }
                    else {
                        System.out.println("Failed to book appointment !!");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Sorry! Doctor not available on this date. ");
            }
        }
        else {
            System.out.println("Either Patient not available or Doctor not available");
        }


    }
    public static boolean checkDoctorAvailability(Connection con,int doctor_id,String appointmentDate){
        String query="Select count(*) from Appointments where doctor_id=? and appointment_date=?;";
        try {
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,doctor_id);
            ps.setString(2,appointmentDate);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                int count=rs.getInt(1);
                if(count==0){
                    return true;
                }
                else {
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;


    }

}
