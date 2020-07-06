import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) {

        try (ServerSocket socket = new ServerSocket(8888)) {
            Socket client = socket.accept();   // сервер бездействует до тех пор, пока клиент к нам не постучится. После того, как клиент пришел, нам нужно создать его сокет

            System.out.println("Client connected");

            DataInputStream input = new DataInputStream(client.getInputStream());  // открываем поток данных ОТ клиента нам - создаем читалку
            DataOutputStream output = new DataOutputStream(client.getOutputStream()); //  открываем поток данных от нас клиенту

            while (!client.isClosed()) {   // до тех пор, пока клиент рбаотает

                String message = input.readUTF();  // считываем его очередное сообщение
                System.out.println("From client: " + message);  // тут же его выводим

                if ("!quit".equals(message)) {  // проверяем, не пришла ли команда выхода
                    break;
                } else {
                    output.writeUTF("Message delivered: " + message);  // если пришла не команда вхыода - отвечаем клиенту, что мы все получили, все ок
                    output.flush();  // стримы ленивые - не забываем после каждой записи flush()
                }
            }

            input.close();   // если пришла команда выхода - нужно закрыть все потоки
            output.close();

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
