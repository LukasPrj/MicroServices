package core.shipment.service;

import java.util.Random;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.shipment.model.Shipment;

@RestController
public class ShipmentService {
	@RequestMapping("/ship/{orderId}")
	public Shipment shipOrder(@PathVariable long orderId) {
		return new Shipment (new Random().nextInt(1000), orderId, "Waterloolaan 16, Brussels");
	}
	
}
