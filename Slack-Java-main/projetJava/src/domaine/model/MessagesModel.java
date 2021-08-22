package domaine.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MessagesModel implements Serializable {

    private UserModel sender;

    private String ip;
    private String content;
    private Timestamp date;
    public MessagesModel() {
    }

    public MessagesModel(UserModel userModel, String ip, String content, Timestamp date){
        sender = userModel;
        this.ip = ip;
        this.content = content;
        this.date = date;
    }

    public UserModel getSender() {
        return sender;
    }

    public String getIp() {
        return ip;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getDate() {
        return date;
    }

    public String toString() {
        String newDate = new SimpleDateFormat("MM/dd HH:mm").format(date);
        return sender.getPseudo() + " : " + content + "\n" + newDate+ "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessagesModel that = (MessagesModel) o;
        long datex = date.getTime() /1000;
        return Objects.equals(sender, that.sender) && Objects.equals(ip, that.ip) && Objects.equals(content, that.content) && Objects.equals(datex, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, ip, content, date);
    }
}
