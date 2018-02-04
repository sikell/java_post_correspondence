package de.sikeller.theoretical.postcorrespondence.rest;

import de.sikeller.theoretical.postcorrespondence.calc.BruteForceImpl;
import de.sikeller.theoretical.postcorrespondence.calc.CorrespondenceCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class Config extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public CorrespondenceCalculator correspondenceCalculator() {
        return new BruteForceImpl();
    }

}
