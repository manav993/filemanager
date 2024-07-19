package com.filemanager.service;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

@WireMockTest(httpPort = 8080)
public class IpValidationServiceTest {

    private IpValidationService ipValidationService;

    @Before
    public void setUp() {
        ipValidationService = new IpValidationService(new RestTemplate());
    }

    @Test
    public void testIsIpValid_Success() {
        stubFor(get(urlEqualTo("/json/8.8.8.8"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"status\":\"success\",\"country\":\"US\",\"isp\":\"Google LLC\"}")));

        boolean isValid = ipValidationService.isIpValid("8.8.8.8");
        assertFalse(isValid);
    }

    @Test
    public void testIsIpValid_Failure() {
        stubFor(get(urlEqualTo("/json/8.8.4.4"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"status\":\"success\",\"country\":\"CN\",\"isp\":\"China Telecom\"}")));

        boolean isValid = ipValidationService.isIpValid("8.8.4.4");
        assertFalse(isValid);
    }

    @Test
    public void testIsIpValid_UnknownIsp() {
        stubFor(get(urlEqualTo("/json/1.1.1.1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"status\":\"success\",\"country\":\"AU\",\"isp\":\"Cloudflare\"}")));

        boolean isValid = ipValidationService.isIpValid("1.1.1.1");
        assertTrue(isValid);
    }
}
