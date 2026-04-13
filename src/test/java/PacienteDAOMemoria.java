import com.hospital.dao.IPacienteDAO;
import com.hospital.modelo.Paciente;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOMemoria implements IPacienteDAO {

    // Esta es nuestra temporal
    private List<Paciente> baseDeDatosFalsa = new ArrayList<>();
    private int contadorId = 1; // Para simular el ID autoincrementable de SQL

    @Override
    public void insertar(Paciente objeto) {
        objeto.setIdUsuario(contadorId++); // Le asignamos un ID falso
        baseDeDatosFalsa.add(objeto);
        System.out.println(" Paciente guardado en memoria: " + objeto.getNombre());
    }

    @Override
    public List<Paciente> obtenerTodos() {
        return baseDeDatosFalsa;
    }


    @Override
    public void actualizar(Paciente objeto) {
        for (int i = 0; i < baseDeDatosFalsa.size(); i++) {
            if (baseDeDatosFalsa.get(i).getIdUsuario() == objeto.getIdUsuario()) {
                baseDeDatosFalsa.set(i, objeto); // Reemplaza el viejo por el nuevo
                System.out.println(" Paciente actualizado: " + objeto.getNombre());
                return;
            }
        }
    }

    @Override
    public void eliminar(int id) {
        // Busca al paciente con ese ID y lo saca de la lista
        baseDeDatosFalsa.removeIf(p -> p.getIdUsuario() == id);
        System.out.println("Paciente eliminado con ID: " + id);
    }
    @Override public Paciente obtenerPorId(int id) { return null; }
    @Override public List<Paciente> buscarPorNombre(String nombre) { return new ArrayList<>(); }
    @Override public Paciente buscarPorUsername(String username) { return null; }
}