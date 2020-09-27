package sample;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;


public class Controller {
    @FXML
    Label labelPort;
    @FXML
    TextField Mensaje;



    public void enviarMensaje() {

        System.out.println(Mensaje.getText());
        Mensaje.clear();


    }

    public static void indicarPuerto(Label label){

        label.setText(String.valueOf(Servidor.Puerto));

    }



}



