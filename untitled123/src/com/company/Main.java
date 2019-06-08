package com.company;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.regex.Pattern;

public class Main {
    static String url = "jdbc:mysql://localhost/store?serverTimezone=Europe/Moscow&useSSL=false";
    static String username = "root";
    static String password = "Qwerty123";
    static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Main() throws SQLException {
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/store?serverTimezone=Europe/Moscow&useSSL=false";
        String username = "root";
        String password = "Qwerty123";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {

            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                System.out.print("Please insert name of department:");
                String department = reader.readLine();
                System.out.print("Please insert template for search:");
                String template  = reader.readLine();
                System.out.println();
                System.out.println("Who is head of department:");

                System.out.println(getHeadOfDepartment(department));
                System.out.println();
                System.out.println("Show " + department + " statistic:");
                System.out.println(showStatistic(department));
                System.out.println();
                System.out.println("Show the average salary for department "+department+":");
                System.out.println(showAvearageSalary(department));
                System.out.println();
                System.out.println("Show count of employee for "+ department+":");
                System.out.println(showCountEmployee(department));
                System.out.println();
                System.out.println("Global search by " + template +":");
                System.out.println(globalSearch(template));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }

    public static String getHeadOfDepartment(String str) throws SQLException {
        String select = "Select headOfDepartmentName FROM  itproger.department Where departmentName ='" + str + "'";
        ResultSet resultSet = null;
        try {
            Statement preparedStatement = conn.createStatement();
            resultSet = preparedStatement.executeQuery(select);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sr = null;
        while (resultSet.next()) {
            sr = resultSet.getString(1);
        }


        return "Head of " + str + " department is " + sr;
    }

    public static String showStatistic(String str) throws SQLException {
        String select = "Select COUNT(degree) FROM  itproger.lectors Where departmentName ='" + str + "'AND degree = 'professor'";
        String select1 = "Select COUNT(degree) FROM  itproger.lectors Where departmentName ='" + str + "'AND degree = 'associateProfessor'";
        String select2 = "Select COUNT(degree) FROM  itproger.lectors Where departmentName ='" + str + "'AND degree = 'assistant'";
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            Statement preparedStatement = conn.createStatement();
            Statement preparedStatement1 = conn.createStatement();
            Statement preparedStatement2 = conn.createStatement();
            resultSet = preparedStatement.executeQuery(select);
            resultSet1 = preparedStatement1.executeQuery(select1);
            resultSet2 = preparedStatement2.executeQuery(select2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        int professorsCount = 0;
        int associateProfessorCount = 0;
        int assistant = 0;

        while (resultSet.next()) {
            professorsCount = resultSet.getInt(1);
        }
        while (resultSet1.next()) {
            associateProfessorCount = resultSet1.getInt(1);
        }
        while (resultSet2.next()) {
            assistant = resultSet2.getInt(1);
        }


        return "professors - " + professorsCount
                + "\nassociate professors - " + associateProfessorCount
                + "\nassistants - " + assistant
                ;
    }

    public static String showAvearageSalary(String str) throws SQLException {
        String select = "Select AVG(salary) FROM  itproger.lectors Where departmentName ='" + str + "'";
        ResultSet resultSet = null;
        try {
            Statement preparedStatement = conn.createStatement();
            resultSet = preparedStatement.executeQuery(select);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sr = null;
        while (resultSet.next()) {
            sr = resultSet.getString(1);
        }
        return "The average salary of " + str + " is "+ sr;
        // write your code here
    } public static String showCountEmployee(String str) throws SQLException {
        String select = "Select COUNT(lector) FROM  itproger.department Where departmentName ='" + str + "'";
        ResultSet resultSet = null;
        try {
            Statement preparedStatement = conn.createStatement();
            resultSet = preparedStatement.executeQuery(select);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sr = null;
        while (resultSet.next()) {
            sr = resultSet.getString(1);
        }
        return sr;
        // write your code here
    }public static String globalSearch(String str) throws SQLException {
        String select = "Select distinct lector  FROM  itproger.department ";
        ResultSet resultSet = null;
        try {
            Statement preparedStatement = conn.createStatement();
            resultSet = preparedStatement.executeQuery(select);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount() ; i++) {


                String sr = resultSet.getString(i);
                if (sr.contains(str)){
                stringBuilder.append(sr+" ,");}
            }
        }
        stringBuilder.setLength(stringBuilder.length()-2);
        return stringBuilder.toString();
        // write your code here
    }

}


