package model.transaction;

public class Transaction {
	private int transactionId;
	private String transactionItem;
	private Double transactionAmount;
	private String transactionAccount;
	private String transactionCategory;
	private String transactionNote;
	private String transactionDate;
	private String transactionPaymode;
	private String transactionRepeat;
	
	public Transaction(String item, String date, Double amount,  String category, String account, String note, String paymode, String repeat)
	{
		this.transactionItem = item;
		this.transactionAmount = amount;
		this.transactionDate = date;
		this.transactionAccount = account;
		this.transactionCategory = category;
		this.transactionNote = note;
		this.transactionPaymode = paymode;
		this.transactionRepeat = repeat;		
	}
	public Transaction()
	{
		
	}	

	/*ID*/
	public int getTransactionId()
	{
		return transactionId;
	}
	public void setTransactionId(int transactionId)
	{
		this.transactionId= transactionId;
	}
	/*Name*/
	/*Item*/
	public String getTransactionItem() {
		return transactionItem;
	}
	public void setTransactionItem(String transactionItem) {
		this.transactionItem = transactionItem;
	}
	/*Amount*/
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	/*Account*/
	public String getTransactionAccount() {
		return transactionAccount;
	}
	public void setTransactionAccount(String transactionAccount) {
		this.transactionAccount = transactionAccount;
	}
	/*Category*/
	public String getTransactionCategory() {
		return transactionCategory;
	}
	public void setTransactionCategory(String transactionCategory) {
		this.transactionCategory = transactionCategory;
	}
	/*Note*/
	public String getTransactionNote() {
		return transactionNote;		
	}
	public void setTransactionNote(String transactionNote){
		this.transactionNote=transactionNote;
	}
	/*Date*/
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	/*Pay mode*/
	public String getTransactionPaymode(){
		return transactionPaymode;
	}
	public void setTransactionPaymode(String transactionPaymode){
		this.transactionPaymode = transactionPaymode;
	}
	/*Repeat*/
	public String getTransactionRepeat(){
		return transactionRepeat;
	}
	public void setTransactionRepeat(String transactionRepeat)
	{
		this.transactionRepeat = transactionRepeat;
	}	
}
