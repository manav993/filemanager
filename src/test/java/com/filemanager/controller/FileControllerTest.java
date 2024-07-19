package com.filemanager.controller;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.filemanager.service.FileProcessingService;
import com.filemanager.service.IpValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
@WireMockTest(httpPort = 8080)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileProcessingService fileProcessingService;

    @MockBean
    private IpValidationService ipValidationService;

    @Before
    public void setUp() {
        ipValidationService = new IpValidationService(new RestTemplate());
    }

    @Test
    public void testProcessFile_Success() throws Exception {
        stubFor(get(urlEqualTo("/json/127.0.0.1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"status\":\"success\",\"country\":\"US\",\"isp\":\"Google LLC\"}")));

        when(ipValidationService.isIpValid(any())).thenReturn(false);

        mockMvc.perform(multipart("/api/files/process")
                        .file("file", "sample data".getBytes())
                        .param("skipValidation", "false"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testProcessFile_InvalidFile() throws Exception {
        mockMvc.perform(multipart("/api/files/process")
                        .param("skipValidation", "false"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testProcessFile_MissingSkipValidation() throws Exception {
        mockMvc.perform(multipart("/api/files/process")
                        .file("file", "sample data".getBytes()))
                .andExpect(status().isBadRequest());
    }
}
