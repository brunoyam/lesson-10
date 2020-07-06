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

            while ((s = reader.readLine()) != null) {

                if ("!quit".equals(s)) {
                    break;
                } else {
                    output.writeUTF(s);
                    output.flush();
                }

                String answer = input.readUTF();
                System.out.println(answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
