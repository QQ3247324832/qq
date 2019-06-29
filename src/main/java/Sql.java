import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * 连接数据库
 * @author xinYing
 * @date 2019-06-29
 */
  class Sql {

	
     static Connection getConnection(){
    	Connection conn=null;
    	//数据库名
    	String tableName = "gluttonoussnake";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+tableName+"?characterEncoding=utf8","root","root");
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
    
     static void exit(Connection conn){
    	
    	try {
    		if(conn!=null){
    		conn.close();
    		}
		} catch (SQLException e) {
			System.out.println("错误");
		}
    	}
}
