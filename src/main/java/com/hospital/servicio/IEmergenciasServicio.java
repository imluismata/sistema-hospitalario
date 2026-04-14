package com.hospital.servicio;

import com.hospital.modelo.Paciente;
import java.util.List;

public interface IEmergenciasServicio {

    // 1. Llega un paciente y lo registramos en el sistema (sin asignarle cama aún)
    void registrarPacienteEmergencia(Paciente paciente);

    // 2. El médico lo evalúa y decide que se puede ir a su casa con una receta
    void darDeAltaEmergencia(String cedulaPaciente);

    // 3. El médico lo evalúa y decide que ES GRAVE y necesita hospitalización
    // (Este método se comunicará con el DAO de Habitaciones/Camillas)
    void internarPaciente(String cedulaPaciente, String idCamilla);

    // 4. Ver quiénes están sentados en la sala de espera de urgencias ahora mismo
    List<Paciente> obtenerPacientesEnEspera();
    int[] obtenerEstadisticasEmergencia();

    boolean[] obtenerEstadoCamillas();
}