package com.upao.Backend23.Service;

import com.upao.Backend23.models.Business;
import com.upao.Backend23.models.Product;
import com.upao.Backend23.repository.ProductRepository;
import com.upao.Backend23.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct_Success() {
        // Arrange
        Business business = new Business(1L, "Jhon", "Mi Negocio", "Trujillo", "925923740");
        Product product = new Product(1L, "Producto1", "Descripción", 20.0, 50, business);

        // Act
        when(productRepository.save(product)).thenReturn(product);
        String result = productService.addProduct(product);

        // Assert
        assertEquals("Producto registrado con éxito. Producto1", result);
    }

    @Test
    void addProduct_Failure() {
        // Arrange
        Business business = new Business(1L, "Jhon", "Mi Negocio", "Trujillo", "925923740");
        Product product = new Product(1L, "Producto1", "Descripción", 20.0, 50, business);

        // Act
        when(productRepository.save(product)).thenThrow(new RuntimeException("Simulated error"));
        // Assert
        assertThrows(IllegalStateException.class, () -> productService.addProduct(product));
    }

    @Test
    void updateProduct_Success() {
        // Arrange
        Long productId = 1L;
        Business business = new Business(1L, "Jhon", "Mi Negocio", "Trujillo", "925923740");
        Product existingProduct = new Product(productId, "Producto1", "Descripción", 20.0, 50, business);
        Product updatedProduct = new Product(productId, "ProductoActualizado", "Descripción Actualizada", 25.0, 60, business);

        // Act
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        String result = productService.updateProduct(productId, updatedProduct);

        // Assert
        assertEquals("Negocio actualizado con éxito", result); // Ajusta el mensaje esperado según la lógica real de tu servicio
        assertEquals(updatedProduct.getProductName(), existingProduct.getProductName());
        assertEquals(updatedProduct.getProductDescription(), existingProduct.getProductDescription());
        assertEquals(updatedProduct.getPrice(), existingProduct.getPrice());
        assertEquals(updatedProduct.getStock(), existingProduct.getStock());
    }

    @Test
    void updateProduct_ProductNotFound() {
        // Arrange
        Long productId = 1L;
        Business business = new Business(1L, "Jhon", "Mi Negocio", "Trujillo", "925923740");
        Product updatedProduct = new Product(productId, "ProductoActualizado", "Descripción Actualizada", 25.0, 60, business);

        // Act
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(productId, updatedProduct));
    }
}
