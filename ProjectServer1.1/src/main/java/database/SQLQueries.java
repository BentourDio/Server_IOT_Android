package database;

import java.sql.*;

// Class for the queries used to the database
public class SQLQueries{

    // Create new table to database with name = table_name
    // If it does not already exist
    public void createTable(Statement stmt, String table_name){
        String query;

        // Query to create table
        // (id is the primary key and increments by 1 for each new value that is inserted in the table so that it can remain unique)
        query = "create table if not exists " + table_name + " (\n"
                + "id int not null AUTO_INCREMENT,\n"
                + "tmstamp timestamp not null,\n"
                + "x_coordinates double not null,\n"
                + "y_coordinates double not null,\n"
                + "smoke_value double,\n"
                + "gas_value double,\n"
                + "temp_value double,\n"
                + "UV_value double,\n"
                + "danger_degree varchar(13) not null,\n"
                + "primary key (id)\n"
                + ");";

        try{
            // Execute query
            stmt.executeUpdate(query);
        }
        catch(SQLException ex){
            // If there was an error
            ex.printStackTrace();
        }
    }

    // Insert new record to database
    public void tableInsert(Statement stmt, int case_num, String table_name, double x, double y, double smoke, double gas, double temp, double UV){
        String query;

        // Query to insert record
        query = "insert into " + table_name + "\n" + "values (null, current_timestamp, " + x + ", " + y + ", ";

        if(case_num == 1){
            query = query + smoke + ", " + gas + ", null, null, 'High Danger');";
        }
        else if(case_num == 2){
            query = query + "null, null," + temp + ", " + UV + ", 'Medium Danger');";
        }
        else if(case_num == 3){
            query = query + "null, " + gas + ", null, null, 'High Danger');";
        }
        else{
            query = query + smoke + ", " + gas + ", " + temp + ", " + UV + ", 'High Danger');";
        }

        try{
            // Execute query
            stmt.executeUpdate(query);
        }
        catch(SQLException ex){
            // If there was an error
            ex.printStackTrace();
        }
    }

    // Search between two timestamps
    // if start_date is "0", then get all values from the beginning until end_date
    // if end_date is "0", then get values from start_date until the end
    // If neither is "0", then get values from start_date until end_date
    // if both equal "0", then get all values
    public void search_by_date(Statement stmt, String table_name, String start_date, String end_date){
        String query =  "select *\n" +
                "from " + table_name + "\n";

        if(start_date == "0" && end_date != "0"){
            query = query + "WHERE tmstamp <= '" + end_date + "';";
        }
        else if(start_date != "0" && end_date == "0"){
            query = query + "WHERE tmstamp >= '" + start_date + "';";
        }
        else if(start_date != "0" && end_date != "0"){
            query = query + "WHERE tmstamp >= '" + start_date + "' and tmstamp <= '" + end_date + "';";
        }
        else{
            query = query + ";";
        }

        try{
            // Execute query
            ResultSet rs = stmt.executeQuery(query);

            // print the results
            System.out.println("id | tmstamp | x | y | smoke_value | gas_value | temp_value | UV_value | danger_degree|");
            System.out.println("---------------------------------------------------------------------------------------");

            while (rs.next()){
                int id = rs.getInt("id");
                Date tmstamp = rs.getDate("tmstamp");
                Double x = rs.getDouble("x_coordinates");
                Double y = rs.getDouble("y_coordinates");
                Double smoke_value = rs.getDouble("smoke_value");
                Double gas_value = rs.getDouble("gas_value");
                Double temp_value = rs.getDouble("temp_value");
                Double UV_value = rs.getDouble("UV_value");
                String danger_degree = rs.getString("danger_degree");

                System.out.format("%s | %s | %s | %s | %s | %s | %s | %s | %s|\n", id, tmstamp, x, y, smoke_value, gas_value, temp_value, UV_value, danger_degree);
            }
        }
        catch(SQLException ex){
            // If there was an error
            ex.printStackTrace();
        }
    }

    // Get values given a danger degree
    // Two options: "Medium Danger" and "High Danger"
    public void search_by_danger(Statement stmt, String table_name, String danger){
        String query =  "select *\n" +
                "from " + table_name + "\n" +
                "where danger_degree = '" + danger + "';";

        try{
            // Execute query
            ResultSet rs = stmt.executeQuery(query);

            // print the results
            System.out.println("id | tmstamp | x | y | smoke_value | gas_value | temp_value | UV_value | danger_degree|");
            System.out.println("---------------------------------------------------------------------------------------");

            while (rs.next()){
                int id = rs.getInt("id");
                Date tmstamp = rs.getDate("tmstamp");
                Double x = rs.getDouble("x_coordinates");
                Double y = rs.getDouble("y_coordinates");
                Double smoke_value = rs.getDouble("smoke_value");
                Double gas_value = rs.getDouble("gas_value");
                Double temp_value = rs.getDouble("temp_value");
                Double UV_value = rs.getDouble("UV_value");
                String danger_degree = rs.getString("danger_degree");

                System.out.format("%s | %s | %s | %s | %s | %s | %s | %s | %s|\n", id, tmstamp, x, y, smoke_value, gas_value, temp_value, UV_value, danger_degree);
            }

            rs.close();
        }
        catch(SQLException ex){
            // If there was an error
            ex.printStackTrace();
        }
    }

    // Delete from one of four types (smoke_value, gas_value, UV_value, temp_value)
    // all rows that have the given value for that type
    public void delete(Statement stmt, String table_name, String type, double value){
        String query =  "delete from " + table_name + "\n" +
                "where " + type + " = " + value + ";";

        try{
            // Execute query
            stmt.executeUpdate(query);
        }
        catch(SQLException ex){
            // If there was an error
            ex.printStackTrace();
        }
    }
}
