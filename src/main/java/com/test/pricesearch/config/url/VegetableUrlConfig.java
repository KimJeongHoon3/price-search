package com.test.pricesearch.config.url;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("vegetable.api")
public class VegetableUrlConfig extends UrlConfig{
}
