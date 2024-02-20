import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UserDatabseOperations {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {

		

		String userName="root";
		String password="root";
		String driver="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/atm";
		
		
		
		
		//Step 1: load driver
		Connection con=null;

		Class.forName(driver);
		
		
		//Step 2: make connection
		con= DriverManager.getConnection(url, userName, password);
		
		System.out.println(con);


		//Step 3: create statement
		
		 Statement stm=null;	 
		 stm=con.createStatement();
		 
		 //Step 4: Execute query
		 
		 int option = 1;
		 Scanner sc=new Scanner(System.in);
		 
		 while (option !=0) {
			 
			 System.out.println();
			 
			 System.out.println("Select option \n0: To exit \n1: To user login  \n2: Create account \n3: To admin login ");
			 option=sc.nextInt();	
			
			 switch (option) {
			
			case 1:{


				 System.out.println("Enter Account Number");
				 Long accountNumber=sc.nextLong();
				 
				 System.out.println("Enter Pin");
				 int pin=sc.nextInt();	
				 

			        // SQL query to check if the user exists and the provided credentials are correct
			        String sql = "SELECT * FROM user WHERE accountNumber = ? AND pin = ?";

			            PreparedStatement statement = con.prepareStatement(sql);
			            statement.setLong(1, accountNumber);
			            statement.setInt(2, pin);

			            // Execute the query
			            ResultSet resultSet = statement.executeQuery();

			            // Check if any rows are returned
			            if (resultSet.next()) {
			                // User exists and credentials are correct
			                String userName1 = resultSet.getString("userName");
			                int userID= resultSet.getInt("userID");
			                Boolean status=resultSet.getBoolean("userStatus");

			                if (status) {
								
							
			                
			                System.out.println("Login successful. Welcome, " + userName1 + " (Account : " + accountNumber + ")");
			                
			                int key=1;
			                while (key!=0) {
			                	 
			       			 System.out.println();
			       			 
			       			 System.out.println("Select option \n0: <- Back \n1: Deposit  \n2: Withdraw \n3: Check Balance \n4: Transfer \n5: Update info \n6: Delete account");
			       			 key=sc.nextInt();
								
								switch (key) {
								
								case 0:{
									break;
									}
								
								case 1:{
										
									Transaction transaction1=new Transaction();
					                Long newaccountBalance =transaction1.balanceCheck(accountNumber);


									System.out.println("Enter amount to deposit");
									Long newBalance=sc.nextLong();
									Long transactionAmount=newBalance;
									newBalance=newaccountBalance+newBalance;
									
										String depositQuery = "UPDATE user SET userBalance = ? WHERE accountNumber = ?";
							             PreparedStatement depositStatement = con.prepareStatement(depositQuery);
							             
							             depositStatement.setLong(1, newBalance);
							             depositStatement.setLong(2, accountNumber);

								            // Execute the update query
								            int rowsUpdated = depositStatement.executeUpdate();


								            if (rowsUpdated > 0) {
								            	Transaction transaction=new Transaction();
								            	transaction.depositAndWithdraw("deposit",userID, transactionAmount);
								                System.out.println("Balance deposit successfully.");
								            } else {
								                System.out.println("Failed...");
								            }

										
										
									
									break;
								}
								
								case 2:{
									
									Transaction transaction=new Transaction();
					                Long newaccountBalance =transaction.balanceCheck(accountNumber);


									System.out.println("Enter amount to withdraw");
									Long newBalance=sc.nextLong();
									Long transactionAmount=newBalance;
									newBalance=newaccountBalance-newBalance;
									
									
									String withdrawQuery = "UPDATE user SET userBalance=? WHERE accountNumber = ?";
						             PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
						             
						             withdrawStatement.setLong(1, newBalance);
						             withdrawStatement.setLong(2, accountNumber);
						             

						             int rowsUpdated=0;
							            // Execute the update query
							            if (newBalance>0) {
								            rowsUpdated = withdrawStatement.executeUpdate();
							
										}
							            if (newBalance<0) {
											System.out.println("Insufficient balance");

										}

							            if (rowsUpdated > 0) {
							            	transaction.depositAndWithdraw("withdraw",userID, transactionAmount);
							                System.out.println("Balance withdraw successfully.");
							            } else {
							                System.out.println("Failed...");
							            }

									
									
								
								break;
								}
								
								case 3:{
									
							        String balanceQuery = "SELECT userBalance FROM user WHERE accountNumber =?";

							        PreparedStatement balaceStatement = con.prepareStatement(balanceQuery);
							        balaceStatement.setLong(1, accountNumber);

						            // Execute the query
						            ResultSet resultSet1 = balaceStatement.executeQuery();
						            if (resultSet1.next()) {
										
						            	Long balance=resultSet1.getLong("userBalance");
										System.out.println(balance);
									}
			
						            
									break;
								}
									
								case 4:{
									System.err.println("Comming Soon");
									break;
								}
								
								case 5:{
					       			 
									System.out.println("Select option \n0: <- Back \n1: Update Name  \n2: Update Email \n3: Update phone no. \n4: Update pin");
									int key1=sc.nextInt();
									switch (key1) {
									case 0:
									{
										break;
									}
									
									case 1:{
										System.out.println("Enter new Name");
										
										System.out.println("Enter first name");
										String newName=sc.next();
										System.out.println("Enter Surname");
										String newSurname=sc.next();
										
										String withdrawQuery = "UPDATE user SET userName = ?, userSurname=? WHERE accountNumber = ?";
							             PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
							             
							             withdrawStatement.setString(1, newName);
							             withdrawStatement.setString(2, newSurname);
							             withdrawStatement.setLong(3, accountNumber);
							             withdrawStatement.executeUpdate();
	
										break;
									}
									
								
									
									case 2:{
										
										System.out.println("Enter new Email");
										String newEmail=sc.next();
										
										String withdrawQuery = "UPDATE user SET userEmail = ? WHERE accountNumber = ?";
							             PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
							             
							             withdrawStatement.setString(1, newEmail);
							             withdrawStatement.setLong(2, accountNumber);
							             withdrawStatement.executeUpdate();
										
										break;
									}
									
									case 3:{
										
										System.out.println("Enter new Phone");
										Long newPhone=sc.nextLong();
										
										String withdrawQuery = "UPDATE user SET userPhone = ? WHERE accountNumber = ?";
							             PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
							             
							             withdrawStatement.setLong(1, newPhone);
							             withdrawStatement.setLong(2, accountNumber);
							             withdrawStatement.executeUpdate();
										
										
										break;
									}
									
									case 4:{

										System.out.println("Enter new pin");
										int newPin=sc.nextInt();
										
										
										 Integer pinLength=String.valueOf(newPin).length();

							             
							             if (pinLength>4 || pinLength<4) {
												System.out.println("Invalid pin");
											}
											 else {
												 String withdrawQuery = "UPDATE user SET pin = ? WHERE accountNumber = ?";
									             PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
									             
									             withdrawStatement.setLong(1, newPin);
									             withdrawStatement.setLong(2, accountNumber);
									             withdrawStatement.executeUpdate();
											}
										
										
										break;
									}
									

									default:
										break;
									}
									
									break;
									
								}
								case 6:{
									
									System.out.println("Do you want to delete account? if yes press 1 ,to exit press 0");
									int option6=sc.nextInt();
									if (option6==1) {
										stm.executeUpdate("delete from user where accountNumber='"+accountNumber+"';");
										System.out.println("deleted");
									}
									
									break;
								}
								
								default:
									System.err.println("Wrong option");
									break;
								}
							}
			                
			                }
			                else {
			                	System.out.println();
								System.out.println("Access denied contact admin");
							}
			            } else {
			                // User does not exist or credentials are incorrect
			                System.out.println("Invalid account number or PIN. Please try again.");
			            }
			        
				 
				 break;
			}
			case 2:{
	
					 System.out.println("Enter Firstname");
					 String newUser=sc.next();
					 System.out.println("Enter Firstname");
					 String newSurname=sc.next();
					 
					 System.out.println("Enter Email");
					 String email=sc.next();	
					 
					 System.out.println("Enter Phone no.");
					 long phoneNo=sc.nextLong();
					 
					 
					 System.out.println("Enter 4 digit Pin");
					 int pin=sc.nextInt();
					 
					 Integer pinLength=String.valueOf(pin).length();
					 
					 if (pinLength>4 || pinLength<4) {
						System.out.println("Invalid pin");
					}
					 else {
						 String insertQuery="insert into user(pin,userName,userSurname,userEmail,userPhone) values("+pin+",'"+newUser+"','"+newSurname+"','"+email+"',"+phoneNo+");";
						 stm.executeUpdate(insertQuery);
						 System.out.println("Account created");
					}
					
					 
					 
					 
					 
					break;
					}
			
			case 3:{
				
				AdminDatabaseOperations adminDatabaseOperations=new AdminDatabaseOperations();
				
				adminDatabaseOperations.userInfo();
				
				
				 break;
				
			}
			
//			case 4:{
//				
//				System.out.println("Enter Username to update");
//				 String user=sc.next();
//				 
//				 System.out.println("1: To change username\n2: To change password \n3: To change both ");
//				 int choice=sc.nextInt();	
//				 
//				 
//				 switch (choice) {
//				case 1:{
//					System.out.println("Enter new Username");
//					 String newuser=sc.next();
//					 String insertQuery="UPDATE login SET name= '"+newuser+"' WHERE name='"+user+"';";
//					 stm.executeUpdate(insertQuery);
//
//					 break;
//				}
//				case 2:{
//					 System.out.println("Enter new Password");
//					 String newpass=sc.next();
//					 
//					
//					 String insertQuery="UPDATE login SET password= '"+newpass+"' WHERE name='"+user+"';";
//					 stm.executeUpdate(insertQuery);
//
//					 break; 
//				}
//					
//					
//				case 3:{
//					System.out.println("Enter new Username");
//					 String newuser=sc.next();
//					 
//					 System.out.println("Enter new Password");
//					 String newpass=sc.next();
//					 
//					
//					 String insertQuery="UPDATE login SET name= '"+newuser+"', password= '"+newpass+"' WHERE name='"+user+"';";
//					 
//					 stm.executeUpdate(insertQuery);
//					
//					break;
//				}
//
//				default:
//					break;
//				}
//				 
//				 break;
//				
//			}
				

			default:
				break;
			}
			 

			 	
			 }
		 
		 System.out.println("Logging out...");
		 TimeUnit.SECONDS.sleep(2);
		 System.out.println("Logged out");

		 
		 sc.close();
		 
		 
		 

	}
	
}

