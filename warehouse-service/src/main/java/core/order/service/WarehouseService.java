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
    public Order getOrder(@PathVariable int	 item) {
        return new Order(new Random().nextInt(1000), new Customer("Accenture","Brussels"),
        		new Item(item,"An awesome item; so much wow!"));
    }
}
