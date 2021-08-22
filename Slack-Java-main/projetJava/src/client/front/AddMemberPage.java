package client.front;

import javax.swing.*;
import java.awt.*;

public class AddMemberPage extends Page{

    public static JTextField emailBox = new JTextField(20);
    private static JButton enter = new JButton("Add This Member");
    private static JLabel enterEmail = new JLabel("Enter IP Address");

    private static AddMemberPage page = new AddMemberPage();

    public AddMemberPage() {

        this.add(emailBox);
        this.add(enterEmail);
        this.add(enter);

        this.enter.addActionListener(Window.getWindow());
    }

    public static AddMemberPage getPage() {
        return page;
    }

    public static JButton getEnter() {
        return enter;
    }

    public static JTextField getEmailBox() {
        return emailBox;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font fontLabel = new Font("Arial",2,15);
        Font fontButton = new Font("Arial",1,15);

        enter.setBackground(new java.awt.Color(0, 0, 255));
        enter.setForeground(new java.awt.Color(255, 255, 255));
        enter.setBounds(150, 210, 200, 30);

        enterEmail.setText("Enter Email");
        enterEmail.setBounds(50, 120, 150, 30);

        emailBox.setForeground(new java.awt.Color(0, 0, 255));
        emailBox.requestFocus();
        emailBox.setBounds(190, 120, 260, 30);

    }

}
