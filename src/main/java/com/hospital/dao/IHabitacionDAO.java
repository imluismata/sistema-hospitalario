package com.hospital.dao;

import com.hospital.modelo.Habitacion;

import java.util.List;

/**
 * Contrato DAO para operaciones de persistencia sobre Habitacion.
 */
public interface IHabitacionDAO extends ICRUD<Habitacion> {

    /**
     * Busca una habitacion por su numero unico.
     *
     * @param numero numero de habitacion
     * @return habitacion encontrada o null si no existe
     */
    Habitacion buscarPorNumero(String numero);

    /**
     * Lista habitaciones disponibles para ingreso hospitalario.
     *
     * @return lista de habitaciones disponibles
     */
    List<Habitacion> obtenerDisponibles();

    /**
     * Lista habitaciones por tipo (General, UCI, Privada, etc.).
     *
     * @param tipo tipo de habitacion
     * @return lista de habitaciones del tipo indicado
     */
    List<Habitacion> obtenerPorTipo(String tipo);

    /**
     * Lista habitaciones por estado operativo.
     *
     * @param estado estado de la habitacion
     * @return lista de habitaciones del estado indicado
     */
    List<Habitacion> obtenerPorEstado(String estado);
}
