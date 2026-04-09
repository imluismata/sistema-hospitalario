package com.hospital.dao;

import java.util.List;

/**
 * Contrato genérico CRUD para cualquier entidad del sistema.
 *
 * @param <T> tipo de entidad (Paciente, Camilla, Factura, etc.)
 */
public interface ICRUD<T> {

    /**
     * Inserta una nueva entidad en la base de datos.
     *
     * @param objeto entidad a guardar
     */
    void insertar(T objeto);

    /**
     * Actualiza una entidad existente en la base de datos.
     *
     * @param objeto entidad con los datos actualizados
     */
    void actualizar(T objeto);

    /**
     * Elimina una entidad por su identificador.
     *
     * @param id identificador de la entidad a eliminar
     */
    void eliminar(int id);

    /**
     * Obtiene una entidad por su identificador.
     *
     * @param id identificador de la entidad
     * @return entidad encontrada o null si no existe
     */
    T obtenerPorId(int id);

    /**
     * Lista todas las entidades de la tabla asociada.
     *
     * @return lista de entidades, vacia si no hay registros
     */
    List<T> obtenerTodos();
}
