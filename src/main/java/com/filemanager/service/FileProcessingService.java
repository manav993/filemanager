package com.filemanager.service;

import com.filemanager.model.Entry;
import com.filemanager.model.Outcome;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcessingService {

    public List<Outcome> processFile(BufferedReader reader, boolean skipValidation) throws IOException {
        List<Outcome> outcomes = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (!skipValidation && parts.length != 7) {
                throw new IllegalArgumentException("Invalid file format");
            }

            Entry entry = new Entry();
            entry.setUuid(parts[0]);
            entry.setId(parts[1]);
            entry.setName(parts[2]);
            entry.setLikes(parts[3]);
            entry.setTransport(parts[4]);
            entry.setAvgSpeed(Double.parseDouble(parts[5]));
            entry.setTopSpeed(Double.parseDouble(parts[6]));

            outcomes.add(new Outcome(entry.getName(), entry.getTransport(), entry.getTopSpeed()));
        }
        return outcomes;
    }
}
