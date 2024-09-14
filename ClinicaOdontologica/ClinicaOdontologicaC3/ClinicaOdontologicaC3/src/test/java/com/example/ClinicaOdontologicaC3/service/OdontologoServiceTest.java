package com.example.ClinicaOdontologicaC3.service;
import com.example.ClinicaOdontologicaC3.Entity.Odontologo;
import com.example.ClinicaOdontologicaC3.Repository.OdontologoRepository;
import com.example.ClinicaOdontologicaC3.Service.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class OdontologoServiceTest {
    @Mock
    private OdontologoRepository odontologoRepository;

    @InjectMocks
    private OdontologoService odontologoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarOdontologo() {
        // Arrange
        Odontologo odontologo = new Odontologo("Pedro", "Casas", "AD234");
        when(odontologoRepository.save(odontologo)).thenReturn(odontologo);

        // Act
        Odontologo result = odontologoService.registrarOdontologo(odontologo);

        // Assert
        assertNotNull(result);
        assertEquals("Pedro", result.getNombre());
        assertEquals("AD234", result.getMatricula());
        verify(odontologoRepository, times(1)).save(odontologo);
    }

    @Test
    void testBuscarPorId() {
        // Arrange
        Odontologo odontologo = new Odontologo("Lina", "Herrera", "AD134");
        odontologo.setId(2L);
        when(odontologoRepository.findById(2L)).thenReturn(Optional.of(odontologo));

        // Act
        Optional<Odontologo> result = odontologoService.buscarPorId(2L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getId());
        verify(odontologoRepository, times(1)).findById(2L);
    }

    @Test
    void testActualizarOdontologo() {
        // Arrange
        Odontologo odontologo = new Odontologo("Pedro", "Casas", "AD234");
        odontologo.setId(1L);
        when(odontologoRepository.save(odontologo)).thenReturn(odontologo);

        // Act
        odontologoService.actualizarOdontologo(odontologo);

        // Assert
        verify(odontologoRepository, times(1)).save(odontologo);
    }

    @Test
    void testEliminarOdontologo() {
        // Arrange
        Long id = 1L;

        // Act
        odontologoService.eliminarOdontologo(id);

        // Assert
        verify(odontologoRepository, times(1)).deleteById(id);
    }

    @Test
    void testListarTodosLosOdontologos() {
        // Arrange
        Odontologo odontologo1 = new Odontologo("Pedro", "Casas", "AD234");
        Odontologo odontologo2 = new Odontologo("Lina", "Herrera", "AD134");
        when(odontologoRepository.findAll()).thenReturn(Arrays.asList(odontologo1, odontologo2));

        // Act
        List<Odontologo> result = odontologoService.listarTodosLosOdontologos();

        // Assert
        assertEquals(2, result.size());
        verify(odontologoRepository, times(1)).findAll();
    }
}
