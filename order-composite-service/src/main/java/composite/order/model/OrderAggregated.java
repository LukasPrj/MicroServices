package composite.order.model;

public class OrderAggregated {
	
	private Order order;
	private Shipment shipment;
	// private BillStatement bill;
	
	public OrderAggregated() {
		super();
	}
	
	public OrderAggregated(Order order, Shipment shipment) {
		this.order = order;
		this.shipment = shipment;
	}
	
	public Order getOrder() {
		return this.order;
	}
	
	public Shipment getShipment() {
		return this.shipment;
	}

}
