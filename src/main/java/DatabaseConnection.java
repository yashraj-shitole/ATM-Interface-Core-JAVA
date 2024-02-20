import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

				String userName="root";
				String password="root";
				String driver="com.mysql.cj.jdbc.Driver";
				String url="jdbc:mysql://localhost:3306/atm";
				
				
				Connection con=null;
				
				
				//Step 1: load driver
				Class.forName(driver);
				
				
				//Step 2: make connection
				con= DriverManager.getConnection(url, userName, password);
				
				System.out.println(con);


				//Step 3: create statement
				
				 Statement stm=null;	 
				 stm=con.createStatement();
				 String query="select * from user";
				 
				 //Step 4: Execute query
				 
				 ResultSet rs=stm.executeQuery(query);
				 
				 System.out.println("Product name \t Product price");
				 System.out.println("------------------------------");
				 
				 while (rs.next()) {
					String productName= rs.getString(2);
					float prodectPrice=rs.getFloat(3);
					
			
					System.out.println(productName+"\t\t\t | "+prodectPrice);
					
				}
				 
				 

	}

}
