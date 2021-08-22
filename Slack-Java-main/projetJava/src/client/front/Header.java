package client.front;
import client.front.AuthPage;
import controleur.ClientController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Header extends JPanel implements ActionListener {

    //private Image img_logo =Imagery.getImage("logo.png");
    //private JButton logo = new JButton(new ImageIcon(img_logo.getScaledInstance(40,40, Image.SCALE_SMOOTH)));
    private JButton logo = new JButton("logo");
    private JLabel loggedInAsBox = new JLabel("user");
    private JButton disconnect = new JButton("Disconnect");
    private static ClientController clientController = ClientController.getClientController();
    private static JToolBar toolBar = new JToolBar();
    private static Header header= new Header();



    public Header(){
        this.toolBar.setFloatable(false);
        this.toolBar.setPreferredSize (new Dimension(500, 50) ) ;

        this.add(toolBar);
        this.toolBar.addSeparator(new Dimension(10,5));
        this.toolBar.add( logo );
        this.toolBar.addSeparator(new Dimension(270,5));

        this.toolBar.add( loggedInAsBox );
        this.toolBar.addSeparator(new Dimension(10,5));

        this.toolBar.add( disconnect );

        this.logo.addActionListener(this);
        this.disconnect.addActionListener(this);
    }

    public static Header getHeader() { return header; }

    public static JToolBar getToolBar() { return toolBar; }

    public JButton getLogo() {
        return this.logo;
    }

    public JButton getDisconnect() {
        return this.disconnect;
    }

    public JLabel getLoggedInAsBox(){
        return loggedInAsBox;
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        ////////tool bar
        if (source == Header.getHeader().getLogo()) {
            Window.getWindow().backToHome();
            clientController.disconnectFromChannel();
            ChatPage.getPage().getConvTa().setText("");
            ChatPage.getPage().getL1().removeAllElements();
        }

        if (source == Header.getHeader().getDisconnect()){
            clientController.disconnectUser();
            Window.setContent(AuthPage.getPage());
            Window.setActualPage("Authentication Page");
        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Font fontLabel = new Font("Arial",2,25);
        Font fontButton = new Font("Arial",1,15);
        Border border = BorderFactory.createLineBorder(Color.black);
        Border margin = new EmptyBorder(4,5,4,5);

        //logo.setBounds(10,10,30,30);

        disconnect.setBackground(new Color(151,52,23));
        disconnect.setForeground(new Color(255,255,255));
        //disconnect.setBounds(390,10,100,30);

        //loggedInAsBox.setBackground(new Color(227, 39, 174));
        loggedInAsBox.setBorder(new CompoundBorder(border, margin));

        //loggedInAsBox.setBounds(280,10,100,26);


    }
}
