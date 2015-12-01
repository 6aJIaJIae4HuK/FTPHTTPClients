package FTPClient;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by BALALAIKA on 25.11.2015.
 */
public class FTPClientCommandReceiver implements Runnable {
    private Socket socket = null;
    private BufferedReader reader = null;
    private Queue<String> requests = new LinkedList<String>();
    private boolean isRunning = false;
    private Pair<String, Integer> addressToReceiveData = null;

    public FTPClientCommandReceiver(Socket socket) {
        try {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            requests = new LinkedList<String>();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        isRunning = true;
    }

    public String getTypeOfQuery() {
        String currentRequest = requests.peek();
        if (currentRequest == null) {
            return null;
        }
        else {
            int last = currentRequest.indexOf(' ');
            if (last != -1)
                return currentRequest.substring(0, currentRequest.indexOf(' '));
            else
                return currentRequest;
        }
    }

    @Override
    public void run() {
        try {
            String line;
            while (isRunning) {
                line = reader.readLine();
                int code = Integer.parseInt(line.substring(0, 3));
                if (code == 220)
                    continue;
                String type = getTypeOfQuery();
                if (type.equals("USER")) {
                    if (code == 331) {
                        requests.poll();
                    }
                }
                else
                if (type.equals("PASS")) {
                    if (code == 230) {
                        requests.poll();
                    }
                }
                else
                if (type.equals("CWD")) {
                    if (code == 250) {
                        requests.poll();
                    }
                }
                else
                if (type.equals("QUIT")) {
                    if (code == 221) {
                        requests.poll();
                        isRunning = false;
                    }
                }
                else
                if (type.equals("PASV")) {
                    if (code == 227) {
                        int[] nums = new int[6];
                        line = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
                        int ind = 0;
                        for (int i = 0; i < 6; i++) {
                            int tmp = line.indexOf(',', ind + 1);
                            if (tmp == -1)
                                nums[i] = Integer.parseInt(line.substring(ind + 1));
                            else
                                nums[i] = Integer.parseInt(line.substring(ind + 1, tmp));
                            ind = tmp;
                        }
                        nums[0] = 192;
                        nums[1] = 168;
                        String address = "";
                        for (int i = 0; i < 4; i++)
                            address = address + (new Integer(nums[i])).toString() + ".";
                        address = address.substring(0, address.lastIndexOf('.'));
                        Integer port = nums[4]*256+nums[5];
                        addressToReceiveData = new Pair<String, Integer>(address, port);
                        requests.poll();
                    }
                }
                else
                if (type.equals("RETR")) {
                    if (code == 150) {

                    }
                    else
                    if (code == 250) {
                        requests.poll();
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Queue<String> getRequests() {
        return requests;
    }

    public Pair<String, Integer> getAddressToReceiveData() {
        return addressToReceiveData;
    }
}
