package server;

import utilities.DataKeeper;
import utilities.RSA;
import utilities.SHA;

import java.io.File;

public class Server {

    private final File shaFile; // файл для записи sha
    private final File rsaFile;
    private final String path; // куда записывать для клиента

    private File data; // исходный файл

    private SHA.AssignedSHA assignedSHA; // подписанный sha

    private SHA sha; // sha, основанный на файле

    private RSA rsa; // rsa

    public Server(final File data, final String path) {
        this.data = data;
        this.path = path;
        this.shaFile = new File(path + "sha.txt");
        this.rsaFile = new File(path + "rsa.txt");
    }

    public void generateRSA() {
        rsa = RSA.Generator.generateRSA();
        System.out.println("RSA:");
        System.out.println(rsa.toString());
        System.out.println();
    }

    public void createSHA() {
        if (data == null) {
            throw new NullPointerException("File must be definited");
        }

        sha = SHA.SHA1Crypter.getSHA1(data);

        System.out.println("SHA:");
        System.out.println(sha.toString());
        System.out.println();
    }

    public void assignSHA() {
        if (sha == null) {
            throw new NullPointerException("SHA must be definited");
        }

        assignedSHA = new SHA.AssignedSHA(RSA.Assigner.assign(sha.toString(), rsa.getCloseRSA()));

        System.out.println("Assigned SHA:");
        System.out.println(assignedSHA);
        System.out.println();
    }

    public void sendAllData() {
        if (assignedSHA == null) {
            throw new NullPointerException("SHA must be assigned");
        }

        System.out.println("Data is sending...");

        sendFile();
        sendSHA();
        sendRSA();

        System.out.println("Data was sent!");
        System.out.println();

        clearAll();
    }

    private void sendSHA() {
        if (assignedSHA == null) {
            throw new NullPointerException("SHA must be assigned");
        }

        DataKeeper.writeTextToFile(assignedSHA.toString(), shaFile);
    }

    private void sendFile() {
        DataKeeper.copyFile(data, path, "data.txt");
    }

    private void sendRSA() {
        DataKeeper.writeTextToFile(rsa.getOpenRSA().toString(), rsaFile);
    }

    private void clearAll() {
        sha = null;
        assignedSHA = null;
        rsa = null;
    }

}
