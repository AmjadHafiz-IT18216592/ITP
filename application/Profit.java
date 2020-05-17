package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class Profit {

	private  SimpleStringProperty Item;
	private  SimpleStringProperty  Brand;
	private SimpleIntegerProperty Quantity;
	private SimpleDoubleProperty Sold;
	private  SimpleDoubleProperty Cost;
	private SimpleDoubleProperty Profit;
	
	
	 public Profit(String item, String brand, Integer quantity,Double sold, Double cost, Double profit) {
	
		  Item = new SimpleStringProperty(item);
			Brand = new SimpleStringProperty( brand);
			Quantity = new SimpleIntegerProperty(quantity);
			Cost = new SimpleDoubleProperty(cost);
		Sold = new SimpleDoubleProperty(sold);
		Profit = new SimpleDoubleProperty(profit);
	}


	public String getItem() {
		return Item.get();
	}


	public String getBrand() {
		return Brand.get();
	}


	public Integer getQuantity() {
		return Quantity.get();
	}


	public Double getSold() {
		return Sold.get();
	}


	public Double getCost() {
		return Cost.get();
	}


	public Double getProfit() {
		return Profit.get();
	}
	
	
}
