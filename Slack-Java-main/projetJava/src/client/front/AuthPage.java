package client.front;

import javax.swing.*;
import java.awt.*;

public class AuthPage extends Page {
    private static JButton loginButton = new JButton("Log In");
    private static JButton signinButton = new JButton("Sign In");

    private static JLabel welcome = new JLabel ("Welcome to Slackish!");
    //private final Image icon = new ImageIcon(this.getClass().getResource("./Images/logo.png")).getImage();
    //private JLabel label = new JLabel((Icon) icon);


    private static AuthPage page=new AuthPage();

    public AuthPage(){

        this.add(loginButton);
        this.add(signinButton);
        this.add(welcome);
        //this.add(label);

        this.loginButton.addActionListener(Window.getWindow());
        this.signinButton.addActionListener(Window.getWindow());

    }

    public static AuthPage getPage() {
        return page;
    }

    public static JButton getLoginButton() {
        return loginButton;
    }

    public static JButton getSigninButton() {
        return signinButton;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Font fontLabel = new Font("Arial",2,25);
        Font fontButton = new Font("Arial",1,15);
        /*int tc=100;

        for(int x=0; x<40; x++){
            for(int y=0; y<21; y++){
                g.drawImage(fond, x*tc, y*tc, tc, tc, this);
            }
        }*/

        loginButton.setBackground(new java.awt.Color(0,0,255));
        loginButton.setForeground(new java.awt.Color(255,255,255));
        loginButton.setBounds(100,130,120,40);
        loginButton.setFont(fontButton);

        signinButton.setBackground(new java.awt.Color(0,0,255));
        signinButton.setForeground(new java.awt.Color(255,255,255));
        signinButton.setBounds(280,130,120,40);
        signinButton.setFont(fontButton);

        welcome.setBounds(110,70,500,20);
        welcome.setFont(fontLabel);
    }
}
