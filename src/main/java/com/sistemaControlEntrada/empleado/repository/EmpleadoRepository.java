package com.sistemaControlEntrada.empleado.repository;

import com.sistemaControlEntrada.empleado.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByDni(String dni);
    @Query("select e from Empleado e where e.dni = ?1 and e.horaEntradaComedor = null")
    Optional<Empleado> findByDniAndHoraEntradaComedorIsNull(String dni);
    List<Empleado> findAllByDni(String dni);
    @Query("select e from Empleado e where e.dni = ?1 and e.horaSalidaComedor = null")
    Optional<Empleado> findByDniAndHoraSalidaComedorIsNull(String dni);
    @Query("select e from Empleado e where e.dni = ?1 and e.horaSalida = null")
    Optional<Empleado> findByDniAndHoraSalidaIsNull(String dni);
    @Query("select e from Empleado e where e.dni = ?1 and e.horaEntrada = null")
    Optional<Empleado> findByDniAndHoraEntradaIsNull(String dni);

}