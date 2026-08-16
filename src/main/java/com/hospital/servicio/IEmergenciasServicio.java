package com.hospital.servicio;

import com.hospital.modelo.Paciente;
import java.util.List;

// POO: Abstraccion - define operaciones de negocio para emergencias.
public interface IEmergenciasServicio {

    // Llega un paciente y lo registramos en el sistema (sin asignarle cama aún)
    void registrarPacienteEmergencia(Paciente paciente);

    // El médico lo evalúa y decide que se puede ir a su casa con una receta
    void darDeAltaEmergencia(String cedulaPaciente);

    //  El médico lo evalúa y decide que ES GRAVE y necesita hospitalización
    void internarPaciente(String cedulaPaciente, String idCamilla);

    // Ver quiénes están sentados en la sala de espera de urgencias ahora mismo
    List<Paciente> obtenerPacientesEnEspera();
    int[] obtenerEstadisticasEmergencia();

    boolean[] obtenerEstadoCamillas();
}