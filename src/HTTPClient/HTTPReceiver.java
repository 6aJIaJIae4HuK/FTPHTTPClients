package HTTPClient;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableObjectValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by BALALAIKA on 24.11.2015.
 */
public class HTTPReceiver implements Runnable {
    HTTPClient client = null;
    private boolean isRunning = false;
    private Integer receivedBytesCount;
    private StringProperty received = new SimpleStringProperty("");

    public HTTPReceiver(HTTPClient client) {
        this.client = client;
    }

    public void run() {
        isRunning = true;
        Socket socket = client.getSocket();
        String line;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receivedBytesCount = 0;
            while (isRunning) {
                line = in.readLine();
                if (line != null) {
                    addLineToHTMLText(line);
                    receivedBytesCount = receivedBytesCount + line.getBytes().length + System.lineSeparator().getBytes().length;
                    updateReceivedString();
                }
                else {
                    isRunning = false;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                socket = null;
            }
        }
    }

    public void stopRunnable() {
        isRunning = false;
    }

    public StringProperty getReceivedString() {
        return received;
    }

    private void addLineToHTMLText(final String line) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                client.getClientGui().getHTMLText().add(line);
            }
        });
    }

    private void updateReceivedString() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                received.setValue("Received: " + receivedBytesCount.toString());
            }
        });
    }
}
