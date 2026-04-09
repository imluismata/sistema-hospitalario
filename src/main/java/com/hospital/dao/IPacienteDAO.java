package com.hospital.dao;

import com.hospital.modelo.Paciente;
import java.util.List;

/**
 * Contrato DAO para persistencia de Paciente.
 * Hereda operaciones CRUD generales desde ICRUD.
 */
public interface IPacienteDAO extends ICRUD<Paciente> {

    /**
     * Busca pacientes por nombre completo o parcial.
     *
     * @param nombre nombre a consultar
     * @return lista de pacientes que coinciden
     */
    List<Paciente> buscarPorNombre(String nombre);

    /**
     * Busca un paciente por su username de acceso.
     *
     * @param username nombre de usuario
     * @return paciente encontrado o null si no existe
     */
    Paciente buscarPorUsername(String username);
}