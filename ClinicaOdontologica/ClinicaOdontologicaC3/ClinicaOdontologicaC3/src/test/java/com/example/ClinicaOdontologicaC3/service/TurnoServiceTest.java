package com.example.ClinicaOdontologicaC3.service;
import com.example.ClinicaOdontologicaC3.Dto.TurnoDTO;
import com.example.ClinicaOdontologicaC3.Entity.Odontologo;
import com.example.ClinicaOdontologicaC3.Entity.Paciente;
import com.example.ClinicaOdontologicaC3.Entity.Turno;
import com.example.ClinicaOdontologicaC3.Repository.TurnoRepository;
import com.example.ClinicaOdontologicaC3.Service.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @InjectMocks
    private TurnoService turnoService;

    private Turno turno;
    private Odontologo odontologo;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        odontologo = new Odontologo("Pedro", "Casas", "AD234");
        odontologo.setId(1L);
        paciente = new Paciente("Paola", "Lemon", "1323434", LocalDate.now(), null, "email@example.com");
        paciente.setId(2L);
        turno = new Turno(paciente, odontologo, LocalDate.now());
        turno.setId(1L);
    }

    @Test
    void testRegistrarTurno() {
        // Arrange
        when(turnoRepository.save(turno)).thenReturn(turno);

        // Act
        TurnoDTO result = turnoService.registrarTurno(turno);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getOdontologoId());
        assertEquals(2L, result.getPacienteId());
        verify(turnoRepository, times(1)).save(turno);
    }

    @Test
    void testActualizarTurno() {
        // Arrange
        when(turnoRepository.save(turno)).thenReturn(turno);

        // Act
        turnoService.actualizarTurno(turno);

        // Assert
        verify(turnoRepository, times(1)).save(turno);
    }

    @Test
    void testEliminarTurno() {
        // Arrange
        Long id = 1L;

        // Act
        turnoService.eliminarTruno(id);

        // Assert
        verify(turnoRepository, times(1)).deleteById(id);
    }

    @Test
    void testBuscarPorId() {
        // Arrange
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        // Act
        Optional<Turno> result = turnoService.buscarPorId(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(turnoRepository, times(1)).findById(1L);
    }

    @Test
    void testListarTodosLosTurnos() {
        // Arrange
        Turno turno2 = new Turno(paciente, odontologo, LocalDate.now().plusDays(1));
        turno2.setId(2L);
        when(turnoRepository.findAll()).thenReturn(Arrays.asList(turno, turno2));

        // Act
        List<Turno> result = turnoService.listarTodosLosTurnos();

        // Assert
        assertEquals(2, result.size());
        verify(turnoRepository, times(1)).findAll();
    }

    @Test
    void testTurnoATurnoDto() {
        // Act
        TurnoDTO result = turnoService.turnoATurnoDto(turno);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getOdontologoId());
        assertEquals(2L, result.getPacienteId());
    }
}