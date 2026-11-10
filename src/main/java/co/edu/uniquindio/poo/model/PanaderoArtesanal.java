package co.edu.uniquindio.poo.model;


public class PanaderoArtesanal extends Empleado implements IProductor {

    private static final int PANES_POR_BANDEJA = 12;
    private static final int MINUTOS_TRADICIONAL = 30;
    private static final int MINUTOS_INTEGRAL = 40;

    public PanaderoArtesanal(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento, codigo, minutosDisponibles);
    }

    @Override
    public int calcularMinutos(TipoPan tipo, int cantidad) {
        int bandejas = cantidad / PANES_POR_BANDEJA;
        if (cantidad % PANES_POR_BANDEJA != 0) {
            bandejas = bandejas + 1;
        }

        int minutosPorBandeja;
        if (tipo == TipoPan.TRADICIONAL) {
            minutosPorBandeja = MINUTOS_TRADICIONAL;
        } else {
            minutosPorBandeja = MINUTOS_INTEGRAL;
        }
        return bandejas * minutosPorBandeja;
    }
}