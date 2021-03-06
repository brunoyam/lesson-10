import java.io.*;
import java.net.Socket;

public class Client {


    public static void main(String[] args) {

        try (Socket server = new Socket("localhost", 8888);
             DataInputStream input = new DataInputStream(server.getInputStream());
             DataOutputStream output = new DataOutputStream(server.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String s = "";
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            System.out.println(input.readUTF());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            if (!readThread.isAlive()) {
                readThread.start();
            }
            while ((s = reader.readLine()) != null) {

                if ("!quit".equals(s)) {
                    break;
                } else {
                    output.writeUTF(s);
                    output.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
