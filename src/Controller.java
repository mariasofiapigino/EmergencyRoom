
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    public Button btn_generar;
    public TextField txt_cantSim;
    public TextField txt_desde;
    public TextField txt_hasta;
    public ImageView img_check;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_generar.setOnAction(e -> {
            Simulacion sim = null;
            try {
                sim = new Simulacion(Integer.parseInt(txt_cantSim.getText()), 0, Integer.parseInt(txt_desde.getText()), Integer.parseInt(txt_hasta.getText()));
                Paciente paciente = new Paciente();
                paciente.reset();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}



