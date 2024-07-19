package com.filemanager.controller;

import com.filemanager.model.Outcome;
import com.filemanager.service.FileProcessingService;
import com.filemanager.service.IpValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileProcessingService fileProcessingService;

    @Autowired
    private IpValidationService ipValidationService;

    @PostMapping("/process")
    public List<Outcome> processFile(@RequestParam("file") MultipartFile file,
                                     @RequestParam(value = "skipValidation", required = false) Boolean skipValidation,
                                     HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (skipValidation == null) {
            throw new IllegalArgumentException("Missing parameter: skipValidation");
        }

        String clientIp = request.getRemoteAddr();

        if (!skipValidation && !ipValidationService.isIpValid(clientIp)) {
            throw new SecurityException("Access denied for IP: " + clientIp);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return fileProcessingService.processFile(reader, skipValidation);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException("Failed to process the file", e);
        }
    }
}
