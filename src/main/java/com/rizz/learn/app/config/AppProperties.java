package com.rizz.learn.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.info")
public record AppProperties(String name, String version, boolean isAwesome) {

}
