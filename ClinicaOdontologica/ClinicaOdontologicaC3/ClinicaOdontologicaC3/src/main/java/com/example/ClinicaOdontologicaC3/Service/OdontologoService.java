package com.example.ClinicaOdontologicaC3.Service;

import com.example.ClinicaOdontologicaC3.Entity.Odontologo;
import com.example.ClinicaOdontologicaC3.Repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo registrarOdontologo(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }
    public Optional<Odontologo> buscarPorId(Long id){
        return odontologoRepository.findById(id);
    }
    public void actualizarOdontologo(Odontologo odontologo){
        odontologoRepository.save(odontologo);
    }
    public void eliminarOdontologo(Long id){
        odontologoRepository.deleteById(id);
    }

    public Optional<Odontologo> buscarPorMatricula(String matricula){
        return odontologoRepository.findByMatricula(matricula);
    }

    public List<Odontologo> listarTodosLosOdontologos(){
        return odontologoRepository.findAll();
    }
















}
