package ru.t1.yd.ocrservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "tessdata")
public final class TessdataConfiguration {

    private String lang;

    private String path;

}
