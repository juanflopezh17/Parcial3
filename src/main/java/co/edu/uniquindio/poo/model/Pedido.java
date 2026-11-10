package co.edu.uniquindio.poo.model;

public class Pedido {

    private final String codigo;
    private final String nombreCliente;
    private final TipoPan tipoPan;
    private final int cantidad;
    private EstadoPedido estado;
    private String codigoEmpleado;
    private int minutosReservados;

    public Pedido(String codigo, String nombreCliente, TipoPan tipoPan, int cantidad) {
        this.codigo = codigo;
        this.nombreCliente = nombreCliente;
        this.tipoPan = tipoPan;
        this.cantidad = cantidad;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public void asignar(String codigoEmpleado, int minutosReservados) {
        this.codigoEmpleado = codigoEmpleado;
        this.minutosReservados = minutosReservados;
        this.estado = EstadoPedido.ASIGNADO;
    }

    public void cancelar() {
        this.estado = EstadoPedido.CANCELADO;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public TipoPan getTipoPan() {
        return tipoPan;
    }

    public int getCantidad() {
        return cantidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public int getMinutosReservados() {
        return minutosReservados;
    }
}
