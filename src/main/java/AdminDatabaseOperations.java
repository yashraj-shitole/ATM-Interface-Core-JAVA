import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class AdminDatabaseOperations {

	
	public void userInfo() throws ClassNotFoundException, SQLException {
		
		String userName="root";
		String password="root";
		String driver="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/atm";
		
		
		
		
		//Step 1: load driver
		Connection con=null;

		Class.forName(driver);
		
		
		//Step 2: make connection
		con= DriverManager.getConnection(url, userName, password);
		

		//Step 3: create statement
		
		 Statement stm=null;	 
		 stm=con.createStatement();
		 
		 int option =1;
		 Scanner sc=new Scanner(System.in);
		 
		 System.out.println("Enter username");
		 String adminName=sc.next();
		 
		 System.out.println("Enter Pin");
		 int pin=sc.nextInt();	
		 

	        // SQL query to check if the user exists and the provided credentials are correct
	        String sql = "SELECT * FROM admin WHERE adminName = ? AND pin = ?";

	            PreparedStatement statement = con.prepareStatement(sql);
	            statement.setString(1, adminName);
	            statement.setInt(2, pin);

	            // Execute the query
	            ResultSet resultSet = statement.executeQuery();

	            // Check if any rows are returned
	            if (resultSet.next()) {
	                // User exists and credentials are correct
	                String adminName1 = resultSet.getString("adminName");
	                int adminID= resultSet.getInt("adminID");
	                
	                Boolean transactionHitoryAllowed=resultSet.getBoolean("transactionHitoryAllowed");
	                Boolean atmInfo=resultSet.getBoolean("atmInfo");
	                Boolean userInfo=resultSet.getBoolean("userInfo");
	                Boolean userStatus=resultSet.getBoolean("userStatus");
	                Boolean main=resultSet.getBoolean("main");
	                
	                
	                
	                
	                

	                
	                System.out.println("Login successful. Welcome, " + adminName1 + " (Admin id : " + adminID + ")");
	                

		 while (option !=0) {

		 
			 System.out.println();
			 
			 System.out.println("Select option \n0: To exit \n1: Users list  \n2: To delete user account  \n3: Update user status \n4: Atm Information \n5: Transaction history \n6:other  ");
			 option=sc.nextInt();	
			 
			 
		 switch (option) {
		 
		 
		case 0:{
			System.out.println("Back");			
			break;
		}
		
		case 1:{
			if (userInfo) {
				
			
			
			ResultSet rs=stm.executeQuery("Select * from user;");
			 
			 System.out.println("Account no. \tUser Name \tEmail  \t\tPhone \t\tBalance \tStatus");
			 System.out.println("---------------------------------------------------------------------------------------");
			 
			 while (rs.next()) {
				 Long account=rs.getLong("accountNumber");
				String Name= rs.getString("userName");
				 String email=rs.getString("userEmail");
				 Long phone=rs.getLong("userPhone");
				 Long balance=rs.getLong("userBalance");
				 Boolean status=rs.getBoolean("userStatus");
				
		
				System.out.println(account+"\t"+Name+"\t"+email+"\t"+phone+"\t"+balance+"\t\t"+status);
				
			}
			}
			else {
				System.out.println("Access denied");
			}
			
			
			break;
		}
			
		case 2:{
			if (userInfo) {
				
			
			
			System.out.println("Enter account number");
			String accountNumber=sc.next();
			
			System.out.println("Do you want to delete account? if yes press 1 ,to exit press 0");
			int option6=sc.nextInt();
			if (option6==1) {
				stm.executeUpdate("delete from user where accountNumber='"+accountNumber+"';");
				System.out.println("deleted");
			}
			}
			else {
				System.out.println("Access denied");
			}
			
			break;
		}
		
		case 3:{
			if (userStatus) {
				
				
				ResultSet rs=stm.executeQuery("Select * from user where userStatus='false' ;");
				 
				 System.out.println("User ID \tAccount no. \tUser Name \tEmail  \tPhone \tBalance \tStatus");
				 System.out.println("----------------------------------------------------------------------------------------");
				 
				 while (rs.next()) {
					 Integer userId=rs.getInt("userID");
					 Long account=rs.getLong("accountNumber");
					String Name= rs.getString("userName");
					 String email=rs.getString("userEmail");
					 Long phone=rs.getLong("userPhone");
					 Long balance=rs.getLong("userBalance");
					 Boolean status=rs.getBoolean("userStatus");
					
			
					System.out.println(userId+"\t"+account+"\t"+Name+"\t"+email+"\t"+phone+"\t"+balance+"\t"+status);
					
				}
				 
				 Integer statusChoice=0;
				 System.out.println();
				 System.out.println("To update status press 1 to exit press 0 ");
				 statusChoice=sc.nextInt();
				 
				 if (statusChoice==1) {

					 System.out.println("Enter user Account number to change status");
					 Long accountNumber=sc.nextLong();
					 System.out.println("Enter status");
					 boolean status=sc.nextBoolean();
					 
					 String statusQuery="UPDATE user SET userStatus =? WHERE accountNumber=? ;";
					 
					    PreparedStatement statusStatement = con.prepareStatement(statusQuery);
			             
					    statusStatement.setBoolean(1, status);
					    statusStatement.setLong(2, accountNumber);

				            // Execute the update query
				            int rowsUpdated = statusStatement.executeUpdate();
				            if (rowsUpdated > 0) {
				            	
				                System.out.println("Status updated...");
				            } else {
				                System.out.println("Failed...");
				            }

					
				}
				 
				
			}
			else {
				System.out.println("Access denied");
			}
			
			break;
		}
		
		case 4:{
			if (atmInfo) {
				
				
				ResultSet rs=stm.executeQuery("Select * from atmInfo;");
				 
				 System.out.println("ATM ID \tATM no. \tATM status\tCash");
				 System.out.println("---------------------------------------------------");
				 
				 while (rs.next()) {
					 Integer atmID=rs.getInt("atmID");
					 Long atmNumber=rs.getLong("atmNumber");
					 Boolean atmStatus=rs.getBoolean("atmStatus");
					 Long cash=rs.getLong("cash");

					
			
					System.out.println(atmID+"\t"+atmNumber+"\t\t"+atmStatus+"\t\t"+cash);
					
				}
				
				
				
			}
			else {
				System.out.println("Access denied");
			}
			
			break;
		}
		
		
		case 5:{
			
			
			if (transactionHitoryAllowed) {
				
				
				

				
				ResultSet rs=stm.executeQuery("Select * from transaction;");
				 
				 System.out.println("transaction ID \tUser ID \tTransaction type\tTransaction amount \tTransaction Date adn time");
				 System.out.println("------------------------------------------------------------------");
				 
				 while (rs.next()) {
					 Integer id=rs.getInt("transactionID");
					 Integer userId=rs.getInt("userID");
					 String type=rs.getString("transactionType");
					 Long amount=rs.getLong("transactionAmount");
					 String time=rs.getString("transactionTime");

					
			
					System.out.println(id+"\t\t"+userId+"\t\t"+type+"\t\t\t"+amount+"\t"+time);
					
				
				
				 }
			}
			else {
				System.out.println("Access denied");
			}
			
			break;
		}
		
		case 6:{
			if (main) {
				
				System.out.println("Under development");
				
			}
			else {
				System.out.println("Access denied");
			}
			
			
			break;
		}

		default:
			break;
		}
		 
		 
		 
		 
		}		 
	            } else {
	                // User does not exist or credentials are incorrect
	                System.out.println("Invalid admin name or PIN. Please try again.");
	            }
	            
	            sc.close();
	}
	
	
}
