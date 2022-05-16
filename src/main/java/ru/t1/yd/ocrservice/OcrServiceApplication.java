package ru.t1.yd.ocrservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.t1.yd.ocrservice.configuration.TessdataConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(TessdataConfiguration.class)
public class OcrServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcrServiceApplication.class, args);
    }

}
