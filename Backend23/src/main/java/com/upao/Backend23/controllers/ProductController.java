package com.upao.Backend23.controllers;

import com.upao.Backend23.models.Business;
import com.upao.Backend23.models.Product;
import com.upao.Backend23.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

     private final ProductService productService;

     public ProductController(final ProductService productService){
         this.productService=productService;
     }


    @PostMapping ("/create")
    public ResponseEntity<?> addProduct(@RequestBody Product product){
        try{
            String newProduct = productService.addProduct(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (IllegalStateException sms){
            return new ResponseEntity<>(sms.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        try {
            String updatedInfo = productService.updateProduct(productId, updatedProduct);
            return new ResponseEntity<>(updatedInfo, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error al actualizar el negocio", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
