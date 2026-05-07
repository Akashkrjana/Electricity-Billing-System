package electricity.billing.system;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame implements Runnable {

    Thread L;
    Splash() {

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/elect.jpg"));
        Image i2 = i1.getImage().getScaledInstance(740, 550, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image);
        setVisible(true);

        int x = 1;
        for (int i = 2; i < 600; i+=4, x+=1) {
            setSize(i + x, i);
            setLocation(740 - ((i + x) / 2), 420 - (i / 2));

            try{

                Thread.sleep(3);
            }catch(Exception e){

                e.printStackTrace();
            }
        }

        L = new Thread(this);
        L.start();

        setVisible(true);
    }

    public void run(){

        try{
            Thread.sleep(7000);
            setVisible(false);


            // login frame

            new Login();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   

    public static void main(String[] args) {
        new Splash();
    }
}