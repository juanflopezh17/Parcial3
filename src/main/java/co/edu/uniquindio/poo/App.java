package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.*;
import co.edu.uniquindio.poo.viewController.PedidoViewController;
import co.edu.uniquindio.poo.viewController.PrimaryController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private Stage primaryStage;
    public static Panaderia panaderia = new Panaderia();

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Panadería El Trigal");
        inicializarData();
        openViewPrincipal();
    }

    public void openViewPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/co/edu/uniquindio/poo/primary.fxml"));
            Parent rootLayout = loader.load();
            PrimaryController primaryController = loader.getController();
            primaryController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCrudPedido() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/co/edu/uniquindio/poo/crudPedido.fxml"));
            Parent rootLayout = loader.load();
            PedidoViewController pedidoViewController = loader.getController();
            pedidoViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void inicializarData() {
        panaderia.registrarEmpleado(new PanaderoArtesanal("E1", "Ana", "1001", 480));
        panaderia.registrarEmpleado(new PanaderoIndustrial("E2", "Luis", "1002", 480));
        panaderia.registrarEmpleado(new Cajero("E3", "Marta", "1003", 480, "Caja 1"));
    }
}