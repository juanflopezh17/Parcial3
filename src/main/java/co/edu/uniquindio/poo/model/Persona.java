package co.edu.uniquindio.poo.model;

public class Persona {

    private final String nombre;
    private final String documento;

    public Persona(String nombre, String documento) {
        this.nombre = nombre;
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }
}
