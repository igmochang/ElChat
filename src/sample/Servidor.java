package sample;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Autor Ignacio Morales Chang
 */
public class Servidor extends JFrame{

    private JFrame mensaje;

    public Servidor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("chat");



        mensaje = new JFrame("Mensajeee");
        mensaje.setSize(500,200);
        mensaje.setLayout(new GridLayout(2,2,5,10));
        JLabel para = new JLabel("Para: ");
        mensaje.add(para);
        JTextField to = new JTextField(15);
        to.setColumns(1);
        to.setAutoscrolls(true);
        mensaje.add(to);
        JTextArea men = new JTextArea("Inserte el mensaje que aquí");
        mensaje.add(men);

        JButton env = new JButton("Enviar");
        enviarMensaje a = new enviarMensaje();
        env.addActionListener(a);
        mensaje.add(env);
        mensaje.setVisible(false);

        JLabel labelPuerto = new JLabel("Puerto: ");
        labelPuerto.setBounds(0,-10, 100,50);
        labelPuerto.setText("Puerto: " + Puerto);
        add(labelPuerto);

        JButton buttonEnviar = new JButton("+");
        buttonEnviar.setBounds(5,415,50,20);
        event e = new event();
        buttonEnviar.addActionListener(e);
        add(buttonEnviar);

        setSize(600,500);
        setLayout(null);
        setVisible(true);
    }

    public class event implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            mensaje.setVisible(true);
        }
    }

    public class enviarMensaje implements ActionListener{
        public void actionPerformed(ActionEvent a) {
            mensaje.setVisible(false);
        }
    }



    public static int Puerto;

    /**
     * @param args
     * @throws IOException errores por input o output
     */
    public static void main(String[] args) throws IOException {

        ServerSocket ss;
        ss = new ServerSocket(5000);

        Puerto = ss.getLocalPort();


        new Servidor();

        System.out.println("El puerto del servidor es: " + Puerto);
        System.out.println("Esperando Conexión....");

        while(true){

            Socket s = null;
            try {
                s = ss.accept();
                System.out.println("Se inició chat con " + s);


                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                Thread t = new ClientHandler(s, dis, dos);
                t.start();

            } catch (IOException e) {
                assert s != null;
                s.close();
                e.printStackTrace();
            }
        }
    }
}
