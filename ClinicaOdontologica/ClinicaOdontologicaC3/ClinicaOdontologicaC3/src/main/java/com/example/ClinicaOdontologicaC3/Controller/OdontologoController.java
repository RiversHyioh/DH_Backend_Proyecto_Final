package com.example.ClinicaOdontologicaC3.Controller;

import com.example.ClinicaOdontologicaC3.Entity.Odontologo;
import com.example.ClinicaOdontologicaC3.Exception.ResourceNotFoundException;
import com.example.ClinicaOdontologicaC3.Repository.OdontologoRepository;
import com.example.ClinicaOdontologicaC3.Service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;


    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        Odontologo nuevoOdontologo = odontologoService.registrarOdontologo(odontologo);
        if(odontologo != null){
            return new ResponseEntity<>(nuevoOdontologo, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoActualizar = odontologoService.buscarPorId(odontologo.getId());
        if (odontologoActualizar.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            return new ResponseEntity<>("Odontologo Actualizado con Exito", HttpStatus.OK);
        }
        //return new ResponseEntity<>("Odontologo no encontrado", HttpStatus.NOT_FOUND);
        throw new ResourceNotFoundException("Odontologo no encontrado por ese ID para su actualizacion");
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Odontologo>> buscarPorId(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologobuscar = odontologoService.buscarPorId(id);
        if (odontologobuscar.isPresent()){
            return new ResponseEntity<>(odontologobuscar, HttpStatus.OK);
        }
        //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        throw new ResourceNotFoundException("Odontologo no encontrado por ese ID");
    }


    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos(){
        List<Odontologo> todosLosOdontologos = odontologoService.listarTodosLosOdontologos();
        if (todosLosOdontologos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(todosLosOdontologos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoAEliminar = odontologoService.buscarPorId(id);
        if (odontologoAEliminar.isPresent()){
            odontologoService.eliminarOdontologo(id);
            return new ResponseEntity<>("Odontologo Eliminado con Exito", HttpStatus.OK);
        }
        //return new ResponseEntity<>("Odontologo no encontrado", HttpStatus.NOT_FOUND);
        throw new ResourceNotFoundException("Odontologo no encontrado por ese ID para su eliminacion ");
    }

    public Optional<Odontologo> buscarPorMatricula(String matricula){
        return  odontologoService.buscarPorMatricula(matricula);
    }

}
