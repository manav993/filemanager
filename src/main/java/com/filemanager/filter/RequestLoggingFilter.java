package com.filemanager.filter;

import com.filemanager.model.RequestLog;
import com.filemanager.repository.RequestLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RequestLoggingFilter implements Filter {

    private final RequestLogRepository requestLogRepository;
    private final RestTemplate restTemplate;

    public RequestLoggingFilter(RestTemplate restTemplate, RequestLogRepository requestLogRepository) {
        this.restTemplate = restTemplate;
        this.requestLogRepository = requestLogRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String clientIp = httpServletRequest.getRemoteAddr();
        String ipApiUrl = "http://ip-api.com/json/" + clientIp;
        IpApiResponse ipApiResponse = restTemplate.getForObject(ipApiUrl, IpApiResponse.class);

        String requestCountryCode = ipApiResponse != null ? ipApiResponse.getCountryCode() : "";
        String requestIpProvider = ipApiResponse != null ? ipApiResponse.getIsp() : "";

        LocalDateTime requestTimestamp = LocalDateTime.now();
        String requestUri = httpServletRequest.getRequestURI();
        long startTime = System.currentTimeMillis();

        try {
            chain.doFilter(request, response);
        } finally {
            int httpResponseCode = httpServletResponse.getStatus();
            long timeLapsed = System.currentTimeMillis() - startTime;
            logRequest(requestUri, requestTimestamp, httpResponseCode, clientIp, requestCountryCode, requestIpProvider, timeLapsed);
        }
    }

    private void logRequest(String requestUri, LocalDateTime requestTimestamp, int httpResponseCode, String requestIpAddress, String requestCountryCode, String requestIpProvider, long timeLapsed) {
        RequestLog requestLog = new RequestLog();
        requestLog.setId(UUID.randomUUID());
        requestLog.setRequestUri(requestUri);
        requestLog.setRequestTimestamp(requestTimestamp);
        requestLog.setHttpResponseCode(httpResponseCode);
        requestLog.setRequestIpAddress(requestIpAddress);
        requestLog.setRequestCountryCode(requestCountryCode);
        requestLog.setRequestIpProvider(requestIpProvider);
        requestLog.setTimeLapsed(timeLapsed);
        requestLogRepository.save(requestLog);
    }

    private static class IpApiResponse {
        private String countryCode;
        private String isp;

        // Getters and setters
        public String getCountryCode() {
            return countryCode;
        }


        public String getIsp() {
            return isp;
        }

    }
}
