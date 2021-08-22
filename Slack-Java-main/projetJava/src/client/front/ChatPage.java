package client.front;

import javax.swing.*;
import java.awt.*;

public class ChatPage extends Page {

    private static JButton send = new JButton();
    private static JLabel messageL = new JLabel("Message : ");
    private static JTextField messageTf = new JTextField(20);
    private static JLabel convL = new JLabel();
    private static JScrollPane convSp = new JScrollPane();
    private static JTextArea convTa = new JTextArea();
    private static JLabel onlineL = new JLabel();
    private DefaultListModel<String> l1 = new DefaultListModel<>();
    private JList onlineJl = new JList(l1);
    private static JScrollPane onlineSp = new JScrollPane();
    private JButton addUser = new JButton();



    private static ChatPage page=new ChatPage();

    public ChatPage(){
        this.convTa.setEditable(false);
        this.add(send);
        this.add(addUser);
        this.add(messageL);
        this.add(messageTf);
        this.add(convL);
        this.add(convTa);
        this.add(convSp);
        this.add(onlineL);
        this.add(onlineJl);
        this.add(onlineSp);

        this.send.addActionListener(Window.getWindow());
        this.addUser.addActionListener(Window.getWindow());
    }

    public static ChatPage getPage() {
        return page;
    }

    public static JButton getSend() {
        return send;
    }

    public JButton getAddUser() {
        return addUser;
    }

    public static JTextField getMessageTf(){
        return messageTf;
    }

    public static JTextArea getConvTa(){
        return convTa;
    }

    public void addUsersToList(String pseudo){
        this.l1.addElement(pseudo);
    }

    public static JLabel getConvL() {
        return convL;
    }

    public DefaultListModel<String> getL1() {
        return l1;
    }

    public JList getUsersList(){ return onlineJl; }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Font fontLabel = new Font("Arial",0,15);
        Font font = new Font("Arial",0,25);

        send.setBackground(new Color(0,0,255));
        send.setForeground(new Color(255,255,255));
        send.setText("send");
        send.setBounds(400,240,81,30);

        addUser.setBackground(new Color(0,0,255));
        addUser.setForeground(new Color(255,255,255));
        addUser.setText("add");
        addUser.setBounds(400,200,81,30);

        messageL.setText("Message : ");
        messageL.setBounds(10,240,100,20);

        messageTf.setForeground(new Color(0,0,255));
        messageTf.requestFocus();
        messageTf.setBounds(100,240,290,30);

        convL.setHorizontalAlignment(SwingConstants.CENTER);
        //convL.setText("Conversation");
        convL.setBounds(100,60,140,16);

        convTa.setColumns(22);
        convTa.setFont(new Font("Arial", Font.PLAIN,12));
        convTa.setForeground(new Color(0,0,255));
        convTa.setLineWrap(true);
        //convTa.setBounds(20,90,317,100);
        convTa.setRows(3);
        convTa.setDragEnabled(false);

        convSp.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        convSp.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        convSp.setViewportView(convTa);
        convSp.setBounds(20,85,370,140);

        onlineL.setHorizontalAlignment(SwingConstants.CENTER);
        onlineL.setText("Members");
        onlineL.setToolTipText("");
        onlineL.setBounds(380,60,120,16);

        onlineJl.setForeground((new Color(0,0,255)));
        onlineJl.setBounds(400,70,81,16);

        onlineSp.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        onlineSp.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        onlineSp.setViewportView(onlineJl);
        onlineSp.setBounds(400,85,81,100);

    }

}
