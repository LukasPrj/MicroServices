package composite.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
	
	private Item orderItem;
	private long orderId;
	private Customer orderCustomer;
	
	public Order() {
		super();
	}
	
	public Order(long orderId, Customer customer, Item item) {
		this.orderId = orderId;
		this.orderItem = item;
		this.orderCustomer = customer;
	}
	
	@JsonProperty("orderId")
	public Long getOrderId()
	{
		return this.orderId;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	@JsonProperty("orderCustomer")
	public Customer getOrderCustomer() {
		return this.orderCustomer;
	}
	
	public void setOrderCustomer(Customer customer) {
		this.orderCustomer = customer;
	}
	
	@JsonProperty("orderItem")
	public void setOrderItem(Item item) {
		this.orderItem = item;
	}
	
	public Item getOrderItem() {
		return this.orderItem;
	}
}
