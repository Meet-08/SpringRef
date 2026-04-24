package com.meet.springref.features.travel;

import com.meet.springref.features.travel.dto.request.TravelCreateRequest;
import com.meet.springref.features.travel.dto.request.TravelUpdateRequest;
import com.meet.springref.features.travel.dto.response.TravelResponse;
import com.meet.springref.features.travel.exception.TravelValidationException;
import com.meet.springref.features.travel.mapper.TravelMapper;
import com.meet.springref.features.travel.repository.TravelRepository;
import com.meet.springref.features.travel.service.TravelService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import({TravelService.class, TravelMapper.class, TravelServiceIntegrationTest.JpaAuditingConfig.class})
class TravelServiceIntegrationTest {

    private final TravelService travelService;
    private final TravelRepository travelRepository;

    TravelServiceIntegrationTest(TravelService travelService, TravelRepository travelRepository) {
        this.travelService = travelService;
        this.travelRepository = travelRepository;
    }

    @Test
    void shouldCreateUpdateFindAndDeleteTravel() {
        TravelCreateRequest createRequest = new TravelCreateRequest(
                "Summer Escape",
                "New York",
                "Paris",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(20),
                new BigDecimal("999.99"),
                12,
                "A curated Europe trip"
        );

        TravelResponse created = travelService.create(createRequest);
        assertEquals(1L, created.id());
        assertEquals("Paris", created.destination());
        assertEquals(1, travelRepository.count());

        TravelResponse loaded = travelService.findById(created.id());
        assertEquals(created.id(), loaded.id());
        assertEquals(created.title(), loaded.title());

        TravelUpdateRequest updateRequest = new TravelUpdateRequest(
                "Summer Escape Plus",
                "New York",
                "Rome",
                LocalDate.now().plusDays(11),
                LocalDate.now().plusDays(21),
                new BigDecimal("1199.99"),
                8,
                "Updated travel plan"
        );

        TravelResponse updated = travelService.update(created.id(), updateRequest);
        assertEquals("Rome", updated.destination());
        assertEquals(new BigDecimal("1199.99"), updated.price());
        assertEquals(8, updated.availableSeats());

        List<TravelResponse> allTravels = travelService.findAll();
        assertEquals(1, allTravels.size());
        assertEquals("Summer Escape Plus", allTravels.getFirst().title());

        travelService.delete(created.id());
        assertTrue(travelRepository.findById(created.id()).isEmpty());
        assertEquals(0, travelRepository.count());
    }

    @Test
    void shouldRejectTravelWithInvalidDateRange() {
        TravelCreateRequest invalidRequest = new TravelCreateRequest(
                "Bad Trip",
                "Berlin",
                "Madrid",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(5),
                new BigDecimal("500.00"),
                6,
                "Return date is earlier than departure date"
        );

        TravelValidationException exception = assertThrows(
                TravelValidationException.class,
                () -> travelService.create(invalidRequest)
        );

        assertEquals("Return date must be on or after departure date", exception.getMessage());
        assertEquals(0, travelRepository.count());
    }

    @TestConfiguration
    @EnableJpaAuditing
    static class JpaAuditingConfig {
    }
}
