package electricity.billing.system;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GenerateBill extends JFrame implements ActionListener {

    String meter;
    JButton bill, print;
    Choice cmonth;
    JTextPane area;

    GenerateBill(String meter) {
        this.meter = meter;

        setSize(500, 750);
        setLocation(550, 30);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        JLabel heading = new JLabel("Generate Bill");
        JLabel meternumber = new JLabel(meter);

        cmonth = new Choice();

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

        area = new JTextPane();
        area.setEditable(false);
        area.setFont(new Font("Senserif", Font.ITALIC, 18));

        area.setText(
                "\n\n\t             --------Click on the--------\n\t            Generate Bill Button to get\n\t          the bill of the Selected Month");

        JScrollPane pane = new JScrollPane(area);

        bill = new JButton("Generate Bill");
        bill.addActionListener(this);

        print = new JButton("Print");
        print.setVisible(false);
        print.addActionListener(this);

        panel.add(heading);
        panel.add(meternumber);
        panel.add(cmonth);

        add(panel, "North");
        add(pane, "Center");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bill);
        buttonPanel.add(print);

        add(buttonPanel, "South");

        setVisible(true);
    }

    public void appendColoredText(String text, Color color) {
        StyledDocument doc = area.getStyledDocument();

        Style style = area.addStyle("ColorStyle", null);
        StyleConstants.setForeground(style, color);

        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == bill) {
            print.setVisible(true);

            try {
                Conn c = new Conn();

                String month = cmonth.getSelectedItem();

                area.setText("");

                appendColoredText(
                        "\t\tReliance Power Limited\n    ELECTRICITY BILL GENERATED FOR THE MONTH\n\t\t  OF "
                                + month + ", 2026\n\n\n",
                        Color.BLACK);

                ResultSet rs = c.s.executeQuery(
                        "select * from customer where meter_no = '" + meter + "'");

                if (rs.next()) {

                    appendColoredText("\n    Customer Name : " + rs.getString("name"), Color.BLACK);
                    appendColoredText("\n    Meter Number  : " + rs.getString("meter_no"), Color.BLACK);
                    appendColoredText("\n    Address       : " + rs.getString("address"), Color.BLACK);
                    appendColoredText("\n    City          : " + rs.getString("city"), Color.BLACK);
                    appendColoredText("\n    State         : " + rs.getString("state"), Color.BLACK);
                    appendColoredText("\n    Email         : " + rs.getString("email"), Color.BLACK);
                    appendColoredText("\n    Phone         : " + rs.getString("phone"), Color.BLACK);

                    appendColoredText(
                            "\n\t-------------------------------------\n",
                            Color.BLACK);
                }

                rs = c.s.executeQuery(
                        "select * from meter_info where meter_no = '" + meter + "'");

                if (rs.next()) {

                    appendColoredText("\n    Meter Location : " + rs.getString("meter_location"), Color.BLACK);
                    appendColoredText("\n    Meter Type     : " + rs.getString("meter_type"), Color.BLACK);
                    appendColoredText("\n    Phase Code     : " + rs.getString("phase_code"), Color.BLACK);
                    appendColoredText("\n    Bill Type      : " + rs.getString("bill_type"), Color.BLACK);
                    appendColoredText("\n    Days           : " + rs.getString("days"), Color.BLACK);

                    appendColoredText(
                            "\n\t-------------------------------------\n",
                            Color.BLACK);
                }

                rs = c.s.executeQuery("select * from tax");

                if (rs.next()) {

                    appendColoredText("\n    Cost Per Unit         : " + rs.getString("cost_per_unit"), Color.BLACK);
                    appendColoredText("\n    Meter Rent            : " + rs.getString("meter_rent"), Color.BLACK);
                    appendColoredText("\n    Service Charge        : " + rs.getString("service_charge"), Color.BLACK);
                    appendColoredText("\n    Service Tax           : " + rs.getString("service_tax"), Color.BLACK);
                    appendColoredText("\n    Swacch Bharat Cess    : " + rs.getString("swacch_bharat_cess"),
                            Color.BLACK);
                    appendColoredText("\n    Fixed Tax             : " + rs.getString("fixed_tax"), Color.BLACK);

                    appendColoredText(
                            "\n\t-------------------------------------\n",
                            Color.BLACK);
                }

                rs = c.s.executeQuery(
                        "select * from bill where meter_no = '" + meter + "' and month='" + month + "'");

                if (rs.next()) {

                    appendColoredText("\n    Current Month  : " + rs.getString("month"), Color.BLACK);
                    appendColoredText("\n    Units Consumed : " + rs.getString("units"), Color.BLACK);
                    appendColoredText("\n    Total Charges  : " + rs.getString("totalbill"), Color.BLACK);

                    appendColoredText(
                            "\n    ---------------------------------------------------------",
                            Color.BLACK);

                    appendColoredText("\n    Total Payable  : " + rs.getString("totalbill"), Color.BLACK);

                    appendColoredText(
                            "\n\t\n",
                            Color.BLACK);
                    String status = rs.getString("status");
                    

                    if (status.equalsIgnoreCase("Paid")) {

                        appendColoredText("\n    Payment Status : ALREADY PAID ✔️", Color.BLUE);

                    } else {

                        appendColoredText("\n    Payment Status : NOT PAID YET ❌", Color.RED);
                    }

                    appendColoredText("\n", Color.BLACK);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == print) {

            try {

                boolean complete = area.print();

                if (complete) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Bill Printed Successfully");

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Printing Cancelled");

                }

            } catch (Exception e) {

                e.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        "Printing Failed");

            }
        }
    }

    public static void main(String[] args) {
        new GenerateBill("");
    }
}