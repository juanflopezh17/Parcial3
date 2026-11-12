package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Empleado;
import co.edu.uniquindio.poo.model.Panaderia;
import co.edu.uniquindio.poo.model.Pedido;
import co.edu.uniquindio.poo.model.TipoPan;

import java.util.Collection;

public class PanaderiaController {

    Panaderia panaderia;

    public PanaderiaController(Panaderia panaderia) {
        this.panaderia = panaderia;
    }

    public void registrarPedido(String codigo, String cliente, TipoPan tipoPan, int cantidad) {
        panaderia.registrarPedido(codigo, cliente, tipoPan, cantidad);
    }

    public void asignarPedido(String codigo) {
        panaderia.asignarPedido(codigo);
    }

    public void cancelarPedido(String codigo) {
        panaderia.cancelarPedido(codigo);
    }

    public Collection<Pedido> obtenerListaPedidos() {
        return panaderia.getPedidos();
    }

    public Collection<Empleado> obtenerListaEmpleados() {
        return panaderia.getEmpleados();
    }
}
