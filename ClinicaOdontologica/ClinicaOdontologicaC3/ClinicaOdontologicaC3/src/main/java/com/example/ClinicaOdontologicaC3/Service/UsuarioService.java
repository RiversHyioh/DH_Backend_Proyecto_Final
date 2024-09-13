package com.example.ClinicaOdontologicaC3.Service;

import com.example.ClinicaOdontologicaC3.Entity.Usuario;
import com.example.ClinicaOdontologicaC3.Entity.UsuarioRole;
import com.example.ClinicaOdontologicaC3.Exception.ResourceNotFoundException;
import com.example.ClinicaOdontologicaC3.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void registrar(String nombre, String userName, String email, String password, String password2) throws ResourceNotFoundException {
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setUserName(userName);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setUsuarioRole(UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void actualizar(Long idUsuario, String nombre, String email, String password, String password2) throws ResourceNotFoundException {
        validar(nombre, email, password, password2);
        Optional<Usuario> respuesta = usuarioRepository.findById(idUsuario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setUsuarioRole(UsuarioRole.ROLE_USER);
            usuarioRepository.save(usuario);
        }
    }

    public Usuario getOne(Long id) {
        return usuarioRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    @Transactional
    public void cambiarRol(Long id) {
        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getUsuarioRole().equals(UsuarioRole.ROLE_USER)) {
                usuario.setUsuarioRole(UsuarioRole.ROLE_ADMIN);

            } else if (usuario.getUsuarioRole().equals(UsuarioRole.ROLE_ADMIN)) {
                usuario.setUsuarioRole(UsuarioRole.ROLE_USER);
            }
        }
    }

    private void validar(String nombre, String email, String password, String password2) throws ResourceNotFoundException {

        if (nombre.isEmpty() || nombre == null) {
            throw new ResourceNotFoundException("El nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new ResourceNotFoundException("El email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new ResourceNotFoundException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new ResourceNotFoundException("Las contraseñas ingresadas deben ser iguales");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);

        if (optionalUsuario.isPresent()) {
            Usuario usuarioBuscado = optionalUsuario.get();

            // Establecer la sesión del usuario
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuarioBuscado);

            // Retornar un UserDetails utilizando el propio objeto Usuario
            return new User(usuarioBuscado.getEmail(), usuarioBuscado.getPassword(), usuarioBuscado.getAuthorities());
        } else {
            return null;
        }
    }
}