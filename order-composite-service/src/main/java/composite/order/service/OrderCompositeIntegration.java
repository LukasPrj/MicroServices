package composite.order.service;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import composite.order.model.Order;
import composite.order.model.Shipment;

@Component
public class OrderCompositeIntegration {

	private static final Logger LOG = LoggerFactory.getLogger(OrderCompositeIntegration.class);
	
	@Autowired
	Util util;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@HystrixCommand (fallbackMethod = "placeFallBackOrder")
	public ResponseEntity placeOrder(long orderId) {
        URI uri = util.getServiceUrl("warehouse-service");
        String url = uri.toString() + "/placeorder/" + orderId;
        LOG.debug("Getting Order from URL: {}", url);

        ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
        LOG.debug("Get Order http-status: {}", resultStr.getStatusCode());
        LOG.debug("Get Order body: {}", resultStr.getBody());

        Order order = util.response2Order(resultStr);
        LOG.debug("GetOrder.id: {}", order.getOrderId());

        return util.createOkResponse(order);
    }
	
	public ResponseEntity placeFallBackOrder(long orderID, Throwable t) {
		LOG.error("Fallback method for placing an order. Handled exception for id " + orderID + 
				". The original exception was " + t.toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Fallback method for placing an order. Handled exception for id " + orderID + 
				". The original exception was " + t.toString());
	}
	
	@HystrixCommand (fallbackMethod = "placeFallBackShipment")
	public ResponseEntity placeShipment(long orderId) {
		URI uri = util.getServiceUrl("shipping-service");
		String url = uri.toString() + "/ship/" + orderId;
		LOG.debug("Shipping order from URL: {}", url);
		
		ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
		LOG.debug("Get Shipment http-status: {}", resultStr.getStatusCode());
        LOG.debug("Get Shipment body: {}", resultStr.getBody());
        
        Shipment shipment = util.response2shipment(resultStr);
        LOG.debug("GetShipment.id: {}", shipment.getShipmentId());
        
        return util.createOkResponse(shipment);
	}
	
	public ResponseEntity placeFallBackShipment(long orderID, Throwable t) {
		LOG.error("Fallback method for placing a shipment. Handled exception for id " + orderID + 
				". The original exception was " + t.toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Fallback method for placing a shipment. Handled exception for id " + orderID + 
				". The original exception was " + t.toString());
	}
}
