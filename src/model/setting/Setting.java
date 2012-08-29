package model.setting;

public class Setting {
	private int id;
	private String item;
	private String value;
	
	public Setting()
	{
		
	}
	
	public Setting(int i, String name, String v)
	{
		this.id = i;
		this.item = name;
		this.value = v;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
