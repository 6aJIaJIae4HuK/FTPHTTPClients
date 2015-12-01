package FTPClient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;

public class Controller {
    @FXML
    private TextField fromField;

    @FXML
    private TextField toField;

    @FXML
    private Button getFileButton;

    @FXML
    private void getFile() {
        main.getClient().receiveFile(fromField.getText(), toField.getText());
    }

    private Main main = null;
    public void setMain(Main main) {
        this.main = main;
    }
}
