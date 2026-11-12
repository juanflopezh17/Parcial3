package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.App;
import co.edu.uniquindio.poo.controller.PanaderiaController;
import co.edu.uniquindio.poo.model.Empleado;
import co.edu.uniquindio.poo.model.EstadoPedido;
import co.edu.uniquindio.poo.model.Pedido;
import co.edu.uniquindio.poo.model.TipoPan;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PedidoViewController {

    PanaderiaController panaderiaController;
    ObservableList<Pedido> listPedidos = FXCollections.observableArrayList();
    ObservableList<Empleado> listEmpleados = FXCollections.observableArrayList();
    Pedido selectedPedido;
    private App app;

    @FXML private TextField txtCodigo;
    @FXML private TextField txtCliente;
    @FXML private ComboBox<TipoPan> cbTipoPan;
    @FXML private TextField txtCantidad;
    @FXML private Label lblMensaje;


    @FXML private TableView<Pedido> tblListPedido;
    @FXML private TableColumn<Pedido, String> tbcCodigo;
    @FXML private TableColumn<Pedido, String> tbcCliente;
    @FXML private TableColumn<Pedido, String> tbcTipoPan;
    @FXML private TableColumn<Pedido, String> tbcCantidad;
    @FXML private TableColumn<Pedido, String> tbcEstado;
    @FXML private TableColumn<Pedido, String> tbcEmpleado;
    @FXML private TableColumn<Pedido, String> tbcMinutos;


    @FXML private TableView<Empleado> tblListEmpleado;
    @FXML private TableColumn<Empleado, String> tbcEmpCodigo;
    @FXML private TableColumn<Empleado, String> tbcEmpNombre;
    @FXML private TableColumn<Empleado, String> tbcEmpTipo;
    @FXML private TableColumn<Empleado, String> tbcEmpMinutos;

    @FXML
    void onRegistrarPedido() {
        registrarPedido();
    }

    @FXML
    void onAsignarPedido() {
        asignarPedido();
    }

    @FXML
    void onCancelarPedido() {
        cancelarPedido();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    @FXML
    void initialize() {
        panaderiaController = new PanaderiaController(App.panaderia);
        initView();
    }

    private void initView() {
        cbTipoPan.setItems(FXCollections.observableArrayList(TipoPan.values()));
        initDataBinding();
        obtenerDatos();
        tblListPedido.setItems(listPedidos);
        tblListEmpleado.setItems(listEmpleados);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreCliente()));
        tbcTipoPan.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoPan().toString()));
        tbcCantidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidad())));
        tbcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado().toString()));
        tbcEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getCodigoEmpleado() == null ? "-" : cellData.getValue().getCodigoEmpleado()));
        tbcMinutos.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getEstado() == EstadoPedido.ASIGNADO
                        ? String.valueOf(cellData.getValue().getMinutosReservados()) : "-"));

        tbcEmpCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcEmpNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcEmpTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        tbcEmpMinutos.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().getMinutosDisponibles())));
    }

    private void obtenerDatos() {
        listPedidos.setAll(panaderiaController.obtenerListaPedidos());
        listEmpleados.setAll(panaderiaController.obtenerListaEmpleados());
        tblListPedido.refresh();
        tblListEmpleado.refresh();
    }

    private void listenerSelection() {
        tblListPedido.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedPedido = newSelection;
        });
    }

    private void registrarPedido() {
        try {
            if (cbTipoPan.getValue() == null) {
                throw new IllegalArgumentException("Seleccione el tipo de pan");
            }
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            panaderiaController.registrarPedido(txtCodigo.getText().trim(), txtCliente.getText().trim(),
                    cbTipoPan.getValue(), cantidad);
            mostrarOk("Pedido " + txtCodigo.getText().trim() + " registrado");
            limpiarCamposPedido();
        } catch (NumberFormatException e) {
            mostrarError("La cantidad debe ser un número entero");
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarError(e.getMessage());
        }
        obtenerDatos();
    }

    private void asignarPedido() {
        if (selectedPedido == null) {
            mostrarError("Seleccione un pedido de la tabla");
            return;
        }
        try {
            panaderiaController.asignarPedido(selectedPedido.getCodigo());
            mostrarOk("Pedido " + selectedPedido.getCodigo() + " asignado");
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarError(e.getMessage());
        }
        obtenerDatos();
    }

    private void cancelarPedido() {
        if (selectedPedido == null) {
            mostrarError("Seleccione un pedido de la tabla");
            return;
        }
        try {
            panaderiaController.cancelarPedido(selectedPedido.getCodigo());
            mostrarOk("Pedido " + selectedPedido.getCodigo() + " cancelado");
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarError(e.getMessage());
        }
        obtenerDatos();
    }

    private void limpiarSeleccion() {
        tblListPedido.getSelectionModel().clearSelection();
        limpiarCamposPedido();
        lblMensaje.setText("");
    }

    private void limpiarCamposPedido() {
        txtCodigo.clear();
        txtCliente.clear();
        txtCantidad.clear();
        cbTipoPan.getSelectionModel().clearSelection();
    }

    private void mostrarOk(String mensaje) {
        lblMensaje.setStyle("-fx-text-fill: green;");
        lblMensaje.setText(mensaje);
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setStyle("-fx-text-fill: red;");
        lblMensaje.setText(mensaje);
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
