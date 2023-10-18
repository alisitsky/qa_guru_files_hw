package com.alisitsky.tests;

import com.alisitsky.model.DudeModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonParsingTest {
    private String pathToJson = "src/test/resources/hw/Dude.json";

    @Test
    @DisplayName("JSON содержит корректные значения")
    void jsonHasValues() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DudeModel dude = objectMapper.readValue(new File(pathToJson), DudeModel.class);

        assertEquals("Dude", dude.getTitle());
        assertEquals("Lebowski", dude.getPersonalData().getLastName());
        assertEquals(1991, dude.getYear());
    }
}