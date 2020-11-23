package jdbctests;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String dbUrl="jdbc:oracle:thin:@52.91.95.229:1521:xe";
        String dbUsername="hr";
        String dbPassword="hr";

        // create connection
        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //creating statement object
        Statement statement= connection.createStatement();
        // run query and get the result in the result set object
        ResultSet resultSet= statement.executeQuery("Select * from departments");

//        // move pointer to first row
//        resultSet.next();
//        //move pointer to the second raw
//        //resultSet.next();
//
//        System.out.println(resultSet.getString("region_name"));
//        System.out.println(resultSet.getString(2));
//
//        System.out.println(resultSet.getString("region_id")+"-" + resultSet.getString(2));
//        //move pointer to the second raw
//        resultSet.next();
//        System.out.println(resultSet.getString(1)+"-"+ resultSet.getString("region_name"));
//
//        resultSet.next();
//        System.out.println(resultSet.getString(1)+"-"+ resultSet.getString("region_name"));
//
        while(resultSet.next()){
            System.out.println(resultSet.getString(1)+"-"+ resultSet.getString(2)+ "-" +  resultSet.getString(3)+ "-" +  resultSet.getString(4));

        }

        // close all connections
        resultSet.close();
        statement.close();
        connection.close();

    }
}
