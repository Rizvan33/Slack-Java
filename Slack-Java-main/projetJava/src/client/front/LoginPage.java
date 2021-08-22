package client.front;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends Page {

    private static JTextField userNameBox = new JTextField(20);
    private static JPasswordField passwordBox = new JPasswordField(20);
    private static  JButton enter = new JButton("ENTER");
    private static JLabel enterUsername = new JLabel ("Email");
    private static JLabel enterPassword = new JLabel ("Password");
    private static  JButton back = new JButton("Back");

    private static LoginPage page = new LoginPage();

    public LoginPage(){
        this.add(enterUsername);
        this.add(userNameBox);
        this.add(enterPassword);
        this.add(passwordBox);
        this.add(enter);
        this.add(back);

        this.enter.addActionListener(Window.getWindow());
        this.back.addActionListener(Window.getWindow());
    }

    public static LoginPage getPage() {
        return page;
    }

    public static JButton getEnter() {
        return enter;
    }

    public static JButton getBack() {
        return back;
    }

    public static JTextField getUserNameBox() {
        return userNameBox;
    }

    public static JTextField getPasswordBox() {
        return passwordBox;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Font fontLabel = new Font("Arial",0,15);
        Font font = new Font("Arial",0,25);

        enter.setBackground(new java.awt.Color(0,0,255));
        enter.setForeground(new java.awt.Color(255,255,255));
        enter.setBounds(340,210,100,30);

        back.setBackground(new java.awt.Color(151, 52, 23));
        back.setForeground(new java.awt.Color(255,255,255));
        back.setBounds(60,210,100,30);

        enterUsername.setText("Email");
        enterUsername.setBounds(60,80,100,20);
        //enterUsername.setFont(fontLabel);

        userNameBox.setForeground(new java.awt.Color(0,0,255));
        userNameBox.requestFocus();
        userNameBox.setBounds(180,80,260,30);

        enterPassword.setText("Password");
        enterPassword.setBounds(60,140,100,20);
        //enterPassword.setFont(fontLabel);

        passwordBox.setForeground(new java.awt.Color(0,0,255));
        passwordBox.setBounds(180,140,260,30);

    }

}
