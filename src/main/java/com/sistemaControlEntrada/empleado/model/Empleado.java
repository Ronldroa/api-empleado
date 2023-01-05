package com.sistemaControlEntrada.empleado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre", length = 20)
    private String nombre;
    @Column(name = "apellido", length = 20)
    private String apellido;
    @Column(name = "dni", length = 8)
    private String dni;
    private Date horaEntrada;
    private Date horaSalida;
    private Date horaEntradaComedor;
    private Date horaSalidaComedor;


}
