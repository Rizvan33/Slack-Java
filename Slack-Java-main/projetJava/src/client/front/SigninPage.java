package client.front;

import javax.swing.*;
import java.awt.*;

public class SigninPage extends Page {

    public static JTextField userNameBox = new JTextField(20);
    public static JTextField emailBox = new JTextField(20);
    public static JPasswordField passwordBox = new JPasswordField(20);
    public static JPasswordField passwordBox2 = new JPasswordField(20);
    private static  JButton enter = new JButton("Register");
    private static JLabel enterUsername = new JLabel ("Enter UserName");
    private static JLabel enterEmail = new JLabel ("Enter Email");
    private static JLabel enterPassword = new JLabel ("Enter Password");
    private static JLabel enterPassword2 = new JLabel ("Confirm Password");
    //Icon icon = new ImageIcon("");
    private static  JButton back = new JButton("Back");


    private static SigninPage page = new SigninPage();

    public SigninPage() {

        this.add(enterUsername);
        this.add(userNameBox);
        this.add(enterEmail);
        this.add(emailBox);
        this.add(enterPassword);
        this.add(passwordBox);
        this.add(enterPassword2);
        this.add(passwordBox2);
        this.add(enter);
        this.add(back);

        this.enter.addActionListener(Window.getWindow());
        this.back.addActionListener(Window.getWindow());
    }

    public static SigninPage getPage() { return page; }

    public static JButton getEnter() {
        return enter;
    }

    public static JButton getBack() {
        return back;
    }

    public static JTextField getUserNameBox() {
        return userNameBox;
    }

    public static JTextField getEmailBox() {
        return emailBox;
    }

    public static JTextField getPasswordBox() { return passwordBox; }

    public static JTextField getPasswordBox2() { return passwordBox2; }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Font fontLabel = new Font("Arial",2,15);
        Font fontButton = new Font("Arial",1,15);
        /*int tc=100;

        for(int x=0; x<40; x++){
            for(int y=0; y<21; y++){
                g.drawImage(fond, x*tc, y*tc, tc, tc, this);
            }
        }*/
        /*JLabel label= new JLabel("Sign In");
        label.setBounds(230,10,120,30);
        add(label);*/

        enter.setBackground(new java.awt.Color(0,0,255));
        enter.setForeground(new java.awt.Color(255,255,255));
        enter.setBounds(340,210,100,30);

        back.setBackground(new java.awt.Color(151, 52, 23));
        back.setForeground(new java.awt.Color(255,255,255));
        back.setBounds(60,210,100,30);

        enterUsername.setText("Username");
        enterUsername.setBounds(60,50,100,20);

        userNameBox.setForeground(new java.awt.Color(0,0,255));
        userNameBox.requestFocus();
        userNameBox.setBounds(240,50,200,20);
        userNameBox.requestFocus();

        enterEmail.setText("Email");
        enterEmail.setBounds(60,90,100,20);

        emailBox.setForeground(new java.awt.Color(0,0,255));
        emailBox.setBounds(240,90,200,20);

        enterPassword.setText("Password");
        enterPassword.setBounds(60,130,100,20);

        passwordBox.setForeground(new java.awt.Color(0,0,255));
        passwordBox.setBounds(240,130,200,20);

        enterPassword2.setText("Confirm Password");
        enterPassword2.setBounds(60,170,180,20);

        passwordBox2.setForeground(new java.awt.Color(0,0,255));
        passwordBox2.setBounds(240,170,200,20);
    }
}

