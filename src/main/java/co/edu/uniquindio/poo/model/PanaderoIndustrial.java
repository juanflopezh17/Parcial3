package co.edu.uniquindio.poo.model;

public class PanaderoIndustrial extends Empleado implements IProductor {

    private static final int PANES_POR_LOTE = 30;
    private static final int MINUTOS_PREPARACION = 20;
    private static final int MINUTOS_TRADICIONAL = 25;
    private static final int MINUTOS_INTEGRAL = 35;

    public PanaderoIndustrial(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento, codigo, minutosDisponibles);
    }

    @Override
    public int calcularMinutos(TipoPan tipo, int cantidad) {
        int lotes = cantidad / PANES_POR_LOTE;
        if (cantidad % PANES_POR_LOTE != 0) {
            lotes = lotes + 1;
        }

        int minutosPorLote;
        if (tipo == TipoPan.TRADICIONAL) {
            minutosPorLote = MINUTOS_TRADICIONAL;
        } else {
            minutosPorLote = MINUTOS_INTEGRAL;
        }
        return MINUTOS_PREPARACION + lotes * minutosPorLote;
    }
}
