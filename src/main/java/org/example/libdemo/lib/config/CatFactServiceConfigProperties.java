package org.example.libdemo.lib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// with a record use @ConfigurationPropertiesScan on the application class
// and not @Configuration here. @Configuration does not like final classes like records.

@ConfigurationProperties("catfact-service")
public record CatFactServiceConfigProperties(String baseUrl) {
}
