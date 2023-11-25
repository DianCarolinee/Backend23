package com.upao.Backend23.services;

import com.upao.Backend23.DTO.UserDTO;
import com.upao.Backend23.models.User;
import com.upao.Backend23.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUserProfiles() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userProfiles = new ArrayList<>();
        for (User user : users) {
            userProfiles.add(new UserDTO(user));
        }
        return userProfiles;
    }

    public List<UserDTO> searchUsers(String firstName, String lastName) {
        List<User> users = userRepository.findByFirstNameAndLastName(firstName, lastName);

        if (users.isEmpty()) {
            throw new IllegalStateException("Usuario no encontrado");
        }
        return users.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    private boolean isEmptyOrWhitespace(String value) {
        return value == null || value.trim().isEmpty();
    }
    public String addUser(User user){
        Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());

        if (isEmptyOrWhitespace(user.getFirstName()) || isEmptyOrWhitespace(user.getLastName()) || isEmptyOrWhitespace(user.getEmail()) || user.getPassword() == null) {
            throw new IllegalStateException("Todos los campos son requeridos");
        }
        if(!existingUserByEmail.isEmpty()) {
            throw new IllegalStateException("El correo que ingresaste ya esta en uso");
        }
        if(user.getPassword() != null && user.getPassword().length() > 8){
            throw new IllegalStateException("La contraseña debe tener menos de 8 caracteres");
        }

        userRepository.save(user);
        return "Usuario registrado correctamente";

    }

    public ResponseEntity<?> getUserProfile(String email) {
        if (isEmptyOrWhitespace(email)) {
            return new ResponseEntity<>("El correo es un campo requerido", HttpStatus.BAD_REQUEST);
        }

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }


        return null;
    }





}
