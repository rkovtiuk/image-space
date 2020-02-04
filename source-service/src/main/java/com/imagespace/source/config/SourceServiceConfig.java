package com.imagespace.source.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "source.properties")
public class SourceServiceConfig {

    int sourceHigh;
    int sourceWidth;

    int sourcePreviewHigh;
    int sourcePreviewWidth;

    int sourceSmallHigh;
    int sourceSmallWidth;

}
