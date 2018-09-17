package support.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

import com.netflix.appinfo.AmazonInfo;

@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(EurekaApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}
	
	@Bean
    @Profile("aws")
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        logger.info("Setting AmazonInfo on EurekaInstanceConfigBean");
        final EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(inetUtils) {

	   //needed only when Eureka server instance binds to EIP
            @Scheduled(initialDelay = 10000L, fixedRate = 30000L)
            public void refreshInfo() {
                logger.debug("Checking datacenter info changes");
                AmazonInfo newInfo = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
                if (!this.getDataCenterInfo().equals(newInfo)) {
                    logger.info("Updating datacenterInfo");
                    ((AmazonInfo) this.getDataCenterInfo()).setMetadata(newInfo.getMetadata());
                }
            }

            private AmazonInfo getAmazonInfo() {
                return (AmazonInfo) getDataCenterInfo();
            }

            @Override
            public String getHostname() {
                AmazonInfo info = getAmazonInfo();
                final String publicHostname = info.get(AmazonInfo.MetaDataKey.publicHostname);
                return this.isPreferIpAddress() ?
                        info.get(AmazonInfo.MetaDataKey.localIpv4) :
                        publicHostname == null ?
                                info.get(AmazonInfo.MetaDataKey.localHostname) : publicHostname;
            }

            @Override
            public String getHostName(final boolean refresh) {
                return getHostname();
            }

            @Override
            public String getHomePageUrl() {
                return super.getHomePageUrl();
            }

           
        };

        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("cloudconfig");
        instance.setDataCenterInfo(info);

        return instance;
    }
}