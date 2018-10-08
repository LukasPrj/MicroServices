package composite.order.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import composite.order.model.Order;
import composite.order.model.Shipment;
import composite.order.service.Util;

@RibbonClient(name="shipping-service")
@Component
@EnableCircuitBreaker
public class ShipmentConsumer {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	Util util;

	private static final Logger LOG = LoggerFactory.getLogger(ShipmentConsumer.class);

	@HystrixCommand
	public ResponseEntity<?> placeOrderShipment(long orderId) {
		String url = "http://shipping-service" + "/ship/" + orderId;
		LOG.debug("Getting Order from URL: {}", url);

		ResponseEntity<String> result = this.restTemplate.getForEntity(url, String.class);
		LOG.debug("Get Order http-status: {}", result.getStatusCode());
		LOG.debug("Get Order body: {}", result.getBody());

		Shipment shipment = util.response2shipment(result);
		LOG.debug("GetOrder.id: {}", shipment.getOrderId());

		return util.createOkResponse(shipment);
	}

}
