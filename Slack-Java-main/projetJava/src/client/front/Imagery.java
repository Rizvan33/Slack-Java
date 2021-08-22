package client.front;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Imagery {

    private static Image image=null;

    public static Image getImage(String nom){
        try {
            image = ImageIO.read(new File("./Images/"+nom));
        }
        catch(IOException exc){
            exc.printStackTrace();
        }

        return image;
    }
}

