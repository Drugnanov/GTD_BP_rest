package GTD.restapi.config;

import GTD.restapi.util.filters.HttpLoggerFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
//import GTD.restapi.web.SpringBootServletInitializer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

//import org.springframework.web.filter.CommonsRequestLoggingFilter;
//import org.springframewsork.web.servlet.HandlerInterceptor;
//
//import javax.servlet.Filter;
//import java.util.Arrays;
@Configuration
@ComponentScan(basePackages = "GTD")
@EnableAutoConfiguration
@PropertySource("classpath:gtd.api.properties")
public class ApplicationConfig extends SpringBootServletInitializer { //

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public FilterRegistrationBean httpLoggerFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpLoggerFilter());
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.addUrlPatterns("/*");
        return registration;
    }

    private static Class<ApplicationConfig> applicationClass = ApplicationConfig.class;
}
