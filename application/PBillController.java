package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PBillController{

	
	@FXML private  TableView<Bill> table;
	@FXML private  TableColumn<Bill, String> Item;
	@FXML private   TableColumn<Bill, String> Brand;
	@FXML private  TableColumn<Bill, Double> Rate;
	@FXML private   TableColumn<Bill, Integer> Quantity;
	@FXML private  TableColumn<Bill,Double> Cost;

			@FXML
			private  TextField billNo,ename,date , noOfItems , tot , discount,netAmount,balance;
			
	/*
	 * @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		

	}
	 */
			
			public void showBill(ActionEvent a) throws ClassNotFoundException, SQLException {
				
				//fr.setText(from.getValue().toString());
				   // t.setText(to.getValue().toString());
				String bno = Integer.toString(billLoginController.billNo);
				billNo.setText(bno);
				
				String sqlE = "SELECT `employee`.`fname`, `employee`.`lname`  FROM `bill`,`employee` WHERE `BillID` = '"+billLoginController.billNo+"' AND `employee`.`EID` = `bill`.`EID`";
				ResultSet rsE = DbUtil.getValues(sqlE);
				
				ename.setText(rsE.getString(1)+" "+rsE.getString(2));
				
				String timeStamp = new SimpleDateFormat("yyyy"+"/"+"MM"+"/"+"dd"+" "+"HH"+":"+"mm"+":"+"ss").format(Calendar.getInstance().getTime());
				date.setText(timeStamp);
				
				
				tot.setText(Double.toString(FirstController.totalSellingPrice));
				 discount.setText(Double.toString(FirstController.diAmount));
				 netAmount.setText(Double.toString(FirstController.finalAmount));
				 balance.setText(Double.toString(FirstController.bal));
				 
				 
				 
				 
				 
				 ObservableList<Bill> data1  = FXCollections.observableArrayList();
					
					Item.setCellValueFactory(new PropertyValueFactory<Bill,String>("Item"));
					Brand.setCellValueFactory(new PropertyValueFactory<Bill,String>("Brand"));
					Quantity.setCellValueFactory(new PropertyValueFactory<Bill,Integer>("Quantity"));
					Rate.setCellValueFactory(new PropertyValueFactory<Bill,Double>("Rate"));
					Cost.setCellValueFactory(new PropertyValueFactory<Bill,Double>("Cost"));
					

					String sql = "CREATE VIEW `printBill`(\r\n" + 
							"    		      		    `Item`, \r\n" + 
							"    		      		    `Brand`,\r\n" + 
							"    						 `Rate`,\r\n" + 
							"    		      		    `Quantity`, \r\n" + 
							"    		      		    `Cost`\r\n" + 
							"    		      		 \r\n" + 
							"    		      		) AS\r\n" + 
							"SELECT \r\n" + 
							"				     		 `item`.`CategoryName`,\r\n" + 
							"				     		 `item`.`BrandName`,\r\n" + 
							"                             `item`.`Selling-Price`,\r\n" + 
							"				     		`billingitem`.`Quantity`,\r\n" + 
							"				     		(`item`.`Selling-Price`*`billingitem`.`Quantity`)\r\n" + 
							"				 \r\n" + 
							"				     		FROM `bill`,`billingitem`, `item`\r\n" + 
							"				     		WHERE\r\n" + 
							"				     		   `bill`.`BillID` = '"+billLoginController.billNo+"'  AND `billingitem`.`ItemID` = `item`.`ItemID` AND `bill`.`BillID` = `billingitem`.`BillID`\r\n" + 
							"				     		";
					
					DbUtil.dbExecuteQuery(sql);
					 
					String sql1 ="SELECT * FROM `printBill` ORDER BY `Quantity`";

	    		     ResultSet rs = DbUtil.getValues(sql1);
	    		       
	    		       
					do{
						 Bill bill = new Bill(rs.getString(1),rs.getString(2),rs.getInt(4),rs.getDouble(3),rs.getDouble(5));
								
	    		       	    data1.add(bill);
	    					
	    		       } while(rs.next());
	    		            table.setItems(data1);
					
	    		            String sqlcount = "SELECT COUNT(`Item`) FROM `printBill` ";
	    		            ResultSet rscount = DbUtil.getValues(sqlcount);
	    		            noOfItems.setText(Integer.toString(rscount.getInt(1)));
	    		            
	    	    		       String drop = "DROP VIEW printBill";
	    	    		       DbUtil.dbExecuteQuery(drop);
			}
	
}

