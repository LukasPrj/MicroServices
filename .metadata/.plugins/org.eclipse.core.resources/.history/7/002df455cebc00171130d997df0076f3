package composite.order.service;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import composite.order.model.Order;
import composite.order.model.OrderAggregated;
import composite.order.model.Shipment;

@RestController
public class OrderCompositeService {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderCompositeService.class);
	
	@Autowired
	private OrderCompositeIntegration integration;
	@Autowired
	private Util util;
	
	@RequestMapping("/")
	public String alive() {
		LOG.info("Received alive check at :" + new Date().toString());
		return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"Hello from OrderAPI\"}";
	}
	
	@RequestMapping("/placeOrder/{orderId}")
	public ResponseEntity<OrderAggregated> placeOrder(@PathVariable long orderId) {
		return null;
		/*Order order = null;
		ResponseEntity<Order> orderResult = integration.placeOrder(orderId);
		if (!orderResult.getStatusCode().is2xxSuccessful())
			LOG.error("Call to warehouse service failed: {)", orderResult.getStatusCode());
		else
			order = orderResult.getBody();
		
		Shipment shipment = null;
		ResponseEntity<Shipment> shipmentResult = integration.placeShipment(orderId);
		if (!orderResult.getStatusCode().is2xxSuccessful())
			LOG.error("Call to shipment service failed: {)", orderResult.getStatusCode());
		else
			shipment = shipmentResult.getBody();
		
		OrderAggregated orderAgg = new OrderAggregated(order, shipment);
		return util.createOkResponse(orderAgg);*/
	}
}
