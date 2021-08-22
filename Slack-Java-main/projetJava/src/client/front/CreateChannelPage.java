package client.front;

import javax.swing.*;
import java.awt.*;

public class CreateChannelPage extends Page{

    private static JTextField ipBox = new JTextField(20);
    private static JTextField titleBox = new JTextField(20);
    private static JCheckBox privacyCheckBox = new JCheckBox("Private Channel");
    private static JButton enter = new JButton("Create Channel");
    private static JLabel enterIP = new JLabel("Enter IP Address");
    private static JLabel enterTitle = new JLabel("Enter Title");

    private static CreateChannelPage page = new CreateChannelPage();

    public CreateChannelPage(){
        this.add(enterIP);
        this.add(ipBox);
        this.add(enterTitle);
        this.add(titleBox);
        this.add(privacyCheckBox);
        this.add(enter);

        enter.addActionListener(Window.getWindow());
    }

    public static CreateChannelPage getPage() {
        return page;
    }

    public static JButton getEnter() {
        return enter;
    }

    public static JTextField getIpBox() {
        return ipBox;
    }

    public static JTextField getTitleBox() {
        return titleBox;
    }

    public static JCheckBox getPrivacyCheckBox() {
        return privacyCheckBox;
    }

    public static int iSelected(JCheckBox cb){
        if (cb.isSelected()) return  1;
        else return 0;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Font fontLabel = new Font("Arial",2,15);
        Font fontButton = new Font("Arial",1,15);

        enter.setBackground(new java.awt.Color(0, 0, 255));
        enter.setForeground(new java.awt.Color(255, 255, 255));
        enter.setBounds(190, 240, 160, 30);

        enterIP.setText("IP Address");
        enterIP.setBounds(80, 100, 100, 20);
        //enterIP.setFont(fontLabel);

        ipBox.setForeground(new java.awt.Color(0, 0, 255));
        ipBox.requestFocus();
        ipBox.setBounds(170, 100, 260, 30);

        enterTitle.setText("Title");
        enterTitle.setBounds(80, 150, 100, 20);
        //enterTitle.setFont(fontLabel);

        titleBox.setForeground(new java.awt.Color(0, 0, 255));
        titleBox.requestFocus();
        titleBox.setBounds(170, 150, 260, 30);

        privacyCheckBox.setBounds(80,180, 160,50);
    }
}
