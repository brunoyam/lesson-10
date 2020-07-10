import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {

    private Socket client;

    public MonoThreadClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(client.getInputStream());
            DataOutputStream output = new DataOutputStream(client.getOutputStream())
        ) {
            while (!client.isClosed()) {   // до тех пор, пока клиент рбаотает

                String message = input.readUTF();  // считываем его очередное сообщение
                System.out.println("From client: " + message);  // тут же его выводим

                if ("!quit".equals(message)) {  // проверяем, не пришла ли команда выхода
                    break;
                } else {
                    //output.writeUTF("Message delivered: " + message);  // если пришла не команда вхыода - отвечаем клиенту, что мы все получили, все ок
                    //output.flush();  // стримы ленивые - не забываем после каждой записи flush()

                    for (Socket cl : Server.clients) {   // получаем список всех клиентов, которые в данный момент подключены к серверу
                        try {
                            DataOutputStream clientOutput = new DataOutputStream(cl.getOutputStream());  // открываем соединение с ним
                            clientOutput.writeUTF("Message delivered: " + message);  // отправляем сообщение
                            clientOutput.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
