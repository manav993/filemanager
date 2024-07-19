package com.filemanager.service;

import com.filemanager.model.Countries;
import com.filemanager.model.Isps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IpValidationService {

    private static final String SUCCESS_STATUS = "success";
    private static final String IP_API_URL = "http://ip-api.com/json/";

    private final RestTemplate restTemplate;

    public IpValidationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isIpValid(String ip) {
        String url = IP_API_URL + ip;
        IpApiResponse response = restTemplate.getForObject(url, IpApiResponse.class);

        if (response == null || !SUCCESS_STATUS.equals(response.getStatus())) {
            return false;
        }

        boolean isBlockedCountry = isBlockedCountry(response.getCountry());
        boolean isBlockedIsp = isBlockedIsp(response.getIsp());

        return !isBlockedCountry && !isBlockedIsp;
    }

    private boolean isBlockedCountry(String country) {
        try {
            Countries blockedCountry = Countries.valueOf(country.toUpperCase());
            return blockedCountry == Countries.CHINA || blockedCountry == Countries.USA || blockedCountry == Countries.SPAIN;
        } catch (IllegalArgumentException e) {
            // If country is not in our enum list, consider it as valid
            return false;
        }
    }

    private boolean isBlockedIsp(String isp) {
        try {
            Isps blockedIsp = Isps.valueOf(isp.toUpperCase());
            return blockedIsp == Isps.AWS || blockedIsp == Isps.GCP || blockedIsp == Isps.Azure;
        } catch (IllegalArgumentException e) {
            // If ISP is not in our enum list, consider it as valid
            return false;
        }
    }

    @Getter
    @Setter
    private static class IpApiResponse {
        private String status;
        private String country;
        private String isp;
    }
}
