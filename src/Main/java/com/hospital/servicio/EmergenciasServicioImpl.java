package com.hospital.servicio;

import com.hospital.dao.PacienteDAOImpl;
import com.hospital.modelo.Paciente;
import java.util.ArrayList;
import java.util.List;

// POO: Polimorfismo - implementa IEmergenciasServicio.
public class EmergenciasServicioImpl implements IEmergenciasServicio {

    // POO: Encapsulacion - el DAO se oculta dentro del servicio.
    private PacienteDAOImpl pacienteDAO = new PacienteDAOImpl();

    @Override
    public void registrarPacienteEmergencia(Paciente paciente) {
        System.out.println("Servicio: Validando paciente y enviando al DAO...");
        pacienteDAO.registrarPaciente(paciente);
    }

    @Override
    public void internarPaciente(String cedulaPaciente, String idCamilla) {
        System.out.println("Servicio: Autorizando internamiento para cédula " + cedulaPaciente);
        pacienteDAO.internarPaciente(cedulaPaciente, idCamilla);
    }

    @Override
    public void darDeAltaEmergencia(String cedulaPaciente) {
        // Lo programaremos luego
        System.out.println("Alta médica en construcción...");
    }

    @Override
    public List<Paciente> obtenerPacientesEnEspera() {
        return new ArrayList<>();
    }

    @Override
    public int[] obtenerEstadisticasEmergencia() {
        return pacienteDAO.obtenerEstadisticas();
    }

    @Override
    public boolean[] obtenerEstadoCamillas() {
        return pacienteDAO.obtenerEstadoCamillas();
    }
}