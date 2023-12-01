package com.upao.Backend23.services;

import com.upao.Backend23.models.Business;
import com.upao.Backend23.repository.BusinessRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class BusinessService {

    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    private boolean isEmptyOrWhitespace(String value) {
        return value == null || value.trim().isEmpty();
    }

    public String addBusiness(Business business) {
        try {
            Business savedBusiness = businessRepository.save(business);
            return "Negocio registrado con éxito. " + savedBusiness.getBusinessName(); // Puedes devolver el ID u otra información relevante
        } catch (Exception e) {
            throw new IllegalStateException("Error al registrar el negocio", e);
        }
    }

    public String updateBusiness(Long businessId, Business updatedBusiness) {
        Optional<Business> existingBusiness = businessRepository.findById(businessId);

        if (existingBusiness.isPresent()) {
            // Actualizar la información del negocio existente
            Business businessToUpdate = existingBusiness.get();
            businessToUpdate.setBusinessName(updatedBusiness.getBusinessName());
            businessToUpdate.setDescription(updatedBusiness.getDescription());
            businessToUpdate.setAddress(updatedBusiness.getAddress());
            businessToUpdate.setPhone(updatedBusiness.getPhone());

            // Guardar el negocio actualizado
            businessRepository.save(businessToUpdate);

            return "Negocio actualizado con éxito";
        } else {
            throw new IllegalArgumentException("No se encontró un negocio con el ID proporcionado");
        }
    }

    private void validate(Business business){
        if (isEmptyOrWhitespace(business.getBusinessName()) || isEmptyOrWhitespace(business.getDescription()) || isEmptyOrWhitespace(business.getAddress()) || isEmptyOrWhitespace(business.getPhone())){
            throw new IllegalStateException("Todos los campos deben ser completados");
        }
    }
}
