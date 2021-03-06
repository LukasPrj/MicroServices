package composite.order.model;

public class Item {
	
	private int id;
	private String description;
	
	public Item() {
		super();
	}
	
	public Item(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String getDescription () {
		return this.description;
	}
	
	public void setDescription (String description) {
		this.description = description;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
