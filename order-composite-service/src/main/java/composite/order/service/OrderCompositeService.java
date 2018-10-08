package composite.order.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import composite.order.consumer.ShipmentConsumer;
import composite.order.consumer.WarehouseConsumer;
import composite.order.model.Order;
import composite.order.model.OrderAggregated;
import composite.order.model.Shipment;

@RestController
public class OrderCompositeService {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderCompositeService.class);
	
	@Autowired
	private Util util;
	
	@Autowired
	private ShipmentConsumer shipConsumer;
	
	@Autowired
	private WarehouseConsumer wConsumer;
	
	@RequestMapping("/")
	public String alive() {
		LOG.info("Received alive check at :" + new Date().toString());
		return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"Hello from OrderAPI\"}";
	}
	
	@RequestMapping("/placeOrder/{orderId}")
	public ResponseEntity<?> placeOrder(@PathVariable long orderId) {
		LOG.info("Received placement of order " + orderId + " at " + new Date());
		
		Order order = null;
		
		@SuppressWarnings("unchecked")
		ResponseEntity<Order> orderResult = (ResponseEntity<Order>) wConsumer.placeOrderWarehouse(orderId);
		
		if (!orderResult.getStatusCode().is2xxSuccessful()){
			LOG.error("Call to warehouse service failed: {)", orderResult.getStatusCode());
			return util.createResponse("An error has occurred during the processing of the WarehouseService", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else
			order = orderResult.getBody();
		
		Shipment shipment = null;
		
		@SuppressWarnings("unchecked")
		ResponseEntity<Shipment> shipmentResult = (ResponseEntity<Shipment>) shipConsumer.placeOrderShipment(orderId);
		
		if (!shipmentResult.getStatusCode().is2xxSuccessful()) {
			LOG.error("Call to shipment service failed: {)", orderResult.getStatusCode());
			return util.createResponse("An error has occurred during the processing of the ShippingService", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else
			shipment = shipmentResult.getBody();
		
		OrderAggregated orderAgg = new OrderAggregated(order, shipment);
		return util.createOkResponse(orderAgg);
	}
}
