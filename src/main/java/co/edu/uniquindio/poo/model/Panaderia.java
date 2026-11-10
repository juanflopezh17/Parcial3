package co.edu.uniquindio.poo.model;
import java.util.ArrayList;
import java.util.List;

public class Panaderia {

    private final List<Empleado> empleados = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();


    public void registrarEmpleado(Empleado empleado) {
        if (buscarEmpleado(empleado.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un empleado con el código " + empleado.getCodigo());
        }
        empleados.add(empleado);
    }

    public Empleado buscarEmpleado(String codigo) {
        for (Empleado e : empleados) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        return null;
    }


    public void registrarPedido(String codigo, String nombreCliente, TipoPan tipoPan, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (buscarPedido(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un pedido con el código " + codigo);
        }
        pedidos.add(new Pedido(codigo, nombreCliente, tipoPan, cantidad));
    }

    public void asignarPedido(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido " + codigo + " no existe");
        }
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("El pedido no está pendiente");
        }

        Empleado elegido = null;
        int menorTiempo = Integer.MAX_VALUE;

        for (Empleado e : empleados) {
            if (e instanceof IProductor) {
                IProductor productor = (IProductor) e;
                int tiempo = productor.calcularMinutos(pedido.getTipoPan(), pedido.getCantidad());
                if (e.getMinutosDisponibles() >= tiempo && tiempo < menorTiempo) {
                    elegido = e;
                    menorTiempo = tiempo;
                }
            }
        }

        if (elegido == null) {
            throw new IllegalStateException("Ningún panadero tiene tiempo suficiente para el pedido");
        }
        elegido.descontarMinutos(menorTiempo);
        pedido.asignar(elegido.getCodigo(), menorTiempo);
    }



    public void cancelarPedido(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido " + codigo + " no existe");
        }
        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new IllegalStateException("El pedido ya está cancelado");
        }

        if (pedido.getEstado() == EstadoPedido.ASIGNADO) {
            Empleado empleado = buscarEmpleado(pedido.getCodigoEmpleado());
            empleado.devolverMinutos(pedido.getMinutosReservados());
        }
        pedido.cancelar();
    }

    public Pedido buscarPedido(String codigo) {
        for (Pedido p : pedidos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public List<Empleado> getEmpleados() {
        return new ArrayList<>(empleados);
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }
}