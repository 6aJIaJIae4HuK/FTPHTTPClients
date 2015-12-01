package HTTPClient;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    private Stage primaryStage = null;
    private GridPane rootLayout = null;

    private HTTPClient client = null;

    private ObservableList<String> HTMLText = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("abcdefghijklmnopqrstuvwxyz");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("HTTPClientGui.fxml"));
        rootLayout = fxmlLoader.load();

        Scene scene = new Scene(rootLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        client = new HTTPClient(this);

        Controller controller = fxmlLoader.getController();
        controller.setMain(this);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

            }
        });
    }


    public ObservableList<String> getHTMLText() {
        return HTMLText;
    }

    public HTTPClient getClient() {
        return client;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
