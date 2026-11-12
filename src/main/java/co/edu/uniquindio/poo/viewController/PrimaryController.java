package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.App;
import javafx.fxml.FXML;

public class PrimaryController {

    App app;

    @FXML
    void onOpenCrudPedido() {
        app.openCrudPedido();
    }

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void initialize() {
    }
}