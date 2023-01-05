package com.sistemaControlEntrada.empleado.service;

import com.sistemaControlEntrada.empleado.dto.EmpleadoDto;
import com.sistemaControlEntrada.empleado.dto.TiempoTrabajadoDto;
import com.sistemaControlEntrada.empleado.exception.EntityYaRegistradaException;
import com.sistemaControlEntrada.empleado.model.Empleado;
import com.sistemaControlEntrada.empleado.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmpleadoService {
    private static final Logger LOG = LoggerFactory.getLogger(EmpleadoService.class);

    private EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    private Empleado convertirDtoAEntity(EmpleadoDto request) {
        Empleado entity = new Empleado();
        entity.setDni(request.getDni());
        return entity;
    }

    public Empleado registrarEntradaDelEmpleado(EmpleadoDto request) {
        Empleado entity = convertirDtoAEntity(request);
        entity.setHoraEntrada(new Date());
        if (estaRegistrado(request.getDni())) {
            Optional<Empleado> entity2 = empleadoRepository.findByDniAndHoraEntradaIsNull(request.getDni());
            if (entity2.isPresent()) {
                throw new EntityYaRegistradaException("El empleado ya se encuentra registrado");
            }

        }
        empleadoRepository.save(entity);
        return entity;
    }

    //esta registrado el dni
    private boolean estaRegistrado(String dni) {
        return empleadoRepository.findByDniAndHoraEntradaIsNull(dni).isPresent();
    }

    public void actualizarEntradaDelAlmuerzoDelEmpleado(String dni) {
        Optional<Empleado> entity = empleadoRepository.findByDniAndHoraEntradaComedorIsNull(dni);
        if (entity.isPresent()) {
            entity.get().setHoraEntradaComedor(new Date());
            empleadoRepository.save(entity.get());
        } else {
            throw new EntityYaRegistradaException("El empleado ya registro su entrada al almuerzo");
        }
    }


    public void actualizarSalidaDelAlmuerzoDelEmpleado(String dni) {
        Optional<Empleado> entity = empleadoRepository.findByDniAndHoraSalidaComedorIsNull(dni);
        if (entity.isPresent()) {
            entity.get().setHoraSalidaComedor(new Date());
            empleadoRepository.save(entity.get());
        } else {
            throw new EntityYaRegistradaException("El empleado ya registro su salida del almuerzo");
        }
    }


    public Empleado actualizarSalidaDelTrabajo(String dni) {

        Optional<Empleado> entity = empleadoRepository.findByDniAndHoraSalidaIsNull(dni);
        if (entity.isPresent()) {
            entity.get().setHoraSalida(new Date());
            empleadoRepository.save(entity.get());
        } else if (!(entity.isPresent())) {
            throw new EntityYaRegistradaException("El empleado ya registro su salida del trabajo");
        } else {
            throw new EntityYaRegistradaException("El empleado no se encuentra registrado");
        }
        return entity.get();

    }

    public EmpleadoDto obtenerEmpleado(String dni) {
        Empleado entity = (Empleado) empleadoRepository.findByDni(dni).orElseThrow(() -> new EntityYaRegistradaException("El dni no se encuentra registrado"));
        return convertirEntityADto(entity);

    }

    private EmpleadoDto convertirEntityADto(Empleado entity) {
        EmpleadoDto dto = new EmpleadoDto();
        dto.setDni(entity.getDni());
        dto.setHoraEntrada(entity.getHoraEntrada());
        dto.setHoraEntradaComedor(entity.getHoraEntradaComedor());
        dto.setHoraSalida(entity.getHoraSalida());
        dto.setHoraSalidaComedor(entity.getHoraSalidaComedor());
        return dto;
    }

    //cuantas horas a trabajado un empleado por dia menos el almuerzo
//    public String obtenerHorasTrabajadasDni(String dni) {
//        Empleado entity = (Empleado) empleadoRepository.findByDni(dni).orElseThrow(() -> new EntityYaRegistradaException("El dni no se encuentra registrado"));
//        long horasTrabajadas = entity.getHoraSalida().getTime() - entity.getHoraEntrada().getTime();
//        long horasAlmuerzo = entity.getHoraSalidaComedor().getTime() - entity.getHoraEntradaComedor().getTime();
//        return (horasTrabajadas - horasAlmuerzo) / 1000 / 60 / 60+" horas "+(horasTrabajadas - horasAlmuerzo) / 1000 / 60 / 60 + " minutos ha trabajado el empleado "+entity.getDni();
//    }
    //listar por dni
    public List<EmpleadoDto> listarEmpleados(String dni) {
        List<Empleado> entities = empleadoRepository.findAllByDni(dni);
        List<EmpleadoDto> dtos = new ArrayList<>();
        for (Empleado entity : entities) {
            dtos.add(convertirEntityADto(entity));
        }
        return dtos;
    }

    /*
     * 1. Obtener todas los dias que ha trabajado un empleado
     * 2. Obtener las horas trabajadas por dia
     */
    public List<TiempoTrabajadoDto> obtenerHorasTrabajadas(String dni) {
        List<Empleado> entities = empleadoRepository.findAllByDni(dni);
        List<TiempoTrabajadoDto> dtos = new ArrayList<>();

        for (Empleado entity : entities) {
            long horasTrabajadas = entity.getHoraSalida().getTime() - entity.getHoraEntrada().getTime();
            long horasAlmuerzo = entity.getHoraSalidaComedor().getTime() - entity.getHoraEntradaComedor().getTime();

            // dtos.add((horasTrabajadas - horasAlmuerzo) / 1000 / 60 / 60+" horas "+(horasTrabajadas - horasAlmuerzo) / 1000 / 60 / 60 + " minutos ha trabajado el empleado "+entity.getDni());
            long sumaDeHoras = (horasTrabajadas - horasAlmuerzo) / 1000 / 60 / 60;
            System.out.println("a:" + sumaDeHoras);
            //
            // Choose time zone in which you want to interpret your Date
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            cal.setTime(entity.getHoraEntrada());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String fecha = year + "-" + month + "-" + day;
            //System.out.println("Fecha-----"+fecha);
            //añadir fecha y horas trabajadas
            TiempoTrabajadoDto dto = new TiempoTrabajadoDto();
            dto.setHoras(sumaDeHoras);
            dto.setFecha(fecha);
            dtos.add(dto);

        }

        return dtos;
    }

}
