package support.edge;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import com.netflix.appinfo.AmazonInfo;

@SpringBootApplication
@Controller
@EnableZuulProxy
public class ZuulApplication {
	
    public static void main(String[] args) {
        new SpringApplicationBuilder(ZuulApplication.class).web(true).run(args);
    }
    
    @Bean
	@Profile("aws")
	public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
	    EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
	    AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
	    config.setDataCenterInfo(info);
	    info.getMetadata().put(AmazonInfo.MetaDataKey.publicHostname.getName(), info.get(AmazonInfo.MetaDataKey.publicIpv4));
	    config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
	    config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
	    return config;
	}
}

