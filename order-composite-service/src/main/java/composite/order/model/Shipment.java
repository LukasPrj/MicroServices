package composite.order.model;

import java.util.Date;

public class Shipment {
	
	private long shipmentId;
	private long orderId;
	private Date shipmentDate;
	private String shippingAddress;
	
	public Shipment() {
		super();
	}
	
	public Shipment(long shipmentId, long orderId, String shippingAddress) {
		this.shipmentId = shipmentId;
		this.orderId = orderId;
		this.shipmentDate = new Date();
		this.shippingAddress = shippingAddress;
	}
	
	public long getOrderId () {
		return this.orderId;
	}
	
	public void setOrderId (long orderId) {
		this.orderId = orderId;
	}
	
	public long getShipmentId() {
		return this.shipmentId;
	}
	
	public void setShipmentId(long shipmentId) {
		this.shipmentId = shipmentId;
	}
	
	public Date getShippingDate() {
		return this.shipmentDate;
	}
	
	public void setShippingDate(Date shippingDate) {
		this.shipmentDate = shippingDate;
	}
	public String getShippingAddress() {
		return this.shippingAddress;
	}

}
