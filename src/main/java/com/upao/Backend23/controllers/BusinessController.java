package com.upao.Backend23.controllers;


import com.upao.Backend23.models.Business;
import com.upao.Backend23.services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/business")
@CrossOrigin(origins = "http://localhost:4200")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService){
        this.businessService=businessService;
    }

    @PostMapping ("/create")
    public ResponseEntity<?> addBusiness(@RequestBody Business business){
        try{
            String newBusiness = businessService.addBusiness(business);
            return new ResponseEntity<>(newBusiness, HttpStatus.CREATED);
           } catch (IllegalStateException sms){
            return new ResponseEntity<>(sms.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/{businessId}")
    public ResponseEntity<?> updateBusiness(@PathVariable Long businessId, @RequestBody Business updatedBusiness) {
        try {
            String updatedInfo = businessService.updateBusiness(businessId, updatedBusiness);
            return new ResponseEntity<>(updatedInfo, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error al actualizar el negocio", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
