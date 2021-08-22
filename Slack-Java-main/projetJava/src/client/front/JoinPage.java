package client.front;

import javax.swing.*;
import java.awt.*;

public class JoinPage extends Page{

    public static JTextField ipBox = new JTextField(20);
    private static JButton enter = new JButton("ENTER");
    private static JLabel enterIP = new JLabel("Enter IP Address");

    private static JoinPage page = new JoinPage();

    public JoinPage() {

        this.add(enterIP);
        this.add(ipBox);
        this.add(enter);

        this.enter.addActionListener(Window.getWindow());
    }

    public static JoinPage getPage() {
        return page;
    }

    public static JButton getEnter() {
        return enter;
    }

    public static JTextField getIpBox() {
        return ipBox;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font fontLabel = new Font("Arial",2,15);
        Font fontButton = new Font("Arial",1,15);

        enter.setBackground(new java.awt.Color(0, 0, 255));
        enter.setForeground(new java.awt.Color(255, 255, 255));
        enter.setBounds(200, 210, 100, 30);

        enterIP.setText("Enter IP Address");
        enterIP.setBounds(50, 110, 150, 30);

        ipBox.setForeground(new java.awt.Color(0, 0, 255));
        ipBox.requestFocus();
        ipBox.setBounds(190, 110, 260, 30);

    }

}


