package com.filemanager.service;

import com.filemanager.model.Outcome;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.*;

public class FileProcessingServiceTest {

    private FileProcessingService fileProcessingService;

    @Before
    public void setUp() {
        fileProcessingService = new FileProcessingService();
    }

    @Test
    public void testProcessFile_ValidInput() throws Exception {
        String input = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n" +
                "3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n" +
                "1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        List<Outcome> outcomes = fileProcessingService.processFile(reader, false);

        assertEquals(3, outcomes.size());
        assertEquals("John Smith", outcomes.get(0).getName());
        assertEquals("Rides A Bike", outcomes.get(0).getTransport());
        assertEquals("12.1", outcomes.get(0).getTopSpeed());
    }

    @Test
    public void testProcessFile_EmptyInput() throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader(""));

        List<Outcome> outcomes = fileProcessingService.processFile(reader, false);

        assertEquals(0, outcomes.size());
    }

    @Test
    public void testProcessFile_MissingFields() throws Exception {
        String input = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        List<Outcome> outcomes = fileProcessingService.processFile(reader, false);

        assertEquals(0, outcomes.size());
    }

    @Test
    public void testProcessFile_InvalidUUID() throws Exception {
        String input = "invalid-uuid|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        List<Outcome> outcomes = fileProcessingService.processFile(reader, false);

        assertEquals(0, outcomes.size());
    }

    @Test
    public void testProcessFile_InvalidSpeeds() throws Exception {
        String input = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|six|twelve\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        List<Outcome> outcomes = fileProcessingService.processFile(reader, false);

        assertEquals(0, outcomes.size());
    }

    @Test
    public void testProcessFile_ExcessiveData() throws Exception {
        String input = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1|Extra Field\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        List<Outcome> outcomes = fileProcessingService.processFile(reader, false);

        assertEquals(0, outcomes.size());
    }

    @Test
    public void testProcessFile_SpecialCharacters() throws Exception {
        String input = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|Jöhn Smïth|Likes Apricots|Rides A Bike|6.2|12.1\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        List<Outcome> outcomes = fileProcessingService.processFile(reader, false);

        assertEquals(1, outcomes.size());
        assertEquals("Jöhn Smïth", outcomes.get(0).getName());
        assertEquals("Rides A Bike", outcomes.get(0).getTransport());
        assertEquals("12.1", outcomes.get(0).getTopSpeed());
    }
}
