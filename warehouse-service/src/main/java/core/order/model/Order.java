package core.order.model;

public class Order {
	
	private Item item;
	private long orderId;
	private Customer customer;
	
	public Order() {
		super();
	}
	
	public Order(long orderId, Customer customer, Item item) {
		this.orderId = orderId;
		this.item = item;
		this.customer = customer;
	}
	
	public Long getOrderId()
	{
		return this.orderId;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public Customer getOrderCustomer() {
		return this.customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public void setOrderItem(Item item) {
		this.item = item;
	}
	
	public Item getOrderItem() {
		return this.item;
	}
}