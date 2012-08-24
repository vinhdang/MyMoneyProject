package model.plan;

public class Plan {

	private int id;
	private String name;
	private String account;
	private String category;
	private double amount;
	
	public Plan()
	{
		
	}
	
	public Plan(String n, String a, String c, double am)
	{
		this.name = n;
		this.account = a;
		this.category = c;
		this.amount = am;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
