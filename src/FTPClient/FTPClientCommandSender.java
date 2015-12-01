package FTPClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by BALALAIKA on 25.11.2015.
 */
public class FTPClientCommandSender implements Runnable {
    private Socket socket = null;
    private DataOutputStream out = null;
    private boolean isRunning = false;

    public FTPClientCommandSender(Socket socket) {
        try {
            this.socket = socket;
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        isRunning = true;
    }

    public void sendRequest(String request) throws IOException {
        request = request + System.lineSeparator();
        out.writeBytes(request);
    }

    public void stopRunning() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {

        }
    }
}
