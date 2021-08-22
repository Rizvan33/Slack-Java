package domaine.model;

public class JoinsModel {
    private String email;
    private String ip;

    public JoinsModel(String ip, String email) {
        this.email = email;
        this.ip = ip;
    }

    public String getEmail() {
        return email;
    }


    public String getIp() {
        return ip;
    }


}
