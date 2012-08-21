package model.account;

public class Account {
	private int id;
	public String accountName;
	public double finalBalance;
	public String unit;
	private String descript;
	
	public Account(String name, double balance, String unit, String descript)
	{
		this.accountName = name;
		this.finalBalance = balance;
		this.unit = unit;
		this.setDescript(descript);
	}
	
	public  Account() 
	{

	}
	
	public String getAccountName() {
		return accountName;
	}
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public double getFinalBalance() {
		return finalBalance;
	}
	public void setFinalBalance(double finalBalance) {
		this.finalBalance = finalBalance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	
}
