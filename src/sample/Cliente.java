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


/**
 * @Autor Ignacio Morales Chang
 */
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

    /**
     * @param args
     * @throws IOException errores por input o output
     */
    public static void main(String[] args) throws IOException {




        System.out.println("1");

        InetAddress ip = InetAddress.getByName("localhost");
        Scanner scn = new Scanner(System.in);
        System.out.println(Servidor.Puerto);
        Socket s = new Socket(ip,5000);

        serverPuerto = s.getLocalPort();
        Cliente gui = new Cliente();
        System.out.println(s.getLocalPort());

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        while (true){



            Thread recmen = new Thread(new recibirmensaje(dis));
            recmen.start();


            Thread envmen = new Thread(new Enviarmensaje(scn, dos));
            envmen.start();


        }


    }

    private static class recibirmensaje implements Runnable {
        String recieved;
        final DataInputStream dis;

        /**
         * @param dis DataInputStream
         */
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

        /**
         * @param scn Scanner
         * @param dos DataOutputStream
         * @throws IOException errores por input o output
         */
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

