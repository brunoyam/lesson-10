import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {

        try (ServerSocket socket = new ServerSocket(8888)) {
            while (!socket.isClosed()) {
                Socket client = socket.accept();   // сервер бездействует до тех пор, пока клиент к нам не постучится. После того, как клиент пришел, нам нужно создать его сокет
                System.out.println("Client connected");
                clients.add(client);
                new Thread(new MonoThreadClientHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
