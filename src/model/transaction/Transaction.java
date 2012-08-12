package model.transaction;

public class Transaction {
	private String transactionName;
	private String transactionDate;
	private Double transactionAmount;
	private String	transactionCategory;
	private String transactionAccount;
	
	public Transaction(String name, String date, Double amount, String category, String accName)
	{
		this.transactionName = name;
		this.transactionAmount = amount;
		this.transactionDate = date;
		this.transactionCategory = category;
		this.setTransactionAccount(accName);
	}
	
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionCategory() {
		return transactionCategory;
	}

	public void setTransactionCategory(String transactionCategory) {
		this.transactionCategory = transactionCategory;
	}

	public String getTransactionAccount() {
		return transactionAccount;
	}

	public void setTransactionAccount(String transactionAccount) {
		this.transactionAccount = transactionAccount;
	}
}
