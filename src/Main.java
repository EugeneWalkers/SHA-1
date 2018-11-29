import client.Client;
import server.Server;

import java.io.File;

public class Main {

    final static File file = new File(""); // Вписать имя файла здесь
    final static String path = "fromServerToClient/";

    public static void main(final String[] args){
        final Server server = new Server(file, path);
        final Client client = new Client(path);

        server.generateRSA();
        server.createSHA();
        server.assignSHA();
        server.sendAllData();

        client.receiveAllData();
        client.reassignReceivedSHA();
        client.calculateSHAOfReceivedFile();

        final boolean isOriginal = client.checkEqualsSHA();

        if (isOriginal){
            System.out.println("Ура!");
        } else {
            System.out.println("Лохотрон...");
        }

    }

}
