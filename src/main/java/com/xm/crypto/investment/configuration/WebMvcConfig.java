package com.xm.crypto.investment.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc configurer.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Needed to enable RestControllers translate date to LocalDate
     *  * i.e @RequestParam("date") LocalDate date
     *
     * @param registry a registry of field formatting logic.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }

    /**
     * Open Api configuration.
     * Provides swagger: start app and go http://localhost:8080/swagger-ui/index.html
     * @return
     */
    @Bean
    public OpenAPI openApi() {
        var info = new Info()
                .title("XM Crypto")
                .description("API for XM crypto recommendation service")
                .version("v0.0.1");
        return new OpenAPI().info(info);
    }
}
