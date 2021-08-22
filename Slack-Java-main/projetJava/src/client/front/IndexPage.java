package client.front;
import client.front.Page;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IndexPage extends Page {


    private static JLabel chat = new JLabel ("Chats");
    //private Box box = new Box(BoxLayout.Y_AXIS);
    private ArrayList<JButton> buttonList = new ArrayList<JButton>();
    private DefaultListModel<String> l1 = new DefaultListModel<>();
    private JList<String> chatList = new JList<>(l1);
    private JButton connect = new JButton("Connect");

    private static JScrollPane chatSp = new JScrollPane();
    private static JButton newChannel = new JButton("New Channel");
    private static JButton joinChannel = new JButton("Join Channel");

    private static IndexPage page = new IndexPage();

    public IndexPage(){

        this.add(chatList);
        this.add(chatSp);
        this.add(chat);
        this.add(newChannel);
        this.add(joinChannel);
        this.add(connect);

        this.connect.addActionListener(Window.getWindow());
        this.newChannel.addActionListener(Window.getWindow());
        this.joinChannel.addActionListener(Window.getWindow());
    }

    public static IndexPage getPage() {
        return page;
    }

    public JList getChatList(){ return chatList; }

    public void addChatToList(String title){
        this.l1.addElement(title);
//        DefaultListCellRenderer renderer =  (DefaultListCellRenderer)chatList.getCellRenderer();
//        renderer.setHorizontalAlignment(JLabel.CENTER);
//        chatList.setFixedCellHeight(20);
    }

    public static JButton getNewChannel() { return newChannel; }

    public static JButton getJoinChannel() { return joinChannel; }

    public JButton getConnect(){ return connect; }

    public ArrayList<JButton> getButtonList() {
        return buttonList;
    }

    /*public void createChannelButton(String channelTitle){
        JButton channelButton = new JButton(channelTitle);
        channelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.getBox().add(channelButton);
        //channelButton.setBackground(new Color(0,0,255));
        //channelButton.setForeground(new Color(255,255,255));
        channelButton.setBounds(200,90,100,30);
        //channelButton.addActionListener(Window.getWindow());
        buttonList.add(channelButton);
    }*/


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font fontLabel = new Font("Arial",2,15);
        Font fontButton = new Font("Arial",1,15);

        chat.setBounds(220,60,60,20);
        chat.setFont(fontLabel);

        connect.setBackground(new Color(0,0,255));
        connect.setForeground(new Color(255,255,255));
        connect.setBounds(200,240,100,30);

        newChannel.setBackground(new Color(0,0,255));
        newChannel.setForeground(new Color(255,255,255));
        newChannel.setBounds(340,240,140,30);

        joinChannel.setBackground(new Color(0,0,255));
        joinChannel.setForeground(new Color(255,255,255));
        joinChannel.setBounds(20,240,140,30);

        chatList.setForeground(new Color(0, 0, 255));
        chatList.setBounds(0,85,600,135);

        chatSp.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatSp.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatSp.setViewportView(chatList);
        chatSp.setBounds(0,85,500,135);
    }

}
