package com.upao.Backend23.Service;

import com.upao.Backend23.models.Business;
import com.upao.Backend23.repository.BusinessRepository;
import com.upao.Backend23.services.BusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class BusinessServiceTest {

    @Mock
    private BusinessRepository businessRepository;

    @InjectMocks
    private BusinessService businessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBusiness_Success() {
        // Arrange
        Business business = new Business(1L, "Mi Negocio", "Descripción", "Trujillo", "925923740");

        // Act
        when(businessRepository.save(business)).thenReturn(business);
        String result = businessService.addBusiness(business);

        // Assert
        assertEquals("Negocio registrado con éxito. Mi Negocio", result);
    }

    @Test
    void addBusiness_RequiredFieldsEmpty() {
        // Arrange
        Business business = new Business(null, "", "Descripción", "Trujillo", "925923740");

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> businessService.addBusiness(business));
    }

    @Test
    void addBusiness_Failure() {
        // Arrange
        Business business = new Business(1L, "Mi Negocio", "Descripción", "Trujillo", "925923740");

        // Act
        when(businessRepository.save(business)).thenThrow(new RuntimeException("Simulated error"));

        // Assert
        assertThrows(IllegalStateException.class, () -> businessService.addBusiness(business));
    }

    @Test
    void updateBusiness_Success() {
        // Arrange
        Long businessId = 1L;
        Business existingBusiness = new Business(businessId, "Mi Negocio", "Descripción", "Trujillo", "925923740");
        Business updatedBusiness = new Business(businessId, "NegocioActualizado", "Descripción Actualizada", "Lima", "123456789");

        // Act
        when(businessRepository.findById(businessId)).thenReturn(Optional.of(existingBusiness));
        String result = businessService.updateBusiness(businessId, updatedBusiness);

        // Assert
        assertEquals("Negocio actualizado con éxito", result);
        assertEquals(updatedBusiness.getBusinessName(), existingBusiness.getBusinessName());
        assertEquals(updatedBusiness.getDescription(), existingBusiness.getDescription());
        assertEquals(updatedBusiness.getAddress(), existingBusiness.getAddress());
        assertEquals(updatedBusiness.getPhone(), existingBusiness.getPhone());
    }

    @Test
    void updateBusiness_BusinessNotFound() {
        // Arrange
        Long businessId = 1L;
        Business updatedBusiness = new Business(businessId, "NegocioActualizado", "Descripción Actualizada", "Lima", "123456789");

        // Act
        when(businessRepository.findById(businessId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> businessService.updateBusiness(businessId, updatedBusiness));
    }
}