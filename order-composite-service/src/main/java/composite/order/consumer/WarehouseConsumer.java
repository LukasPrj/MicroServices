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
import composite.order.service.Util;

@RibbonClient(name="warehouse-service")
@Component
@EnableCircuitBreaker
public class WarehouseConsumer {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	Util util;
	
	private static final Logger LOG = LoggerFactory.getLogger(WarehouseConsumer.class);
	
	@HystrixCommand
	public ResponseEntity<?> placeOrderWarehouse(long orderId) {
		String url = "http://warehouse-service" + "/placeorder/" + orderId;
        LOG.debug("Getting Order from URL: {}", url);

        ResponseEntity<String> result = this.restTemplate.getForEntity(url, String.class);
        LOG.debug("Get Order http-status: {}", result.getStatusCode());
        LOG.debug("Get Order body: {}", result.getBody());

        Order order = util.response2Order(result);
        LOG.debug("GetOrder.id: {}", order.getOrderId());

        return util.createOkResponse(order);
	}

}
