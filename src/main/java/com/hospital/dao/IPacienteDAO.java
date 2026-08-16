package com.hospital.dao;

import com.hospital.modelo.Paciente;

import java.util.List;

// POO: Abstraccion - contrato para operaciones de pacientes.
public interface IPacienteDAO {
    void insertar(Paciente objeto);

    void actualizar(Paciente objeto);

    void eliminar(int id);

    List<Paciente> obtenerTodos();

    Paciente obtenerPorId(int id);

    List<Paciente> buscarPorNombre(String nombre);

    Paciente buscarPorUsername(String username);
}
