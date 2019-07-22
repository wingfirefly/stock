package vip.linhs.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class StockApplication {

    private static final Logger logger = LoggerFactory.getLogger(StockApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StockApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        StockApplication.logger.info("http://127.0.0.1:{}", environment.getProperty("server.port"));
    }

}
