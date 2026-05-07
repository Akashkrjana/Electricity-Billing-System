package electricity.billing.system;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class TransactionDetails extends JFrame implements ActionListener {

        String meter, month;

        JButton print, back;

        JTextPane area;

        TransactionDetails(String meter, String month) {

                this.meter = meter;
                this.month = month;

                setSize(550, 700);
                setLocation(550, 30);

                setLayout(new BorderLayout());

                JPanel panel = new JPanel();

                JLabel heading = new JLabel("Transaction Details");

                heading.setFont(new Font("Tahoma", Font.BOLD, 22));

                panel.add(heading);

                add(panel, "North");

                area = new JTextPane();
                area.setEditable(false);

                area.setFont(new Font("Senserif", Font.PLAIN, 18));

                JScrollPane pane = new JScrollPane(area);

                add(pane, "Center");

                print = new JButton("Print");

                print.addActionListener(this);

                back = new JButton("Back");

                back.addActionListener(this);

                JPanel buttonPanel = new JPanel();

                buttonPanel.add(print);

                buttonPanel.add(back);

                add(buttonPanel, "South");

                generateTransaction();

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

        public void generateTransaction() {

                try {

                        Conn c = new Conn();

                        area.setText("");

                        ResultSet rs = c.s.executeQuery(
                                        "select * from customer where meter_no = '" + meter + "'");

                        String name = "";

                        if (rs.next()) {

                                name = rs.getString("name");
                        }

                        rs = c.s.executeQuery(
                                        "select * from bill where meter_no = '" + meter +
                                                        "' and month = '" + month + "'");

                        if (rs.next()) {

                                String total = rs.getString("totalbill");

                                String transactionId = rs.getString("transaction_id");

                                String paymentDate = rs.getString("payment_date");

                                Random random = new Random();

                                String[] gateways = {
                                                "BILLDESK",
                                                "RAZORPAY",
                                                "PAYTM",
                                                "PHONEPE",
                                                "GOOGLE PAY"
                                };

                                String[] paymentModes = {
                                                "UPI",
                                                "Credit Card",
                                                "Debit Card",
                                                "Net Banking",
                                                "Wallet"
                                };

                                String gateway = gateways[random.nextInt(gateways.length)];

                                String paymentMode = paymentModes[random.nextInt(paymentModes.length)];

                                String invoiceNo = "INV" + (100000 + random.nextInt(900000));

                                appendColoredText(
                                                "\t\tReliance Power Limited\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\t\t  PAYMENT RECEIPT\n\n",
                                                Color.BLUE);

                                appendColoredText(
                                                "\tTransaction ID          : " + transactionId + "\n\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\tDate                            : " + paymentDate + "\n\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\tPayment Gateway   : " + gateway + "\n\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\tBilling Office         : null\n\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\tConsumer Id          : " + meter + "\n\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\tName                      : " + name + "\n\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\tInvoice Number    : " + invoiceNo + "\n\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\tBill Paid For       : " + month.toUpperCase() + " 2026\n\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\tPayment Mode        : " + paymentMode + "\n\n",
                                                Color.BLACK);

                                appendColoredText(
                                                "\tReceived               : Rs. " + total + "\n\n",
                                                Color.BLUE);
                        }

                } catch (Exception e) {

                        e.printStackTrace();
                }
        }

        public void actionPerformed(ActionEvent ae) {

                if (ae.getSource() == print) {

                        try {

                                boolean complete = area.print();

                                if (complete) {

                                        JOptionPane.showMessageDialog(
                                                        null,
                                                        "Receipt Printed Successfully");

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

                } else if (ae.getSource() == back) {

                        setVisible(false);
                }
        }

        public static void main(String[] args) {

                new TransactionDetails("", "");
        }
}