package composite.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class OrderCompositeServiceApplication {

	public static void main(String[] args) {
        SpringApplication.run(OrderCompositeServiceApplication.class, args);
    }
	
	@Configuration
	public class Config {
		@LoadBalanced
		@Bean
		RestTemplate restTemplate(){
			return new RestTemplate();
		}
	}
}
