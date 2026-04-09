package com.hospital.dao;

import com.hospital.modelo.Camilla;

import java.util.List;

/**
 * Contrato DAO para operaciones de persistencia sobre Camilla.
 */
public interface ICamillaDAO extends ICRUD<Camilla> {

    /**
     * Busca una camilla por su numero unico.
     *
     * @param numero numero de camilla
     * @return camilla encontrada o null si no existe
     */
    Camilla buscarPorNumero(String numero);

    /**
     * Lista camillas filtradas por estado (Disponible, Ocupada, Mantenimiento).
     *
     * @param estado estado de camilla
     * @return lista de camillas que cumplen el filtro
     */
    List<Camilla> obtenerPorEstado(String estado);

    /**
     * Lista solo las camillas disponibles para nueva asignacion.
     *
     * @return lista de camillas disponibles
     */

    List<Camilla> obtenerDisponibles();

    /**
     * Lista camillas por ubicacion (area/sala).
     *
     * @param ubicacion ubicacion fisica de la camilla
     * @return lista de camillas de la ubicacion indicada
     */
    List<Camilla> obtenerPorUbicacion(String ubicacion);
}
