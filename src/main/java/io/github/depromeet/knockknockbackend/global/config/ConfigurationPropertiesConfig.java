package io.github.depromeet.knockknockbackend.global.config;

import io.github.depromeet.knockknockbackend.global.property.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(JwtProperties.class)
@Configuration
public class ConfigurationPropertiesConfig {
}
