package com.upao.Backend23.controllers;

import com.upao.Backend23.DTO.UserDTO;
import com.upao.Backend23.mappers.LoginRequest;
import com.upao.Backend23.mappers.LoginResponse;
import com.upao.Backend23.models.User;
import com.upao.Backend23.services.UserService;
import com.upao.Backend23.repository.UserRepository;
import com.upao.Backend23.util.EncryptionUtil;
import com.upao.Backend23.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private final UserService userService;

    @Autowired

    JwtTokenUtil jwtTokenUtil;
    @Autowired

    UserRepository userRepository;
    @Autowired

    AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userService = userService;

        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/profiles")
    public List<UserDTO> getAllUserProfiles() {
        return userService.getAllUserProfiles();
    }
    @PostMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestBody Map<String, String> searchRequest) {
        if (searchRequest.containsKey("firstName") && searchRequest.containsKey("lastName")) {
            String firstName = searchRequest.get("firstName");
            String lastName = searchRequest.get("lastName");
            try {
                List<UserDTO> users = userService.searchUsers(firstName, lastName);
                return ResponseEntity.ok(users);
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parámetros de búsqueda incorrectos");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try{
            String newUser = userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalStateException sms){
            return new ResponseEntity<>(sms.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest loginRequest) throws Exception{
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if(user.isPresent()){
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                return new LoginResponse(EncryptionUtil.encrypt(jwtTokenUtil.generateToken(user.get())));
            }catch (AuthenticationException e){
                //pass to the throw.
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo y/o contraseña incorrecta");
    }

}
