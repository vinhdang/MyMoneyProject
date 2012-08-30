package model.bill;
public class Bill{
	private int billId;
	public String billItem;
	public double billAmount;
	public String billCategory;
	public String billDueDay;
	public String billNote;
	public String billNotification;
	public String billRepeat;
	
	public Bill(int id, String item, double amount, String category, String dueday, String note, String notification, String repeat ){
		this.billId = id;
		this.billItem = item;
		this.billAmount = amount;
		this.billCategory = category;
		this.billDueDay = dueday;
		this.billNote = note;
		this.billNotification = notification;
		this.billRepeat = repeat;
	}
	
	public Bill()
	{
		
	}
	
	public int getBillId(){
		return this.billId;
	}
	public void setBillId(int id){
		this.billId = id;
	}
	
	public String getBillItem(){
		return this.billItem;
	}
	public void setBillItem(String item){
		this.billItem = item;
	}
	
	public String getBillCategory(){
		return this.billCategory;
	}
	public void setBillCategory(String category){
		this.billCategory = category;
	}
	
	public double getBillAmount(){
		return this.billAmount;
	}
	public void setBillAmount(double amount){
		this.billAmount = amount;
	}
	
	public String getBillDueDay(){
		return this.billDueDay;
	}
	public void setBillDueDay(String dueday){
		this.billDueDay = dueday;
	}
	
	public String getBillNote(){
		return this.billNote;
	}
	public void setBillNote(String note){
		this.billNote = note;
	}
	
	public String getBillNotification(){
		return this.billNotification;
	}
	public void setBillNotification(String notification){
		this.billNotification = notification;
	}
	
	public String getBillRepeat(){
		return this.billRepeat;
	}
	public void setBillRepeat(String repeat){
		this.billRepeat = repeat;
	}	
}