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

    private SHA assignedSHA; // подписанный sha

    private SHA sha; // sha, основанный на файле

    private RSA rsa; // rsa

    {
        shaFile = new File("sha.txt");
        rsaFile = new File("rsa.txt");
    }

    public Server(final File data, final String path) {
        this.data = data;
        this.path = path;
    }

    public void generateRSA(){
        rsa = RSA.Generator.generateRSA();
    }

    public void createSHA() {
        if (data == null) {
            throw new NullPointerException("File must be definited");
        }

        sha = SHA.SHA1Crypter.getSHA1(data);
    }

    public void assignSHA() {
        if (sha == null) {
            throw new NullPointerException("SHA must be definited");
        }

        assignedSHA = new SHA(RSA.Assigner.assign(sha.toString(), rsa.getCloseRSA()));
    }

    public void sendAllData() {
        if (assignedSHA == null) {
            throw new NullPointerException("SHA must be assigned");
        }

        sendFile();
        sendSHA();
        sendRSA();

        clearAll();
    }

    private void sendSHA() {
        if (assignedSHA == null) {
            throw new NullPointerException("SHA must be assigned");
        }

        DataKeeper.writeTextToFile(assignedSHA.toString(), shaFile);
    }

    private void sendFile() {
        DataKeeper.copyFile(data, path + "data.txt");
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
