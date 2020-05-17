package application;

import java.awt.Button;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bill {

	private  SimpleStringProperty Item;
	//private final SimpleStringProperty ItemNo;
	private   SimpleStringProperty Brand;
	private  SimpleIntegerProperty Quantity;
	

	private  SimpleDoubleProperty Rate;
	private  SimpleDoubleProperty Cost;
	//private final SimpleDoubleProperty givenAmount;
	private Button button;

	public Bill(String item, String brand, Integer quantity,Double rate, Double cost) {//Double givenAmount,String ItemNo 
		
		Item = new SimpleStringProperty(item);
		Brand = new SimpleStringProperty( brand);
		Quantity = new SimpleIntegerProperty(quantity);
		Rate = new SimpleDoubleProperty(rate);
		Cost = new SimpleDoubleProperty(cost);
		this.button = new Button("D");
		//this.U = new Button("U");
		//ItemNo = new SimpleStringProperty(itemNo);
		//this.givenAmount = new SimpleDoubleProperty(givenAmount);
		
	}
	
	

	



	public Button getButton() {
		return button;
	}







	public void setButton(Button button) {
		this.button = button;
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

	public double getRate() {
		return Rate.get();
	}

	public double getCost() {
		return  Cost.get();
	}

	/*
	 * public double getgivenAmount() {
		return  givenAmount.get();
		
		
	}
	 */
	public void setQuantity(Integer quantity) {
		Quantity = new SimpleIntegerProperty(quantity);
	}
	
	
}
