package sample;


import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class ClientHandler extends Thread {


    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) throws IOException {

        this.s = s;
        this.dis = dis;
        this.dos = dos;
        dos.writeUTF("Se ha iniciado un chat!");
    }

    @Override
    public void run() {


        //String toreturn;


        while (true) {
            try {
                Thread recmen = new recibirmensaje(this.dis);
                recmen.start();

                Scanner scn = new Scanner(System.in);
                Thread envmen = new Enviarmensaje(scn, this.dos);
                envmen.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static class Enviarmensaje extends Thread {
        String toreturn;
        public Enviarmensaje(Scanner scn, DataOutputStream dos) throws IOException {
            toreturn = scn.nextLine();
            dos.writeUTF(toreturn);
            dos.flush();
        }
    }

    private static class recibirmensaje extends Thread {
        String recieved;
        public recibirmensaje(DataInputStream dis) throws IOException {
            recieved = dis.readUTF();
            System.out.println(recieved); // Se escribe mensaje de cliente
        }
    }

}