package composite.order.service;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import composite.order.model.Order;
import composite.order.model.Shipment;

@Component
public class OrderCompositeIntegration {

	private static final Logger LOG = LoggerFactory.getLogger(OrderCompositeIntegration.class);
	
	@Autowired
	Util util;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public ResponseEntity<Order> placeOrder(long orderId) {
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
	
	public ResponseEntity<Shipment> placeShipment(long orderId) {
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
}
