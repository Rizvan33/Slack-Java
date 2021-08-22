package client.front;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte[] b = md.digest();

            StringBuffer sb = new StringBuffer();
            for (byte b1 : b) {
                sb.append(Integer.toHexString(b1 & 0xff).toString());
            }

            System.out.println("En format hexa : " + sb.toString());
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

}