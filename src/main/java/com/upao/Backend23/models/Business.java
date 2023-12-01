package com.upao.Backend23.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Entity
@Data
@Table (name = "businesses")
@NoArgsConstructor
@AllArgsConstructor
public class Business {

@Id
@Column(name = "business_id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long businessId;

@Column(name = "business_name")
private String businessName;

@Column(name = "description")
private String description;

@Column(name = "address")
private String address;

@Column(name = "phone")
private String phone;


}


