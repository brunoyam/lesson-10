import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try {

            URL lenta = new URL("https://lenta.ru/rss");   // открываем подобие сокета до вк.ком

            String s = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(lenta.openStream()));  // создаем поток чтения из него

            while ((s = reader.readLine()) != null) { // считываем все, что нам отдается в документе
                System.out.println(s); // и выводим полученный код на экран
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
