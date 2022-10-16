package org.example.libdemo.lib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("catfact-service")
public record CatFactServiceConfigProperties(String baseUrl) {
}
