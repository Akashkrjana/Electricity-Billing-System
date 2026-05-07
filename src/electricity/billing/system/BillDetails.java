package electricity.billing.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import net.proteanit.sql.DbUtils;

public class BillDetails extends JFrame {

    JTable table;
    String meter;

    BillDetails(String meter) {

        this.meter = meter;

        setBounds(400, 150, 900, 650);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        table = new JTable();

        try {

            Conn c = new Conn();

            String query = "select meter_no, month, units, totalbill, status from bill where meter_no = '" + meter
                    + "' ";

            ResultSet rs = c.s.executeQuery(query);

            table.setModel(DbUtils.resultSetToTableModel(rs));

            // ADD ACTION COLUMN
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            model.addColumn("Action");

            for (int i = 0; i < model.getRowCount(); i++) {

                String status = model.getValueAt(i, 4).toString();

                if (status.equalsIgnoreCase("Paid")) {

                    model.setValueAt("VIEW", i, 5);

                } else {

                    model.setValueAt("PAY", i, 5);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ACTION BUTTON RENDERER
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());

        // ACTION BUTTON EDITOR
        table.getColumn("Action").setCellEditor(
                new ButtonEditor(new JCheckBox(), table));

        table.setRowHeight(35);

        JScrollPane sp = new JScrollPane(table);

        sp.setBounds(0, 0, 880, 600);

        add(sp);

        setVisible(true);
    }

    public static void main(String[] args) {
        new BillDetails("");
    }
}

// BUTTON RENDERER

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {

        setOpaque(true);

        setForeground(Color.WHITE);
    }

    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        String label = (value == null) ? "" : value.toString();

        setText(label);

        if (label.equalsIgnoreCase("PAY")) {

            setForeground(Color.RED);

        } else {

            setForeground(Color.BLUE);
        }

        return this;
    }
}

// BUTTON EDITOR

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;

    private String label;

    private boolean clicked;

    JTable table;

    public ButtonEditor(JCheckBox checkBox, JTable table) {

        super(checkBox);

        this.table = table;

        button = new JButton();

        button.setOpaque(true);

        button.setForeground(Color.WHITE);

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(
            JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column) {

        label = (value == null) ? "" : value.toString();

        button.setText(label);

        if (label.equalsIgnoreCase("PAY")) {

            button.setBackground(Color.RED);

        } else {

            button.setBackground(Color.BLUE);
        }

        clicked = true;

        return button;
    }

    public Object getCellEditorValue() {

        if (clicked) {

            int row = table.getSelectedRow();

            String meterNo = table.getValueAt(row, 0).toString();
            String month = table.getValueAt(row, 1).toString();

            String action = table.getValueAt(row, 5).toString();

            if (action.equalsIgnoreCase("PAY")) {

                SwingUtilities.getWindowAncestor(button).setVisible(false);

                new PayBill(meterNo);

            } else {

                SwingUtilities.getWindowAncestor(button).setVisible(false);

                new TransactionDetails(meterNo, month);
            }
        }

        clicked = false;

        return label;
    }

    public boolean stopCellEditing() {

        clicked = false;

        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {

        super.fireEditingStopped();
    }
}