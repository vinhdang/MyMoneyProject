package model.account;

public class Account {
	public String accountName;
	public double finalBalance;
	public String unit;
	
	public Account(String name, double balance, String unit)
	{
		this.accountName = name;
		this.finalBalance = balance;
		this.unit = unit;
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
	
	
}
