package electricity.billing.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.*;

public class PayBill extends JFrame implements ActionListener {
    Choice cmonth;
    JButton pay, back;
    String meter;

    PayBill(String meter) {
        this.meter = meter;
        setLayout(null);
        setBounds(300, 150, 900, 600);

        JLabel heading = new JLabel("Electricity Bill");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setBounds(120, 5, 400, 30);
        add(heading);

        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(35, 80, 200, 20);
        add(lblmeternumber);

        JLabel meternumber = new JLabel("");
        meternumber.setBounds(300, 80, 200, 20);
        add(meternumber);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(35, 140, 200, 20);
        add(lblname);

        JLabel lebelname = new JLabel("");
        lebelname.setBounds(300, 140, 200, 20);
        add(lebelname);

        JLabel lblmonth = new JLabel("Month");
        lblmonth.setBounds(35, 200, 200, 20);
        add(lblmonth);

        cmonth = new Choice();
        cmonth.setBounds(300, 200, 200, 20);
        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        add(cmonth);

        JLabel lblunits = new JLabel("Units");
        lblunits.setBounds(35, 260, 200, 20);
        add(lblunits);

        JLabel lebelunits = new JLabel("");
        lebelunits.setBounds(300, 260, 200, 20);
        add(lebelunits);

        JLabel lbltotalbill = new JLabel("Total Bill");
        lbltotalbill.setBounds(35, 320, 200, 20);
        add(lbltotalbill);

        JLabel lebeltotalbill = new JLabel("");
        lebeltotalbill.setBounds(300, 320, 200, 20);
        add(lebeltotalbill);

        JLabel lblstatus = new JLabel("Status");
        lblstatus.setBounds(35, 380, 200, 20);
        add(lblstatus);

        JLabel lebelstatus = new JLabel("");
        lebelstatus.setBounds(300, 380, 200, 20);
        lebelstatus.setForeground(Color.RED);
        add(lebelstatus);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '" + meter + "'");

            while (rs.next()) {
                meternumber.setText(meter);
                lebelname.setText(rs.getString("name"));
            }

            rs = c.s.executeQuery("select * from bill where meter_no = '" + meter + "' AND month = 'January'");

            while (rs.next()) {
                lebelunits.setText(rs.getString("units"));
                lebeltotalbill.setText(rs.getString("totalbill"));
                lebelstatus.setText(rs.getString("status"));

                if (lebelstatus.getText().equalsIgnoreCase("Paid")) {

                    lebelstatus.setForeground(Color.BLUE);

                } else {

                    lebelstatus.setForeground(Color.RED);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cmonth.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ae) {
                try {
                    Conn c = new Conn();
                    ResultSet rs = c.s
                            .executeQuery("select * from bill where meter_no = '" + meter + "'AND month = '"
                                    + cmonth.getSelectedItem() + "'");

                    if (rs.next()) {
                        lebelunits.setText(rs.getString("units"));
                        lebeltotalbill.setText(rs.getString("totalbill"));
                        lebelstatus.setText(rs.getString("status"));

                        if (lebelstatus.getText().equalsIgnoreCase("Paid")) {

                            lebelstatus.setForeground(Color.BLUE);

                        } else {

                            lebelstatus.setForeground(Color.RED);
                        }
                    } else {
                        lebelunits.setText("0");
                        lebeltotalbill.setText("0");
                        lebelstatus.setText("No Bill Found");
                        lebelstatus.setForeground(Color.RED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        pay = new JButton("Pay");
        pay.setBounds(100, 460, 100, 25);
        pay.setBackground(Color.BLACK);
        pay.setForeground(Color.WHITE);
        pay.addActionListener(this);
        add(pay);

        back = new JButton("Back");
        back.setBounds(230, 460, 100, 25);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        getContentPane().setBackground(Color.WHITE);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bill.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 120, 600, 300);
        add(image);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == pay) {
            try {
                Conn c = new Conn();
                Random random = new Random();

                String transactionId = "TXN" + (100000 + random.nextInt(900000));

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

                String paymentDate = sdf.format(new Date());

                c.s.executeUpdate(
                        "update bill set " +
                                "status = 'Paid', " +
                                "payment_date = '" + paymentDate + "', " +
                                "transaction_id = '" + transactionId + "' " +
                                "where meter_no = '" + meter + "' " +
                                "AND month = '" + cmonth.getSelectedItem() + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
            setVisible(false);
            new Paytm(meter);
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new PayBill("");
    }
}
