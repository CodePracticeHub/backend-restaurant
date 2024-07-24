package com.restaurantmanagement.entity.seating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.restaurantmanagement.exceptions.ResourceNotFoundException;

public class SeatingServiceImplTest {

    @InjectMocks
    private SeatingServiceImpl seatingService;

    @Mock
    private SeatingRepository seatingRepository;

    private Seating seating;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        seating = new Seating(1L, 4, "Near the window", true, LocalDateTime.now(), null);
    }

    @Test
    public void testGetAllSeating() {
        Page<Seating> page = new PageImpl<>(List.of(seating));
        Pageable pageable = PageRequest.of(0, 10);
        when(seatingRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Seating> result = seatingService.getAllSeating(pageable);

        assertEquals(1, result.getTotalElements());
        verify(seatingRepository).findAll(any(Pageable.class));
    }

    @Test
    public void testGetSeatingById() {
        when(seatingRepository.findById(anyLong())).thenReturn(Optional.of(seating));

        Seating result = seatingService.getSeatingById(1L);

        assertEquals(seating, result);
        verify(seatingRepository).findById(anyLong());
    }

    @Test
    public void testGetSeatingByIdNotFound() {
        when(seatingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            seatingService.getSeatingById(1L);
        });

        verify(seatingRepository).findById(anyLong());
    }

    @Test
    public void testDeleteSeatingById() {
        when(seatingRepository.findById(anyLong())).thenReturn(Optional.of(seating));

        seatingService.deleteSeatingById(1L);

        verify(seatingRepository).findById(anyLong());
    }

    @Test
    public void testDeleteSeatingByIdNotFound() {
        when(seatingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            seatingService.deleteSeatingById(1L);
        });

        verify(seatingRepository).findById(anyLong());
    }

    @Test
    public void testSaveSeating() {
        when(seatingRepository.save(any(Seating.class))).thenReturn(seating);

        Seating result = seatingService.saveSeating(seating);

        assertEquals(seating, result);
        verify(seatingRepository).save(any(Seating.class));
    }

    @Test
    public void testUpdateSeating() {
        when(seatingRepository.save(any(Seating.class))).thenReturn(seating);

        Seating result = seatingService.updateSeating(seating);

        assertEquals(seating, result);
        verify(seatingRepository).save(any(Seating.class));
    }

    @Test
    public void testFindByIsAvailable() {
        Page<Seating> page = new PageImpl<>(List.of(seating));
        Pageable pageable = PageRequest.of(0, 10);
        when(seatingRepository.findByisAvailable(any(Boolean.class), any(Pageable.class))).thenReturn(page);

        Page<Seating> result = seatingService.findByisAvailable(true, pageable);

        assertEquals(1, result.getTotalElements());
        verify(seatingRepository).findByisAvailable(any(Boolean.class), any(Pageable.class));
    }
}
