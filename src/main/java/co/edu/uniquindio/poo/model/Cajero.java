package co.edu.uniquindio.poo.model;

public class Cajero extends Empleado {

    private final String cajaAsignada;

    public Cajero(String nombre, String documento, String codigo, int minutosDisponibles, String cajaAsignada) {
        super(nombre, documento, codigo, minutosDisponibles);
        this.cajaAsignada = cajaAsignada;
    }

    public String getCajaAsignada() {
        return cajaAsignada;
    }
}
