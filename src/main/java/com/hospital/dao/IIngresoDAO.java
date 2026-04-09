package com.hospital.dao;

import com.hospital.modelo.Ingreso;

import java.util.Date;
import java.util.List;

/**
 * Contrato DAO para operaciones de persistencia sobre Ingreso.
 */
public interface IIngresoDAO extends ICRUD<Ingreso> {

    /**
     * Lista ingresos de un paciente especifico.
     *
     * @param idPaciente id del paciente
     * @return lista de ingresos del paciente
     */
    List<Ingreso> obtenerPorPaciente(int idPaciente);

    /**
     * Lista ingresos por estado (Activo, Cerrado, Trasladado, etc.).
     *
     * @param estado estado del ingreso
     * @return lista de ingresos del estado indicado
     */
    List<Ingreso> obtenerPorEstado(String estado);

    /**
     * Lista ingresos registrados en un rango de fechas.
     *
     * @param fechaInicio fecha inicial del rango
     * @param fechaFin fecha final del rango
     * @return lista de ingresos dentro del rango
     */
    List<Ingreso> obtenerPorRangoFechas(Date fechaInicio, Date fechaFin);
}
