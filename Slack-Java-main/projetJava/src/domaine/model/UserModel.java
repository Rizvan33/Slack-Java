package domaine.model;

import java.io.Serializable;
import java.util.Objects;

public class UserModel implements Serializable {
    private String email;
    private String pseudo;

    public UserModel(){
    }

    public UserModel(String email, String pseudo) {
        this.email = email;
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(email, userModel.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
