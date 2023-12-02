package com.upao.Backend23.models;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Event")
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(name = "nombre_evento")
    private String nommbreEvento;



}
