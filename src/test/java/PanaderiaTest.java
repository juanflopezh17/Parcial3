import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.logging.Logger;
import co.edu.uniquindio.poo.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PanaderiaTest {


    private static final Logger LOG = Logger.getLogger(PanaderiaTest.class.getName());

    /**
     * Test registrar Pedido Valido
     */
    @Test
    @DisplayName("Registrar P01 por 25 panes tradicionales")
    public void registrarPedidoValido() {
        LOG.info("Inicio de prueba registrarPedidoValido...");
        Panaderia panaderia = new Panaderia();
        panaderia.registrarEmpleado(new PanaderoArtesanal("Ana", "1001", "E1", 100));
        panaderia.registrarEmpleado(new PanaderoIndustrial("Carlos", "1002", "E2", 45));

        panaderia.registrarPedido("P01", "Cafe lucerna", TipoPan.TRADICIONAL, 25);

        Pedido pedido = panaderia.buscarPedido("P01");
        assertNotNull(pedido);
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertEquals(25, pedido.getCantidad());
        assertEquals(100, panaderia.buscarEmpleado("E1").getMinutosDisponibles());
        assertEquals(45, panaderia.buscarEmpleado("E2").getMinutosDisponibles());
        LOG.info("Fin de la prueba");
    }

    /**
     * Test registrar Codigo Duplicado Tras Cancelar
     */
    @Test
    @DisplayName("Registrar con código repetido de un pedido cancelado")
    public void registrarCodigoDuplicadoTrasCancelar() {
        LOG.info("Inicio de prueba registrarCodigoDuplicadoTrasCancelar...");
        Panaderia panaderia = new Panaderia();
        panaderia.registrarPedido("P01", "Cafe lucerna", TipoPan.TRADICIONAL, 25);
        panaderia.cancelarPedido("P01");
        Pedido original = panaderia.buscarPedido("P01");

        assertThrows(IllegalArgumentException.class,
                () -> panaderia.registrarPedido("P01", "Restaurante Bianco", TipoPan.INTEGRAL, 10));

        assertIterableEquals(List.of(original), panaderia.getPedidos());
        assertEquals(EstadoPedido.CANCELADO, original.getEstado());
        assertEquals("Cafe lucerna", original.getNombreCliente());
        LOG.info("Fin de la prueba");
    }

    /**
     * Test registrar Cantidad Cero
     */
    @Test
    @DisplayName("Registrar un pedido de cero panes")
    public void registrarCantidadCero() {
        LOG.info("Inicio de prueba registrarCantidadCero...");
        Panaderia panaderia = new Panaderia();

        assertThrows(IllegalArgumentException.class,
                () -> panaderia.registrarPedido("P01", "Cafe lucerna", TipoPan.TRADICIONAL, 0));

        assertTrue(panaderia.getPedidos().isEmpty());
        LOG.info("Fin de la prueba");
    }

    /**
     * Test asignar Al Que Menos Tarda
     */
    @Test
    @DisplayName("Asignar al panadero que menos tarda")
    public void asignarAlQueMenosTarda() {
        LOG.info("Inicio de prueba asignarAlQueMenosTarda...");
        Panaderia panaderia = new Panaderia();
        panaderia.registrarEmpleado(new PanaderoArtesanal("Mariana", "1001", "E1", 100));
        panaderia.registrarEmpleado(new PanaderoIndustrial("Ruben", "1002", "E2", 45));
        panaderia.registrarPedido("P01", "Cafe lucerna", TipoPan.TRADICIONAL, 25);

        panaderia.asignarPedido("P01");

        Pedido pedido = panaderia.buscarPedido("P01");
        assertEquals(EstadoPedido.ASIGNADO, pedido.getEstado());
        assertEquals("E2", pedido.getCodigoEmpleado());
        assertEquals(45, pedido.getMinutosReservados());
        assertEquals(0, panaderia.buscarEmpleado("E2").getMinutosDisponibles());
        assertEquals(100, panaderia.buscarEmpleado("E1").getMinutosDisponibles());
        LOG.info("Fin de la prueba");
    }

    /**
     * Test asignar Empate Por Orden De Registro
     */
    @Test
    @DisplayName("Asignar con empate: gana el primero registrado")
    public void asignarEmpatePorOrdenDeRegistro() {
        LOG.info("Inicio de prueba asignarEmpatePorOrdenDeRegistro...");
        Panaderia panaderia = new Panaderia();
        panaderia.registrarEmpleado(new PanaderoArtesanal("Carolina", "1001", "E1", 80));
        panaderia.registrarEmpleado(new PanaderoArtesanal("Diana", "1002", "E2", 80));
        panaderia.registrarEmpleado(new PanaderoIndustrial("Esteban", "1003", "E3", 54));
        panaderia.registrarPedido("P01", "Cafe lucerna", TipoPan.INTEGRAL, 13);

        panaderia.asignarPedido("P01");

        Pedido pedido = panaderia.buscarPedido("P01");
        assertEquals(EstadoPedido.ASIGNADO, pedido.getEstado());
        assertEquals("E1", pedido.getCodigoEmpleado());
        assertEquals(80, pedido.getMinutosReservados());
        assertEquals(0, panaderia.buscarEmpleado("E1").getMinutosDisponibles());
        assertEquals(80, panaderia.buscarEmpleado("E2").getMinutosDisponibles());
        assertEquals(54, panaderia.buscarEmpleado("E3").getMinutosDisponibles());
        LOG.info("Fin de la prueba");
    }

    /**
     * Test asignar Sin Tiempo Suficiente
     */
    @Test
    @DisplayName("Asignar cuando ningún panadero tiene tiempo suficiente")
    public void asignarSinTiempoSuficiente() {
        LOG.info("Inicio de prueba asignarSinTiempoSuficiente...");
        Panaderia panaderia = new Panaderia();
        panaderia.registrarEmpleado(new PanaderoIndustrial("Enrique", "1001", "E1", 44));
        panaderia.registrarEmpleado(new Cajero("Lucía", "1002", "E2", 120, "Caja 1"));
        panaderia.registrarPedido("P01", "Cafe lucerna", TipoPan.TRADICIONAL, 25);

        assertThrows(IllegalStateException.class, () -> panaderia.asignarPedido("P01"));

        Pedido pedido = panaderia.buscarPedido("P01");
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertNull(pedido.getCodigoEmpleado());
        assertEquals(0, pedido.getMinutosReservados());
        assertEquals(44, panaderia.buscarEmpleado("E1").getMinutosDisponibles());
        assertEquals(120, panaderia.buscarEmpleado("E2").getMinutosDisponibles());
        LOG.info("Fin de la prueba");
    }

    /**
     * Test cancelar Asignado Devuelve Minutos
     */
    @Test
    @DisplayName("Cancelar un pedido asignado devuelve solo sus minutos")
    public void cancelarAsignadoDevuelveMinutos() {
        LOG.info("Inicio de prueba cancelarAsignadoDevuelveMinutos...");
        Panaderia panaderia = new Panaderia();
        panaderia.registrarEmpleado(new PanaderoIndustrial("Luis", "1001", "E1", 120));
        panaderia.registrarPedido("P01", "Cafe lucerna", TipoPan.TRADICIONAL, 25);
        panaderia.registrarPedido("P02", "Restaurante Bianco", TipoPan.TRADICIONAL, 25);
        panaderia.asignarPedido("P01");
        panaderia.asignarPedido("P02");
        assertEquals(30, panaderia.buscarEmpleado("E1").getMinutosDisponibles());

        panaderia.cancelarPedido("P01");

        assertEquals(75, panaderia.buscarEmpleado("E1").getMinutosDisponibles());
        assertEquals(EstadoPedido.CANCELADO, panaderia.buscarPedido("P01").getEstado());
        assertEquals(EstadoPedido.ASIGNADO, panaderia.buscarPedido("P02").getEstado());
        LOG.info("Fin de la prueba");
    }

    /**
     * Test cancelar Pendiente Conserva Minutos
     */
    @Test
    @DisplayName("Cancelar un pedido pendiente conserva los minutos")
    public void cancelarPendienteConservaMinutos() {
        LOG.info("Inicio de prueba cancelarPendienteConservaMinutos...");
        Panaderia panaderia = new Panaderia();
        panaderia.registrarEmpleado(new PanaderoArtesanal("Ana", "1001", "E1", 100));
        panaderia.registrarPedido("P01", "Cafe lucerna", TipoPan.TRADICIONAL, 25);

        panaderia.cancelarPedido("P01");

        assertEquals(EstadoPedido.CANCELADO, panaderia.buscarPedido("P01").getEstado());
        assertTrue(panaderia.getPedidos().contains(panaderia.buscarPedido("P01")));
        assertEquals(1, panaderia.getPedidos().size());
        assertEquals(100, panaderia.buscarEmpleado("E1").getMinutosDisponibles());
        LOG.info("Fin de la prueba");
    }

    /**
     * Test cancelar Dos Veces
     */
    @Test
    @DisplayName("Cancelar dos veces devuelve los minutos una sola vez")
    public void cancelarDosVeces() {
        LOG.info("Inicio de prueba cancelarDosVeces...");
        Panaderia panaderia = new Panaderia();
        panaderia.registrarEmpleado(new PanaderoIndustrial("Guillermo", "1001", "E1", 100));
        panaderia.registrarPedido("P01", "Cafe lucerna", TipoPan.TRADICIONAL, 25);
        panaderia.asignarPedido("P01");
        assertEquals(55, panaderia.buscarEmpleado("E1").getMinutosDisponibles());

        panaderia.cancelarPedido("P01");
        assertEquals(100, panaderia.buscarEmpleado("E1").getMinutosDisponibles());

        assertThrows(IllegalStateException.class, () -> panaderia.cancelarPedido("P01"));

        assertEquals(100, panaderia.buscarEmpleado("E1").getMinutosDisponibles());
        assertEquals(EstadoPedido.CANCELADO, panaderia.buscarPedido("P01").getEstado());
        LOG.info("Fin de la prueba");
    }
}
