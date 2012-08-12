package model.category;

import java.io.Serializable;

public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String categoryName;
	public String categoryType;
	public String categoryDescription;
	
	public Category(String name, String type, String description)
	{
		this.categoryName = name;
		this.categoryType = type;
		this.categoryDescription = description;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
}
