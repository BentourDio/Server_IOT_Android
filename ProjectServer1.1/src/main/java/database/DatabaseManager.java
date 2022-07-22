package database;

// Class with all the methods that can be called from the app
public class DatabaseManager{
    private String table_name = "DangerDB";
    private SQLQueries queries = new SQLQueries();
    private SQLConnection sql_con = new SQLConnection();

    public void connect(){
        sql_con.CreateConnection();
    }

    public void initialize(){
        try{
            queries.createTable(sql_con.stmt, table_name);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void update(int case_num, double x, double y, double smoke, double gas, double temp, double UV){
        queries.tableInsert(sql_con.stmt, case_num, table_name, x, y, smoke, gas, temp, UV);
    }

    public void date_search(String start_date, String end_date){
        queries.search_by_date(sql_con.stmt, table_name, start_date, end_date);
    }

    public void danger_search(String danger){
        queries.search_by_danger(sql_con.stmt, table_name, danger);
    }

    public void delete(String type, double value){
        queries.delete(sql_con.stmt, table_name, type, value);
    }

    public void disconnect(){
        try{
            sql_con.CloseConnection();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

