package com.filemanager.config;

import com.filemanager.filter.RequestLoggingFilter;
import com.filemanager.repository.RequestLogRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> loggingFilter(RestTemplate restTemplate, RequestLogRepository requestLogRepository) {
        FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestLoggingFilter(restTemplate, requestLogRepository));
        registrationBean.addUrlPatterns("/api/files/*");
        return registrationBean;
    }
}
