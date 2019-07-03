package config;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Tools {
    static BASE64Encoder encoder = new BASE64Encoder();
    static BASE64Decoder decoder = new BASE64Decoder();
    static public String getImageBinary(String filePath) {
        File f = new File(filePath);
        try {
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public Image base64StringToImage(String base64String) {
        Image demo = null;
        try {
            byte[] bytes = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            demo = ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return demo;
    }


    public static void main(String[] args) {
        System.out.println(getImageBinary("avatar/2.jpg")); // image to base64

        Image a = base64StringToImage(getImageBinary("avatar/2.jpg")); // base64 to image
        JFrame demo = new JFrame("test");
        demo.setSize(200, 200);
        JButton db = new JButton();
        db.setSize(200, 200);
        db.setIcon(new ImageIcon(a));
        demo.getContentPane().add(db);
        demo.setVisible(true);
    }
}
