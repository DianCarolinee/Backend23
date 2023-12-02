package com.upao.Backend23.Service;

import com.upao.Backend23.DTO.UserDTO;
import com.upao.Backend23.models.User;
import com.upao.Backend23.repository.UserRepository;
import com.upao.Backend23.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchUsers() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        List<User> users = new ArrayList<>();
        users.add(new User(1L, firstName, lastName, "john@example.com", "password"));
        when(userRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(users);

        // Act
        List<UserDTO> userDTOs = userService.searchUsers(firstName, lastName);

        // Assert
        assertEquals(1, userDTOs.size());
        assertEquals(firstName, userDTOs.get(0).getFirstName());
        assertEquals(lastName, userDTOs.get(0).getLastName());
    }

    @Test
    void getAllUserProfiles() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", "Doe", "john@example.com", "password"));
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserDTO> userProfiles = userService.getAllUserProfiles();

        // Assert
        assertEquals(1, userProfiles.size());
        assertEquals("John", userProfiles.get(0).getFirstName());
        assertEquals("Doe", userProfiles.get(0).getLastName());
    }

    @Test
    void addUser() {
        // Arrange
        User user = new User(null, "John", "Doe", "john@example.com", "password");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        // Act
        String result = userService.addUser(user);

        // Assert
        assertEquals("Usuario registrado correctamente", result);
    }

    @Test
    void addUserWithEmailInUse() {
        // Arrange
        User user = new User(null, "John", "Doe", "john@example.com", "password");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> userService.addUser(user));
    }

    @Test
    void getAllUserProfiles_EmptyDatabase() {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<UserDTO> userProfiles = userService.getAllUserProfiles();

        // Assert
        assertTrue(userProfiles.isEmpty());
    }

    @Test
    void addUser_PasswordTooLong() {
        // Arrange
        User user = new User(1L, "John", "Doe", "john@example.com", "toolongpassword");

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> userService.addUser(user));
    }

    @Test
    void addUser_NullPassword() {
        // Arrange
        User user = new User(null, "John", "Doe", "john@example.com", null);

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> userService.addUser(user));
    }
}
