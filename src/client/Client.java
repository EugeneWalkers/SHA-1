package client;

import javafx.util.Pair;
import utilities.DataKeeper;
import utilities.RSA;
import utilities.SHA;

import java.io.File;
import java.math.BigInteger;

public class Client {
    private final String path; // откуда считывать
    private final File rsaFile;
    private final File shaFile;

    private File data;
    private Pair<BigInteger, BigInteger> openRSA;
    private SHA.AssignedSHA receivedAssignedSHA;

    private SHA receivedSHA;
    private SHA shaOfReceivedFile;

    public Client(final String path) {
        this.path = path;
        this.data = new File(path + "data.txt");
        this.rsaFile = new File(path + "rsa.txt");
        this.shaFile = new File(path + "sha.txt");
    }

    public void receiveAllData() {
        System.out.println("Data is receiving...");

        receiveData();
        receiveSHA();
        receiveRSA();

        System.out.println("Data was received!");
        System.out.println();
        System.out.println("Received assigned sha:");
        System.out.println(receivedAssignedSHA.toString());
        System.out.println("Received RSA:");
        System.out.println(openRSA.toString());
        System.out.println();
    }

    private void receiveData() {
        this.data = new File(path + "data.txt");
    }

    private void receiveSHA() {
        receivedAssignedSHA = new SHA.AssignedSHA(DataKeeper.getStringFromFile(shaFile));
    }

    private void receiveRSA() {
        openRSA = RSA.getRSAKeyFromString(DataKeeper.getStringFromFile(rsaFile));
    }

    public void reassignReceivedSHA() {
        receivedSHA = new SHA(RSA.Assigner.deassign(receivedAssignedSHA.toString(), openRSA));

        System.out.println("Reassigned received SHA:");
        System.out.println(receivedSHA);
        System.out.println();
    }

    public void calculateSHAOfReceivedFile() {
        shaOfReceivedFile = SHA.SHA1Crypter.getSHA1(data);
    }

    public boolean checkEqualsSHA() {
        return receivedSHA.toString().equals(shaOfReceivedFile.toString());
    }

}
