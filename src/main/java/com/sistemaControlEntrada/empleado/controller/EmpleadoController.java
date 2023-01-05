package com.sistemaControlEntrada.empleado.controller;

import com.sistemaControlEntrada.empleado.dto.EmpleadoDto;
import com.sistemaControlEntrada.empleado.dto.TiempoTrabajadoDto;
import com.sistemaControlEntrada.empleado.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmpleadoController {

    @Autowired
    EmpleadoService service;

    @PostMapping("/empleados")
    public ResponseEntity<String> crearEntada(@Valid @RequestBody EmpleadoDto request) {
        service.registrarEntradaDelEmpleado(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/empleado/{dni}/entradaAlmuerzo")
    public ResponseEntity<String> actualizarEmpleadoEntradaAlmuerzo(@Valid @PathVariable String dni) {
        service.actualizarEntradaDelAlmuerzoDelEmpleado(dni);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/empleado/{dni}/salidaAlmuerzo")
    public ResponseEntity<String> actualizarEmpleadoSalidaAlmuerzo(@Valid @PathVariable String dni) {
        service.actualizarSalidaDelAlmuerzoDelEmpleado(dni);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/empleado/{dni}/salida")
    public ResponseEntity<String> actualizarEmpleadoSalida(@Valid @PathVariable String dni) {
        service.actualizarSalidaDelTrabajo(dni);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/empleado/{dni}")
    public ResponseEntity<EmpleadoDto> obtenerEmpleado(@PathVariable String dni) {
        return ResponseEntity.ok(service.obtenerEmpleado(dni));
    }

    @GetMapping("/empleado/{dni}/horasTrabajadas")
    public ResponseEntity<List<TiempoTrabajadoDto>> obtenerHorasTrabajadas(@PathVariable String dni) {
        return ResponseEntity.ok(service.obtenerHorasTrabajadas(dni));
    }
    @GetMapping("/empleado/{dni}/listarPorDni")
    public ResponseEntity<List<EmpleadoDto>> listarPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(service.listarEmpleados(dni));
    }
}
