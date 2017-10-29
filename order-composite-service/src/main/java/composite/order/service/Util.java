package composite.order.service;

import java.io.IOException;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import composite.order.model.Order;
import composite.order.model.Shipment;

@Component
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    @Autowired
    private LoadBalancerClient loadBalancer;
    private ObjectReader orderReader = null;
    private ObjectReader shipmentReader = null;

    /**
     *      *
     * @param serviceId
     * @param fallbackUri
     * @return
     */
    public URI getServiceUrl(String serviceId) {
        URI uri = null;
        try {
            ServiceInstance instance = loadBalancer.choose(serviceId);
            uri = instance.getUri();
            LOG.debug("Resolved serviceId '{}' to URL '{}'.", serviceId, uri);

        } catch (RuntimeException e) {
            // Eureka not available
            LOG.error("Cannot get service URL for: " + serviceId);
            throw new RuntimeException(e);
        }
        return uri;
    }

    public <T> ResponseEntity<T> createOkResponse(T body) {
        return createResponse(body, HttpStatus.OK);
    }

    public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }

	public Order response2Order(ResponseEntity<String> resultStr) {
		try {
			return getOrderReader().readValue(resultStr.getBody());
		}
		catch(IOException e) {
			LOG.error("Error while converting JSON to Order", e);
			throw new RuntimeException(e);
		}
	}
	
	private ObjectReader getOrderReader() {

        if (orderReader != null) return orderReader;

        ObjectMapper mapper = new ObjectMapper();
        return orderReader = mapper.readerFor(Order.class);
    }

	public Shipment response2shipment(ResponseEntity<String> resultStr) {
		try {
			return getShipmentReader().readValue(resultStr.getBody());
		}
		catch(IOException e) {
			LOG.error("Error while converting JSON to Shipment", e);
			throw new RuntimeException(e);
		}
	}
	
	private ObjectReader getShipmentReader() {
		if (shipmentReader != null) return shipmentReader;
		
		ObjectMapper mapper = new ObjectMapper();
		return shipmentReader = mapper.readerFor(Shipment.class);
	}
}

