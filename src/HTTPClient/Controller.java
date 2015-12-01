package HTTPClient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {
    private Main main;

    @FXML
    private ListView<String> HTMLTextView;

    @FXML
    private TextField addressField;

    @FXML
    private Button openHTMLTextButton;

    @FXML
    private Label receivedBytesCountLabel;

    @FXML
    private void openHTMLText() {
        main.getHTMLText().clear();
        main.getClient().getTxtFromHttp(addressField.getText());
    }

    public void setMain(Main main) {
        this.main = main;

        if (HTMLTextView == null)
            HTMLTextView = new ListView<String>();
        HTMLTextView.setItems(main.getHTMLText());

        if (receivedBytesCountLabel == null)
            receivedBytesCountLabel = new Label();
        receivedBytesCountLabel.textProperty().bind(main.getClient().getReceiver().getReceivedString());
    }
}
