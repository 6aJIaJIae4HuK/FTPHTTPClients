package FTPClient;

import javafx.util.Pair;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by BALALAIKA on 25.11.2015.
 */
public class FTPClient {
    private Main clientGUI = null;
    private FTPClientCommandSender sender = null;
    private FTPClientCommandReceiver receiver = null;
    private Socket socket = null;

    public FTPClient(Main clientGUI) {
        this.clientGUI = clientGUI;
        try {
            socket = new Socket("192.168.213.129", 21);
            sender = new FTPClientCommandSender(socket);
            receiver = new FTPClientCommandReceiver(socket);

            (new Thread(sender)).start();
            (new Thread(receiver)).start();

            sendRequest("USER balalaika");
            sendRequest("PASS qwerty");
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveFile(String pathFrom, String pathTo) {
        if (pathFrom.charAt(pathFrom.length() - 1) == '/') {

        }
        else {
            String path = pathFrom.substring(0, pathFrom.lastIndexOf('/'));
            String name = pathFrom.substring(pathFrom.lastIndexOf('/') + 1);
            sendRequest("PASV");
            Pair<String, Integer> address = null;
            while ((address = receiver.getAddressToReceiveData()) == null);
            sendRequest("CWD " + path);
            sendRequest("RETR " + name);
            Thread thread = new Thread(new FTPClientDataReceiver(address.getKey(), address.getValue(), pathTo));
            thread.start();
        }
    }

    public void sendRequest(String request) {
        try {
            sender.sendRequest(request);
            receiver.getRequests().add(request);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
