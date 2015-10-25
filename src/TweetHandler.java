import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class TweetHandler {
	public Connection conn;
    private Statement statement;
    public static TweetHandler db;

    public TweetHandler() {
    	String url = "jdbc:mysql://localhost:3306/";
    	String databaseName = "MyTwitterStream";
    	String userName = "root";
    	String password = "";
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    		System.out.println("troublshooting");
    		//DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
    		this.conn = DriverManager.getConnection(url + databaseName,userName,password);
	    }
	    catch (Exception sqle) {
	        sqle.printStackTrace();
	    }
	
    }
    
    public static synchronized TweetHandler getDbCon() {
        if ( db == null ) {
            db = new TweetHandler();
        }
        return db;
 
    }
    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException{
    	System.out.println("troublshooting 2");
        statement = conn.createStatement();
        System.out.println("troublshooting 3");
        ResultSet res = statement.executeQuery(query);
        System.out.println("troublshooting 4");
        return res;
    }
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
        statement = conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
    }
}

