package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class SalesController {
	@FXML
	private Button search;
	@FXML private DatePicker from;
	
	@FXML private DatePicker to;
	
	@FXML
	private TableView<Profit> table;
	@FXML
	private TableColumn<Profit,String> Item;
	@FXML
	private TableColumn<Profit,String> Brand;
	@FXML
	private TableColumn<Profit,Integer> Quantity;
	
	@FXML
	private TableColumn<Profit,Double> Sold;
	@FXML
	private TableColumn<Profit,Double> Cost;
	@FXML
	private TableColumn<Profit,Double> Profit;
	@FXML
	private Button pr;
	@FXML
	private TextField tp , t ,fr;
	@FXML
	private TextField prof;
	@FXML
	private TextField da;
	@FXML
	private TextField se;
	@FXML
	private TextField ptc;

//ObservableList<String> list1 = FXCollections.observableArrayList("January","February","March","April","May","June","July","August","September","October","November","December");
//ObservableList<String> list2 = FXCollections.observableArrayList("January","February","March","April","May","June","July","August","September","October","November","December");
	
	
	
	
	
     public void search(ActionEvent e) throws ClassNotFoundException, SQLException {

    	 if(from.getValue() == null || to.getValue() == null) {
    		 TrayNotification tray = new TrayNotification();
 		 	String title = "Fields are empty!";
 	        String message = "Please fill the fields correctly";
 	        tray.setTitle(title);
 	        tray.setMessage(message);
 	        tray.setNotificationType(NotificationType.WARNING);
 	        tray.showAndDismiss(Duration.seconds(6));
    	 }
    	 else {
    		     fr.setText(from.getValue().toString());
    		     t.setText(to.getValue().toString());
    		 ObservableList<Profit> data  = FXCollections.observableArrayList();
    		 	Item.setCellValueFactory(new PropertyValueFactory<Profit,String>("Item"));
    			Brand.setCellValueFactory(new PropertyValueFactory<Profit,String>("Brand"));
    			Quantity.setCellValueFactory(new PropertyValueFactory<Profit,Integer>("Quantity"));
    			Sold.setCellValueFactory(new PropertyValueFactory<Profit,Double>("Sold"));
    			Cost.setCellValueFactory(new PropertyValueFactory<Profit,Double>("Cost"));
    			Profit.setCellValueFactory(new PropertyValueFactory<Profit,Double>("Profit"));
    		    /*
    		     *  String sql = "SELECT SUM(`TotalNet-Price`) FROM `bill`WHERE DATE BETWEEN '2020-03-26 00:00:00' AND '2020-03-28 00:00:00'";
    		      String sql1 = "SELECT SUM(`TotalCost-Price`) FROM `bill`WHERE DATE BETWEEN '2020-03-26 00:00:00' AND '2020-03-28 00:00:00'";
    		      
    		      ResultSet rs = DbUtil.getValues(sql);
    		      ResultSet rs1 = DbUtil.getValues(sql1);
    		      
    		      Double totalNetprice = Double.parseDouble(rs.getString(1));
    		      Double totalCostprice = Double.parseDouble(rs1.getString(1));
    		       
    		      Double total_Item_Profit = totalNetprice-totalCostprice;
    		     */
    		      
    		      
    		      
    		      String sql = "CREATE VIEW `profitTable`(\r\n" + 
    		      		"    `Item`,\r\n" + 
    		      		"    `Brand`,\r\n" + 
    		      		"    `Quantity`,\r\n" + 
    		      		"    `Cost`,\r\n" + 
    		      		"    `Sold`,\r\n" + 
    		      		"    `Profit`\r\n" + 
    		      		") AS SELECT\r\n" + 
    		      		"    `item`.`CategoryName`,\r\n" + 
    		      		"    `item`.`BrandName`,\r\n" + 
    		      		"    SUM(`billingitem`.`Quantity`),\r\n" + 
    		      		"    `item`.`Cost-Price`,\r\n" + 
    		      		"    `item`.`Selling-Price`,\r\n" + 
    		      		"    (`item`.`Selling-Price`-`item`.`Cost-Price`)*(SUM(`billingitem`.`Quantity`))\r\n" + 
    		      		"FROM\r\n" + 
    		      		"    `bill`,\r\n" + 
    		      		"    `billingitem`,\r\n" + 
    		      		"    `item`\r\n" + 
    		      		"WHERE\r\n" + 
    		      		"    `bill`.`BillID` = `billingitem`.`BillID` AND `billingitem`.`ItemID` = `item`.`ItemID` AND DATE BETWEEN '"+java.sql.Date.valueOf(from.getValue())+" 00:00:00' AND '"+java.sql.Date.valueOf(to.getValue())+" 00:00:00'\r\n" + 
    		      		"GROUP BY\r\n" + 
    		      		"    (`billingitem`.`ItemID`)";
    		      DbUtil.dbExecuteQuery(sql);
    		      /*
    		       * CREATE VIEW `profitTable`(
    		    `Item`,
    		    `Brand`,
    		    `Quantity`,
    		    `Cost`,
    		    `Sold`,
    		    `Profit`
    		) AS SELECT
    		    `item`.`CategoryName`,
    		    `item`.`BrandName`,
    		    SUM(`billingitem`.`Quantity`),
    		    `item`.`Cost-Price`,
    		    `item`.`Selling-Price`,
    		    (`item`.`Selling-Price`-`item`.`Cost-Price`)*(SUM(`billingitem`.`Quantity`))
    		FROM
    		    `bill`,
    		    `billingitem`,
    		    `item`
    		WHERE
    		    `bill`.`BillID` = `billingitem`.`BillID` AND `billingitem`.`ItemID` = `item`.`ItemID`
    		GROUP BY
    		    (`billingitem`.`ItemID`)
    		       */
    		   
    		     Double pro = 0.00; 
    		      String sql1 ="SELECT * FROM profittable";
    		      
    		       ResultSet rs = DbUtil.getValues(sql1);
    		       
    		       
    		       while(rs.next()){
    					Profit profit =  new Profit(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getDouble(4), rs.getDouble(5),rs.getDouble(6));
    					
    		           //data.add( new Profit(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getDouble(4), rs.getDouble(5),rs.getDouble(6)));
    					//table.getItems().add(profit);;
    		       	    data.add(profit);
    					pro = pro + rs.getDouble(6);
    		       }
    		            table.setItems(data);
    		           prof.setText(pro.toString());

    		           //DISCOUNT
    				String sql2 = "SELECT SUM(`Discount-Amount`) FROM `bill`WHERE DATE BETWEEN '"+java.sql.Date.valueOf(from.getValue())+" 00:00:00' AND '"+java.sql.Date.valueOf(to.getValue())+" 00:00:00';";
    				ResultSet rs1 = DbUtil.getValues(sql2);
    				Double disa = rs1.getDouble(1);
    				String totDiscount = disa.toString();
    				da.setText(totDiscount);
    				
    		       //PETTY CASH
    		       String sql3 = "SELECT SUM(`Amount`) FROM `petty-cash` WHERE `petty-cash`.`Date`  BETWEEN '"+java.sql.Date.valueOf(from.getValue())+" 00:00:00' AND '"+java.sql.Date.valueOf(to.getValue())+" 00:00:00'";
    		       ResultSet rs2 = DbUtil.getValues(sql3);
    		       Double pettyCash = rs2.getDouble(1);
    		       ptc.setText(pettyCash.toString());
    		       
    		       //SALARY
    		       String  sql4 = "SELECT SUM(`NetSalary`) FROM `paid-salary-details` WHERE `Month` BETWEEN '"+java.sql.Date.valueOf(from.getValue())+" 00:00:00' AND '"+java.sql.Date.valueOf(to.getValue())+" 00:00:00'";
    		       ResultSet rs3 = DbUtil.getValues(sql4);
    		       Double totSal = rs3.getDouble(1);
    		       se.setText(totSal.toString());
    		       
    		      Double totProf = pro - (disa+pettyCash+totSal); 
    		       tp.setText(totProf.toString());
    		       
    		       String drop = "DROP VIEW profittable";
    		       DbUtil.dbExecuteQuery(drop);
    	 }
    
     }
     
     
     
     
     
     public void restAll(ActionEvent e) {
    	/*
    	 * 
    	  TrayNotification tray = new TrayNotification();
		 	String title = "";
	        String message = "Please fill the fields correctly";
	        //Notification notification = Notification.SUCCESS;
	        tray.setTitle(title);
	        tray.setMessage(message);
	        tray.setNotificationType(NotificationType.S);
	        tray.setOnShown(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        tray.showAndDismiss(Duration.seconds(4));
    	 
    	 */
	     
	     prof.setText(null);
    	 da.setText(null);
    	 ptc.setText(null);
    	 se.setText(null);
    	 
    	 from.setValue(null);
    	 to.setValue(null);
    	 table.setItems(null);
    	 tp.setText(null);
    	 
     }
     @FXML
	 public void BackSecond(ActionEvent e) throws IOException{
		
		 
		 Parent root = FXMLLoader.load(getClass().getResource("/application/First.fxml"));
		 Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	 }
}
