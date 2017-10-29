package core.order.service;

import java.util.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.order.model.Customer;
import core.order.model.Item;
import core.order.model.Order;

@RestController
public class WarehouseService {

    @RequestMapping("/placeorder/{item}")
    public Order getOrder(@PathVariable String item) {
        return new Order(new Random().nextLong(),new Item(new Random().nextInt(),item), 
        		new Customer("Accenture","Brussels"));
    }
}
