package com.example.ClinicaOdontologicaC3.Controller;

import com.example.ClinicaOdontologicaC3.Entity.Paciente;
import com.example.ClinicaOdontologicaC3.Exception.ResourceNotFoundException;
import com.example.ClinicaOdontologicaC3.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;


  @PostMapping
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.registrarPaciente(paciente));
  }
  @PutMapping
   public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente)throws ResourceNotFoundException{
      Optional<Paciente> pacienteBuscado= pacienteService.buscarPorId(paciente.getId());
      if(pacienteBuscado.isPresent()){
          pacienteService.actualizarPaciente(paciente);
          return new ResponseEntity<>("Paciente Actualizado con Exito", HttpStatus.OK);
      }else{
            throw new ResourceNotFoundException("paciente no encontrado");
            // return ResponseEntity.notFound().build();
      }
  }
  @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Paciente>> buscarPorId(@PathVariable Long id) throws ResourceNotFoundException{

        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorId(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado);
        }else{
            //return ResponseEntity.notFound().build();
            throw new ResourceNotFoundException("paciente no encontrado por id");
        }
  }
  @GetMapping("/buscar/email/{email}")
    public ResponseEntity<Optional<Paciente>> buscarPorEmail(@PathVariable String email) throws ResourceNotFoundException{
      Optional<Paciente> pacienteBuscado= pacienteService.buscarPorEmail(email);
      if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado);
      }else{
          //return ResponseEntity.notFound().build();
          throw new ResourceNotFoundException("paciente no encontrado por email");
      }
  }
  @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos(){

      List<Paciente> todosLosPacientes = pacienteService.listarTodos();
      if (todosLosPacientes.isEmpty()){
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(todosLosPacientes, HttpStatus.OK);}


  @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException{
      Optional<Paciente> pacienteBuscado=pacienteService.buscarPorId(id);
      if(pacienteBuscado.isPresent()){
          pacienteService.eliminarPaciente(id);
          return ResponseEntity.ok("paciente eliminado con exito");
      }else{
          throw new ResourceNotFoundException("paciente no encontrado");
          //return ResponseEntity.notFound().build();
      }

  }
}
