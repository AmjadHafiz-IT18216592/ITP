package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
public class billLoginController {

	/*
	 * 
	 */

	@FXML
	private Button next;
	
	@FXML
	private TextField fname;
	@FXML
	private TextField lname;
	@FXML
	public static int billNo ;


	
	// Event Listener on Button[#next].onAction
	@FXML
	public void insert(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {

		if(fname.getText().equals("")|| lname.getText().equals("")) {
			
			TrayNotification tray = new TrayNotification();
		 	String title = "Fields are empty!";
	        String message = "Please fill the fields correctly";
	        //Notification notification = Notification.SUCCESS;
	        tray.setTitle(title);
	        tray.setMessage(message);
	        tray.setNotificationType(NotificationType.WARNING);
	        tray.showAndDismiss(Duration.seconds(6));
	        
		}
		else {
				String sqlmain = "SELECT `EID` FROM `employee` WHERE `fname`='"+fname.getText()+"' AND `lname` = '"+lname.getText()+"'";
			ResultSet rs1 = DbUtil.getValues(sqlmain);
			
			
			
			if(rs1.getRow() == 0) {
				
			 	TrayNotification tray = new TrayNotification();
			 	String title = "Wrong!";
		        String message = "Please enter ypur first name or last name correctly";
		        //Notification notification = Notification.SUCCESS;
		        tray.setTitle(title);
		        tray.setMessage(message);
		        tray.setNotificationType(NotificationType.ERROR);
		        tray.showAndDismiss(Duration.seconds(5));
			}
			else {
				  
				String sql =   "INSERT INTO `bill`( `EID`,`TotalNet-Price`, `TotalCost-Price`, `TotalSelling-Price`, `Discount-Amount`) VALUES ('"+rs1.getString(1)+"',0.0,0.0,0.0,0.0)";
				//String sql1 = "SELECT * FROM `bill` WHERE EID ='"+rs1.getString(1)+"'";
				String sql1 = "SELECT * FROM `bill` WHERE  `EID`= '"+rs1.getString(1)+"' AND `TotalNet-Price` = 0.0 AND `TotalCost-Price` = 0.0 AND `TotalSelling-Price`= 0.0 AND `Discount-Amount`= 0.0";
				try {
					DbUtil.dbExecuteQuery(sql);
					
					ResultSet rs =	DbUtil.getValues(sql1);
					 billNo = rs.getInt(1);
					 	
					 	TrayNotification tray = new TrayNotification();
					 	String title = fname.getText()+" Congratulations !";
				        String message = "You've successfully Login";
				        //Notification notification = Notification.SUCCESS;
				        tray.setTitle(title);
				        tray.setMessage(message);
				        tray.setNotificationType(NotificationType.SUCCESS);
				        tray.showAndDismiss(Duration.seconds(6));
				        
					 Parent root = FXMLLoader.load(getClass().getResource("/application/First.fxml"));
					 Scene scene = new Scene(root);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.show();
				}
				catch(SQLException e) {
					System.out.println("exception occure while inserting the data "+ e);
					e.printStackTrace();
					throw e;
				}
			}
		}
		
		
		
		
	}
	
	
	@FXML
	public void toFirst(ActionEvent event) {
		
		 
	}

}
