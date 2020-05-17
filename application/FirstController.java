package application;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import com.mysql.cj.xdevapi.RemoveStatement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class FirstController implements Initializable{

	@FXML private TableView<Bill> table;
	@FXML private TableColumn<Bill, String> Item;
	@FXML private TableColumn<Bill, String> Brand;
	@FXML private TableColumn<Bill, Integer> Quantity;
	@FXML private TableColumn<Bill, Double> Rate;
	@FXML private TableColumn<Bill,Double> Cost;
	
	
	@FXML private Button btn,ok,ed,del,edit,tot,pick,next;
	@FXML private TextField givenAmount , totView, dis,ga,bl,na;
	@FXML private TextField tx;
	@FXML private ComboBox<Integer> cb3;

	private String cn , bn,ItemNO;
	private Integer qn , ind;
	private Double sp,cp,c,s;
	public static double totalCostPrice = 0.0;
	public static double totalSellingPrice = 0.0 ;
	public static double finalAmount;
	public static  double diAmount;
	public static double bal;
	DecimalFormat df = new DecimalFormat("####0.00");
	/*
	 * @FXML private ComboBox<String> cb1;
	@FXML private ComboBox<String> cb2;
	
	
	
	
	ObservableList<String> list1 = FXCollections.observableArrayList("Gents T-Shirt","Gents Pants","Saree","Longe sleev Shirt","Short sleev Shirt","Shalwar");
	ObservableList<String> list2 = FXCollections.observableArrayList("UnderArmour","Emerald","Kanniya","Gucci","Adidas");
	ObservableList<Integer> list3 = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,10,11,12,13);
	
	 */
	
	ObservableList<Integer> list3 = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,10,11,12,13);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*
		 * cb1.setItems(list1);
		cb2.setItems(list2);
		 */
		cb3.setItems(list3);

		
		Item.setCellValueFactory(new PropertyValueFactory<Bill, String>("Item"));
		Brand.setCellValueFactory(new PropertyValueFactory<Bill, String>("Brand"));
		Quantity.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("Quantity"));
		Rate.setCellValueFactory(new PropertyValueFactory<Bill, Double>("Rate"));
		Cost.setCellValueFactory(new PropertyValueFactory<Bill, Double>("Cost"));
		
		table.setEditable(true);
		
		//SET TOOLTIP to delete button
		  final Tooltip deleteTooltip = new Tooltip();
		  deleteTooltip.setText("Click here to delete!");
		  del.setTooltip(deleteTooltip);
		  
		//SET TOOLTIP to edit button
		  final Tooltip editTooltip = new Tooltip();
		  editTooltip.setText("Click here to edit!");
		  edit.setTooltip(editTooltip);
		
		  //SET TOOLTIP to pick button
		  final Tooltip pickTooltip = new Tooltip();
		  pickTooltip.setText("use to pick a certain row in table");
		  pick.setTooltip(pickTooltip);
		  
		//SET TOOLTIP to pick button
		  final Tooltip nextTooltip = new Tooltip();
		  nextTooltip.setText("Click to add item o the table");
		  next.setTooltip(nextTooltip);
		   
	}
	 
	 
	 
	
	  @FXML
	    public void insert(ActionEvent a) throws ClassNotFoundException, SQLException {
			
		 
		if(tx.getText().equals("") || cb3.getValue() == null) {
			
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
			
			try {
			 String sql2 = "CREATE VIEW `repeatitem`(`ItemID`) AS SELECT `ItemID` FROM `billingitem` WHERE `BillID` = '"+billLoginController.billNo+"' ";
			DbUtil.dbExecuteQuery(sql2);
			
			 String sql4 = "SELECT `ItemID` FROM `repeatitem` WHERE `ItemID` ='"+tx.getText()+"' ";
			ResultSet Res = DbUtil.getValues(sql4);
			//String IID = Res.getString(1);
		
			if(Res.isLast()) {
				
				String sql6 = "DROP VIEW repeatitem";
				DbUtil.dbExecuteQuery(sql6);
				
				TrayNotification tray = new TrayNotification();
			 	String title = "Item already in bill!";
		        String message = "Please update the item!";
		        //Notification notification = Notification.SUCCESS;
		        tray.setTitle(title);
		        tray.setMessage(message);
		        tray.setNotificationType(NotificationType.ERROR);
		        tray.showAndDismiss(Duration.seconds(6));
		        
		        
				
			}
			
			else {
				
				String sql6 = "DROP VIEW repeatitem";
				DbUtil.dbExecuteQuery(sql6);
				

				  
				   String sql ="SELECT * FROM `item` WHERE itemID = '"+tx.getText()+"'";
				    String sql1 = "INSERT INTO `billingitem`(`BillID`, `ItemID`, `Quantity`) VALUES ('"+billLoginController.billNo+"','"+tx.getText()+"','"+cb3.getValue()+"')";
				
					
					ResultSet rs =	DbUtil.getValues(sql);
					 cn = rs.getString(8);
					 
					 bn =  rs.getString(6);
					 sp = rs.getDouble(5);
					 cp = rs.getDouble(4);
					 
					 
					 
					totalCostPrice = totalCostPrice + (cp*cb3.getValue());
					totalSellingPrice = totalSellingPrice+(sp*cb3.getValue()) ;
					
						Bill bill = new Bill(cn,bn,cb3.getValue(),sp,cb3.getValue()*sp);
						
						
						table.getItems().add(bill);
							
						DbUtil.dbExecuteQuery(sql1);
						
						
				
			}
			 
			
			
		
					
			   }
			  /*
			   * 
			   *  else {
				   TrayNotification tray = new TrayNotification();
				 	String title = "Item already in bill!";
			        String message = "Please update the item!";
			        //Notification notification = Notification.SUCCESS;
			        tray.setTitle(title);
			        tray.setMessage(message);
			        tray.setNotificationType(NotificationType.ERROR);
			        tray.showAndDismiss(Duration.seconds(6));
			        
			        String sql4 = "DROP VIEW `repeatitem` ";
					DbUtil.dbExecuteQuery(sql4);
			   }
			   
			   */
			  
			
			
			catch(SQLException e) {
				System.out.println("exception occure while inserting the data "+ e);
				e.printStackTrace();
				throw e;
			}
		}
			
	}
	  
	  
	  @FXML
		 public void total() throws ClassNotFoundException, SQLException {
		  
		  
		   //double netPrice = totalSellingPrice-totalCostPrice; 
				 double totalProfit = totalSellingPrice-totalCostPrice; 
				 
				 String sql="UPDATE `bill` SET `TotalNet-Price`='"+totalSellingPrice+"',`TotalCost-Price`='"+totalCostPrice+"',`TotalSelling-Price`='"+totalSellingPrice+"' WHERE `BillID`='"+billLoginController.billNo+"'";
				 String sql1="SELECT * FROM `bill` WHERE BillID ='"+billLoginController.billNo+"'";
				 try {
					DbUtil.dbExecuteQuery(sql);
					String val = Double.toString(totalSellingPrice);
					  totView.setText(val);
					  
					ResultSet rs =	DbUtil.getValues(sql1);
					 //double ntp = rs.getDouble(4);
					 
					 if(rs.getDouble(6) >= 10000.00) {
						
						
						//status.setText("You have 5% discount!");
						TrayNotification tray = new TrayNotification();
					 	String title = "Congradulations!";
				        String message = "You have 5% discount!";
				        //Notification notification = Notification.SUCCESS;
				        tray.setTitle(title);
				        tray.setMessage(message);
				        tray.setNotificationType(NotificationType.SUCCESS);
				        tray.showAndDismiss(Duration.seconds(7));
				        
				        
						
						diAmount =  (rs.getDouble(6)*5)/100;
						finalAmount = rs.getDouble(6)-diAmount;
							dis.setText(Double.toString(diAmount)); 
							na.setText(Double.toString(finalAmount));
				 String sql2="UPDATE `bill` SET `Discount-Amount`='"+diAmount+"',`TotalNet-Price`='"+finalAmount+"' WHERE `BillID`='"+billLoginController.billNo+"'";
				 DbUtil.dbExecuteQuery(sql2);		
					 }
					 else
						 finalAmount =0.00;
					
						}
						catch(SQLException e) {
							System.out.println("exception occure while inserting the data "+ e);
							e.printStackTrace();
							throw e;
						}
				 }
			
		 

	  @FXML
		 public void calcBlance(ActionEvent e) throws IOException {
		  Double amount = Double.parseDouble(ga.getText());
		  
		  if(ga.getText().equals(null)) {
			  TrayNotification tray = new TrayNotification();
			 	String title = "Field is empty!!";
		        String message = "Please fill and try again.";
		        //Notification notification = Notification.SUCCESS;
		        tray.setTitle(title);
		        tray.setMessage(message);
		        tray.setNotificationType(NotificationType.WARNING);
		        tray.showAndDismiss(Duration.seconds(4));
		  }
		  else if(amount <=0) {
			  TrayNotification tray = new TrayNotification();
			 	String title = "Not a valid amount!";
		        String message = "Please enter a valid amount!";
		        //Notification notification = Notification.SUCCESS;
		        tray.setTitle(title);
		        tray.setMessage(message);
		        tray.setNotificationType(NotificationType.ERROR);
		        tray.showAndDismiss(Duration.seconds(4));
		  }
		  
		  else {
			 
			  if( finalAmount == 0.00) {
				  
				  if(amount >= totalSellingPrice) {
				  bal = amount-totalSellingPrice;
				  
				  bl.setText(Double.toString(bal));
				  }
				  else{
					  TrayNotification tray = new TrayNotification();
					 	String title = "Not a valid amount!";
				        String message = "Please enter a valid amount!";
				        //Notification notification = Notification.SUCCESS;
				        tray.setTitle(title);
				        tray.setMessage(message);
				        tray.setNotificationType(NotificationType.ERROR);
				        tray.showAndDismiss(Duration.seconds(4));
				  }
			  }
			  
			  else {
			   
				  if(amount >= finalAmount ) {
			  bal = amount-finalAmount;
			  bl.setText(Double.toString(bal));
			  }
				  else{
					  TrayNotification tray = new TrayNotification();
					 	String title = "Not a valid amount!";
				        String message = "Please enter a valid amount!";
				        //Notification notification = Notification.SUCCESS;
				        tray.setTitle(title);
				        tray.setMessage(message);
				        tray.setNotificationType(NotificationType.ERROR);
				        tray.showAndDismiss(Duration.seconds(4));
				  }
				  
			  }
		  }
		  
	  }
	 
	  @FXML
		 public void editDetail(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
		 
		  Bill selected = table.getSelectionModel().getSelectedItem();
		  if(selected == null) {
			  TrayNotification tray = new TrayNotification();
			 	String title = "Row isn't selected!";
		        String message = "Please select a row in table.";
		        //Notification notification = Notification.SUCCESS;
		        tray.setTitle(title);
		        tray.setMessage(message);
		        tray.setNotificationType(NotificationType.ERROR);
		        tray.showAndDismiss(Duration.seconds(4));
		
		  }
		  else {
			  int index = table.getSelectionModel().getSelectedIndex();
				 String sql = "SELECT * FROM `item` WHERE CategoryName = '"+selected.getItem()+"' AND BrandName = '"+selected.getBrand()+"'";
				 ResultSet rs = DbUtil.getValues(sql);
				 String  itemID = rs.getString(1);
				  qn = selected.getQuantity();
				 tx.setText(itemID);
				 cb3.setValue(qn);
				setItemID(itemID);
				setIndex(index);
		  }
		
		
		
	  }
	  @FXML
	  public void resetValues(ActionEvent e) {
		  tx.setText(null);
		  cb3.setValue(null);
	  }
	  
	  public void setIndex(int index) {
		   ind = index;
	   }
	   public int getIndex() {
		   return ind;
	   }
	   public void setItemID(String id) {
		   ItemNO = id;
	   }
	   public String getItemID() {
		   return ItemNO;
	   }
	   
	   @FXML
		 public void saveEditedDetail(ActionEvent e) throws ClassNotFoundException, SQLException {
		   String ncn,nbn;
		   Double nsp;
		   Integer nqn;
		   
		   if(cb3.getValue() == null || tx.getText().equals(null) ){
			   
			   TrayNotification tray = new TrayNotification();
			 	String title = "Row isn't selected!";
		        String message = "Press pick button and select a row.";
		        //Notification notification = Notification.SUCCESS;
		        tray.setTitle(title);
		        tray.setMessage(message);
		        tray.setNotificationType(NotificationType.ERROR);
		        tray.showAndDismiss(Duration.seconds(4));
		   }
		   else {
			   
			   nqn = cb3.getValue();
			   String sql ="SELECT * FROM `item` WHERE itemID = '"+getItemID()+"'";
			   ResultSet rs = DbUtil.getValues(sql);
			   
			   ncn = rs.getString(8);
			   nbn = rs.getString(6);
			   nsp = rs.getDouble(5);
			   double ncp = rs.getDouble(4);
				String sql1 = "UPDATE `billingitem` SET `Quantity`='"+nqn+"' WHERE `BillID`='"+billLoginController.billNo+"' AND`ItemID`= '"+getItemID()+"'";
				DbUtil.dbExecuteQuery(sql1);

				table.getItems().remove(getIndex());
				Bill bill = new Bill(ncn,nbn,nqn,nsp,nqn*nsp);
				table.getItems().add(bill);
				
				TrayNotification tray = new TrayNotification();
			 	String title = "Updated successfully!";
		        String message = "Quantity has changed to "+nqn;
		        //Notification notification = Notification.SUCCESS;
		        tray.setTitle(title);
		        tray.setMessage(message);
		        tray.setNotificationType(NotificationType.SUCCESS);
		        tray.showAndDismiss(Duration.seconds(6));
				
				int q ;
				if(nqn > qn) {
					q = nqn - qn;
					totalCostPrice = totalCostPrice + (ncp*q);
					totalSellingPrice = totalSellingPrice + (nsp*q) ;
				}
				else if(nqn == qn) {
					totalCostPrice = totalCostPrice + 0;
					totalSellingPrice = totalSellingPrice +0;
				
				}
				else {
					q = qn - nqn;
					totalCostPrice = totalCostPrice - (ncp*q);
					totalSellingPrice = totalSellingPrice - (nsp*q) ;
				}
				
				
			   
		   }
		   
			
	  }
	   
	
	  @FXML
		 public void delete(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
		
		  
		  if(cb3.getValue() == null || tx.getText().equals(null) ){
			   
			   TrayNotification tray = new TrayNotification();
			 	String title = "Row isn't selected!";
		        String message = "Press pick button and select a row.";
		        //Notification notification = Notification.SUCCESS;
		        tray.setTitle(title);
		        tray.setMessage(message);
		        tray.setNotificationType(NotificationType.ERROR);
		        tray.showAndDismiss(Duration.seconds(4));
		   }
		   else {
			   String sql1 = "DELETE FROM `billingitem` WHERE `billingitem`.`BillID` ='"+billLoginController.billNo+"' AND `billingitem`.`ItemID` = '"+getItemID()+"'";
				 DbUtil.dbExecuteQuery(sql1);

				  table.getItems().remove(getIndex());
				 //status.setText("Deleted successfully!");
				  	
				  	TrayNotification tray = new TrayNotification();
				 	String title = "Deleted successfully!";
			        String message = getItemID()+" Item deleted.";
			        //Notification notification = Notification.SUCCESS;
			        tray.setTitle(title);
			        tray.setMessage(message);
			        tray.setNotificationType(NotificationType.SUCCESS);
			        tray.showAndDismiss(Duration.seconds(6));
			        /*
			         *  tray.setOnShown( new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					});
			         */
				 
				   Double nsp, ncp;
			
				   String sql ="SELECT * FROM `item` WHERE itemID = '"+getItemID()+"'";
				   ResultSet rs = DbUtil.getValues(sql);
				   
				   ncp = rs.getDouble(4);
				   nsp = rs.getDouble(5);
				   
					totalCostPrice = totalCostPrice - (ncp*cb3.getValue());
					totalSellingPrice = totalSellingPrice - (nsp*cb3.getValue()) ;
					
		   }
		
		
	  }
		 @FXML
		 public void printBill(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
			
			 Parent root = FXMLLoader.load(getClass().getResource("/application/PBill.fxml"));
			 Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			 //PBillController.showBill(e);
		 }
		 @FXML
		 public void tosales(ActionEvent e) throws IOException {
			 
			 Parent root = FXMLLoader.load(getClass().getResource("/application/Sales.fxml"));
			 Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		 }

		 @FXML
		 public void Back(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
			 
			 String sql = "DELETE FROM `bill` WHERE `TotalNet-Price` = 0.0 AND `TotalCost-Price` = 0.0  AND  `TotalSelling-Price`= 0.0  AND  `Discount-Amount`= 0.0";
			 DbUtil.dbExecuteQuery(sql);
			
			 
			 Parent root = FXMLLoader.load(getClass().getResource("/application/billLogin.fxml"));
			 Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		 }
}
