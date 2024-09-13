package com.example.ClinicaOdontologicaC3.Controller;

import com.example.ClinicaOdontologicaC3.Dto.TurnoDTO;
import com.example.ClinicaOdontologicaC3.Entity.Odontologo;
import com.example.ClinicaOdontologicaC3.Entity.Paciente;
import com.example.ClinicaOdontologicaC3.Entity.Turno;
import com.example.ClinicaOdontologicaC3.Exception.ResourceNotFoundException;
import com.example.ClinicaOdontologicaC3.Service.OdontologoService;
import com.example.ClinicaOdontologicaC3.Service.PacienteService;
import com.example.ClinicaOdontologicaC3.Service.TurnoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;


    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody Turno turno) throws BadRequestException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(turno.getOdontologo().getId());

        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            turno.setPaciente(pacienteBuscado.get());
            turno.setOdontologo(odontologoBuscado.get());
            return new ResponseEntity<>(turnoService.registrarTurno(turno), HttpStatus.OK);
        } else {
            throw new BadRequestException("El paciente o el odontólogo no existen");
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) throws BadRequestException{
        Optional<Turno> turnoAActualizar = turnoService.buscarPorId(turno.getId());
        if (turnoAActualizar.isPresent()){
            turnoService.actualizarTurno(turno);
            return new ResponseEntity<>("Turno actualizado Correctamente", HttpStatus.OK);
        }
        //return new ResponseEntity<>("Turno no encontrado", HttpStatus.NOT_FOUND);
        throw new BadRequestException("Turno no encontrado por ese ID");
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable Long id) throws BadRequestException{
        Turno turnoBuscado = turnoService.buscarPorId(id)
                .orElseThrow(() -> new BadRequestException("turno no encontrado por id"));
        return ResponseEntity.ok(turnoBuscado);
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos(){
        List<Turno> todosLosTurnos = turnoService.listarTodosLosTurnos();
        if (todosLosTurnos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(todosLosTurnos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws BadRequestException{
        Optional<Turno> turnoAEliminar = turnoService.buscarPorId(id);
        if (turnoAEliminar.isPresent()){
            turnoService.eliminarTruno(id);
            return new ResponseEntity<>("Turno eliminado con Exito", HttpStatus.OK);
        }
        //return new ResponseEntity<>("Turno no encontrado", HttpStatus.NOT_FOUND);
        throw new BadRequestException("Turno no encontrado por ID");
    }















}
