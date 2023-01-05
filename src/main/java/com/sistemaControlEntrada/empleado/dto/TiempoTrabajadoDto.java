package com.sistemaControlEntrada.empleado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TiempoTrabajadoDto {

    private String fecha;

    private long horas;
}
