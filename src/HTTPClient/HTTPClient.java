package HTTPClient;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by BALALAIKA on 24.11.2015.
 */

class HTTPClient {
    private Socket socket = null;
    private Main clientGui = null;
    private HTTPReceiver receiver = null;

    public HTTPClient(Main app) {
        clientGui = app;
        receiver = new HTTPReceiver(this);
    }

    public void getTxtFromHttp(String addr) {
        receiver.stopRunnable();

        String host = addr.substring(0, addr.indexOf('/'));
        String path = addr.substring(addr.indexOf('/'));

        String request = "GET " + path + " HTTP/1.1" + System.lineSeparator();
        request = request + "Host: " + host + System.lineSeparator() + System.lineSeparator();

        try {
            socket = new Socket(host, 80);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.writeBytes(request);
            (new Thread(receiver)).start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public Main getClientGui() {
        return clientGui;
    }

    public HTTPReceiver getReceiver() {
        return receiver;
    }
}