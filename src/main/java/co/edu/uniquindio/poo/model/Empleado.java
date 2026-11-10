package co.edu.uniquindio.poo.model;

public abstract class Empleado extends Persona {

    private final String codigo;
    private int minutosDisponibles;

    public Empleado(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento);
        this.codigo = codigo;
        this.minutosDisponibles = minutosDisponibles;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getMinutosDisponibles() {
        return minutosDisponibles;
    }

    public void descontarMinutos(int minutos) {
        this.minutosDisponibles -= minutos;
    }

    public void devolverMinutos(int minutos) {
        this.minutosDisponibles += minutos;
    }
}
