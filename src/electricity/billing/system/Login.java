package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton login, cancel, signup;
    TextField username, password;
    Choice logginin;

    Login() {
        super("Login Page");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(300, 20, 100, 20);
        add(lblusername);

        username = new TextField();
        username.setBounds(400, 20, 150, 20);
        add(username);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(300, 60, 100, 20);
        add(lblpassword);

        password = new TextField();
        password.setBounds(400, 60, 150, 20);
        add(password);

        JLabel loggininas = new JLabel("Loggin in as");
        loggininas.setBounds(300, 100, 100, 20);
        add(loggininas);

        logginin = new Choice();
        logginin.add("Admin");
        logginin.add("Customer");
        logginin.setBounds(400, 100, 150, 20);
        add(logginin);

        ImageIcon l1 = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image l2 = l1.getImage().getScaledInstance(16, 13, Image.SCALE_DEFAULT);
        login = new JButton("Login", new ImageIcon(l2));
        login.addActionListener(this);
        login.setBounds(330, 140, 100, 20);
        add(login);

        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("icon/cancel.jpg"));
        Image c2 = c1.getImage().getScaledInstance(16, 13, Image.SCALE_DEFAULT);
        cancel = new JButton("Cencel", new ImageIcon(c2));
        cancel.setBounds(450, 140, 100, 20);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon s1 = new ImageIcon(ClassLoader.getSystemResource("icon/signup.png"));
        Image s2 = s1.getImage().getScaledInstance(16, 13, Image.SCALE_DEFAULT);
        signup = new JButton("Signup", new ImageIcon(s2));
        signup.setBounds(390, 180, 100, 20);
        signup.addActionListener(this);
        add(signup);

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icon/second.jpg"));
        Image i4 = i3.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i5 = new ImageIcon(i4);
        JLabel image = new JLabel(i5);
        image.setBounds(0, 0, 250, 250);
        add(image);

        setSize(640, 300);
        setLocation(400, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == login) {
            String susername = username.getText();
            String spassword = password.getText();
            String user = logginin.getSelectedItem();

            try {
                Conn c = new Conn();
                String query = "select * from login where username = '" + susername + "' and password = '" + spassword
                        + "' and user = '" + user + "'";
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    String meter = rs.getString("meter_no");

                    setVisible(false);
                    new Project(user, meter);

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login");
                    username.setText("");
                    password.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == cancel) {
            setVisible(false);
        } else if (ae.getSource() == signup) {
            setVisible(false);

            new Signup();
        }
    }

    public static void main(String[] args) {
        new Login();

    }
}
