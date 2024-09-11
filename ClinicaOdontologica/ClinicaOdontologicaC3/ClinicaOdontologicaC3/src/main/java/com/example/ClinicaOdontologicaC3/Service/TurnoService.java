package com.example.ClinicaOdontologicaC3.Service;

import com.example.ClinicaOdontologicaC3.Dto.TurnoDTO;
import com.example.ClinicaOdontologicaC3.Entity.Turno;
import com.example.ClinicaOdontologicaC3.Repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    public TurnoDTO registrarTurno(Turno turno){
         Turno turnoGuardado= turnoRepository.save(turno);
         return turnoATurnoDto(turnoGuardado);
    }

    public void actualizarTurno(Turno turno){
        turnoRepository.save(turno);
    }

    public void eliminarTruno(long id){
        turnoRepository.deleteById(id);
    }

    public Optional<Turno> buscarPorId(Long id){
        return turnoRepository.findById(id);
    }

    public List<Turno> listarTodosLosTurnos(){
        return turnoRepository.findAll();
    }


    public TurnoDTO turnoATurnoDto(Turno turno){
        TurnoDTO dto= new TurnoDTO();
        dto.setId(turno.getId());
        dto.setFecha(turno.getFecha());
        dto.setOdontologoId(turno.getOdontologo().getId());
        dto.setPacienteId(turno.getPaciente().getId());
        return dto;
    }
}
