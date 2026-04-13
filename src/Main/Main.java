import bd.PacienteDAO;

public class Main {
    public static void main(String[] args) {

        PacienteDAO dao = new PacienteDAO();

        // 🔹 INSERT
        dao.guardarPaciente(
                "Carlos Perez",
                "001-5555555-5",
                "809-222-2222",
                28
        );

        // 🔹 SELECT
        System.out.println("---- LISTA DE PACIENTES ----");
        dao.listarPacientes();

        // 🔹 UPDATE
        dao.actualizarPaciente(1, "Carlos Actualizado");

        // 🔹 DELETE
        dao.eliminarPaciente(2);
    }
}