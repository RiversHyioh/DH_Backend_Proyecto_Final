package com.example.ClinicaOdontologicaC3.Security;

import com.example.ClinicaOdontologicaC3.Entity.*;
import com.example.ClinicaOdontologicaC3.Repository.OdontologoRepository;
import com.example.ClinicaOdontologicaC3.Repository.PacienteRepository;
import com.example.ClinicaOdontologicaC3.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private OdontologoRepository odontologoRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String passSinCifrar= "admin";
        String passCifrado= bCryptPasswordEncoder.encode(passSinCifrar);
        System.out.println("pass cifrado: "+passCifrado);
        Usuario usuario= new Usuario("admin","admin","admin@digitalhouse.com",passCifrado, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuario);

        String passSinCifrar1= "user";
        String passCifrado1= bCryptPasswordEncoder.encode(passSinCifrar1);
        System.out.println("pass cifrado: "+passCifrado1);
        Usuario usuario1= new Usuario("Jorgito","jpereyradh","jorge.pereyra@digitalhouse.com",passCifrado1, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuario1);

        Domicilio domicilio1 = new Domicilio( "Howard", 1, "Toronto", "Ontario");
        Paciente paciente1 = new Paciente("Paola", "Lemone", "1323434",  LocalDate.parse("2009-01-01"), domicilio1,"ochoag@gmail.com");
        pacienteRepository.save(paciente1);

        Domicilio domicilio2 = new Domicilio( "Londres", 1, "Grice", "Lima");
        Paciente paciente2 = new Paciente("Cristian", "Franco", "13234",  LocalDate.parse("2009-01-02"), domicilio2,"112g@gmail.com");
        pacienteRepository.save(paciente2);

        Odontologo odontologo1 = new Odontologo("Pedro","Casas","AD234");
        odontologoRepository.save(odontologo1);
        Odontologo odontologo2 = new Odontologo("Lina","Herrera","AD134");
        odontologoRepository.save(odontologo2);

    }
}
