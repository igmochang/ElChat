package sample;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;



public class Cliente extends JFrame {

    Cliente(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("chat");

        JLabel labelPuerto = new JLabel("Puerto: ");
        labelPuerto.setBounds(0,-10, 100,50);
        labelPuerto.setText("Puerto: " + serverPuerto);
        add(labelPuerto);

        JButton buttonEnviar = new JButton("+");
        buttonEnviar.setBounds(5,415,50,20);
        add(buttonEnviar);

        setSize(600,500);
        setLayout(null);
        setVisible(true);
    }

    public static int serverPuerto = 0;

    public static void main(String[] args) throws IOException {




        System.out.println("1");

        InetAddress ip = InetAddress.getByName("localhost");
        Scanner scn = new Scanner(System.in);
        System.out.println(Servidor.Puerto);
        Socket s = new Socket(ip,5000);

        serverPuerto = s.getLocalPort();
        Cliente gui = new Cliente();

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        while (true){

            System.out.println(s.getLocalPort());

            System.out.println("4");

            Thread recmen = new Thread(new recibirmensaje(dis));
            recmen.start();

            System.out.println("5");

            Thread envmen = new Thread(new Enviarmensaje(scn, dos));
            envmen.start();

            System.out.println("6");


        }


    }

    private static class recibirmensaje implements Runnable {
        String recieved;
        final DataInputStream dis;

        public recibirmensaje(DataInputStream dis){
            this.dis = dis;
        }

        @Override
        public void run() {
            try {
                recieved = dis.readUTF();
                System.out.println(recieved); // Se escribe mensaje de cliente
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static class Enviarmensaje implements Runnable {
        final String toreturn;
        final DataOutputStream dos;

        public Enviarmensaje(Scanner scn, DataOutputStream dos) throws IOException {
            toreturn = scn.nextLine();
            this.dos = dos;


        }

        @Override
        public void run() {
            try {
                dos.writeUTF(toreturn);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}

