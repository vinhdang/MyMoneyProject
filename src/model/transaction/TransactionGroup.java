package model.transaction;

import java.util.ArrayList;

public class TransactionGroup {
	private String name;
	private ArrayList<Transaction>  items;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Transaction> getItems() {
		return items;
	}
	public void setItems(ArrayList<Transaction> items) {
		this.items = items;
	}
}
