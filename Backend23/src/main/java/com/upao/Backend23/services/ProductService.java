package com.upao.Backend23.services;

import com.upao.Backend23.models.Business;
import com.upao.Backend23.models.Product;
import com.upao.Backend23.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository){
        this.productRepository=productRepository;
    }


    public String addProduct(Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            return "Producto registrado con éxito. " + savedProduct.getProductName(); // Puedes devolver el ID u otra información relevante
        } catch (Exception e) {
            throw new IllegalStateException("Error al registrar el producto", e);
        }
    }

    public String updateProduct(Long productId, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(productId);

        if (existingProduct.isPresent()) {
            // Actualizar la información del negocio existente
            Product productToUpdate = existingProduct.get();
            productToUpdate.setProductName(updatedProduct.getProductName());
            productToUpdate.setProductDescription(updatedProduct.getProductDescription());
            productToUpdate.setPrice(updatedProduct.getPrice());
            productToUpdate.setStock(updatedProduct.getStock());

            // Guardar el negocio actualizado
            productRepository.save(productToUpdate);

            return "Negocio actualizado con éxito";
        } else {
            throw new IllegalArgumentException("No se encontró un negocio con el ID proporcionado");
        }
    }

}
