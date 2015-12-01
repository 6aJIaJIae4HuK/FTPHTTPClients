package FTPClient;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by BALALAIKA on 25.11.2015.
 */
public class FTPClientDataReceiver implements Runnable{
    private Socket socket = null;
    private String localPath = null;
    private DataInputStream inputStream = null;

    public FTPClientDataReceiver(String host, Integer port, String localPath) {
        try {
            socket = new Socket(host, port);
            this.localPath = localPath;
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            ArrayList<Byte> bytes = new ArrayList<Byte>();

            byte c;
            try {
                while (true) {
                    c = inputStream.readByte();
                    bytes.add(c);
                }
            }
            catch (EOFException e) {
                OutputStream os = new FileOutputStream(localPath);
                os.write(getBytes(bytes));
                os.flush();
                os.close();
            }
        }
        catch (IOException e) {

        }
    }

    private byte[] getBytes(final ArrayList<Byte> bytes) {
        int len = bytes.size();
        byte[] res = new byte[len];
        for (int i = 0; i < len; i++) {
            res[i] = bytes.get(i);
        }
        return res;
    }
}
