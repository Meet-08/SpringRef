package com.meet.springref.features.travel.controller;

import com.meet.springref.features.travel.dto.response.TravelResponse;
import com.meet.springref.features.travel.service.TravelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TravelController.class)
@AutoConfigureMockMvc(addFilters = false)
class TravelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelService travelService;

    @Test
    void createShouldReturnCreatedTravel() throws Exception {
        TravelResponse response = new TravelResponse(
                1L,
                "Summer Escape",
                "New York",
                "Paris",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(20),
                new BigDecimal("999.99"),
                12,
                "A curated Europe trip",
                Instant.parse("2026-04-09T10:15:30Z"),
                Instant.parse("2026-04-09T10:15:30Z")
        );

        when(travelService.create(org.mockito.ArgumentMatchers.any())).thenReturn(response);

        mockMvc.perform(post("/api/travels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Summer Escape",
                                  "origin": "New York",
                                  "destination": "Paris",
                                  "departureDate": "%s",
                                  "returnDate": "%s",
                                  "price": 999.99,
                                  "availableSeats": 12,
                                  "description": "A curated Europe trip"
                                }
                                """.formatted(LocalDate.now().plusDays(10), LocalDate.now().plusDays(20))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.title").value("Summer Escape"))
                .andExpect(jsonPath("$.data.destination").value("Paris"));

        verify(travelService).create(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void findAllShouldReturnTravelList() throws Exception {
        when(travelService.findAll()).thenReturn(List.of(new TravelResponse(
                2L,
                "Business Trip",
                "London",
                "Tokyo",
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(12),
                new BigDecimal("1450.00"),
                4,
                "Work and leisure",
                Instant.parse("2026-04-09T10:15:30Z"),
                Instant.parse("2026-04-09T10:20:30Z")
        )));

        mockMvc.perform(get("/api/travels"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(2L))
                .andExpect(jsonPath("$.data[0].origin").value("London"))
                .andExpect(jsonPath("$.data[0].destination").value("Tokyo"));

        verify(travelService).findAll();
    }
}