class Transaction{
	
	
	
	public void depositAndWithdraw(String type,int userID,Long transactionAmount) throws ClassNotFoundException, SQLException {

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
		
		if (type=="deposit") {
			
		
		 String insertQuery="insert into transaction(userID,transactionType,transactionAmount) values("+userID+",'deposit',"+transactionAmount+")";
		 
		 stm.executeUpdate(insertQuery);
		}
		if (type=="withdraw") {
			String insertQuery="insert into transaction(userID,transactionType,transactionAmount) values("+userID+",'withdraw',"+transactionAmount+")";
			 
			 stm.executeUpdate(insertQuery);
		}
	
		
		
	}
	
	
	public long balanceCheck(Long accountNumber) throws ClassNotFoundException, SQLException {
		String userName="root";
		String password="root";
		String driver="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/atm";
		//Step 1: load driver
		Connection con=null;

		Class.forName(driver);
		
		
		//Step 2: make connection
		con= DriverManager.getConnection(url, userName, password);

		
		String balanceQuery = "SELECT userBalance FROM user WHERE accountNumber =?";

        PreparedStatement balaceStatement = con.prepareStatement(balanceQuery);
        balaceStatement.setLong(1, accountNumber);

        // Execute the query
        Long balance = null;
        ResultSet resultSet1 = balaceStatement.executeQuery();
        if (resultSet1.next()) {
			
        	 balance=resultSet1.getLong("userBalance");
			System.out.println(balance);
		}
		return  balance;
	}
}


