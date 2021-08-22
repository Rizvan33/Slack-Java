package domaine.model;

import java.io.Serializable;

public class ChannelModel implements Serializable {
    private String ip;
    private String title;
    private String idAdmin;
    private int isPrivate;

    public ChannelModel(String ip, String title, String idAdmin, int isPrivate) {
        this.ip = ip;
        this.title = title;
        this.idAdmin = idAdmin;
        this.isPrivate = isPrivate;
    }

    public ChannelModel() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public boolean isPrivate() {
        return isPrivate == 1;
    }

    public void setPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String toString(){
        return title + " : " + ip;
    }
}
