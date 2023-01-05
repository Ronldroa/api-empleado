package com.sistemaControlEntrada.empleado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpleadoDto {

    private String dni;

    private String nombre;

    private String apellido;
    private Date horaEntrada;

    private Date horaSalida;
    private Date horaEntradaComedor;
    private Date horaSalidaComedor;
}
