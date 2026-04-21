package frontend.dialogs;

import backend.controllers.KioskController;
import backend.models.OrderRecord;
import frontend.ui.AppTheme;
import frontend.ui.RoundedButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

/**
 * A professional dialog displaying the full order history read from orders.txt.
 * Features: JTable, search by token, refresh, newest-first sort, empty state handling.
 */
public class OrderHistoryDialog extends JDialog {

    private final KioskController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final TableRowSorter<DefaultTableModel> sorter;
    private final JLabel statusLabel;

    private static final String[] COLUMNS = {
        "Token", "Date & Time", "Items", "Qty", "Total (₹)", "Payment", "Status"
    };

    public OrderHistoryDialog(JFrame parent, KioskController controller) {
        super(parent, "Order History", true);
        this.controller = controller;

        setSize(780, 520);
        setLocationRelativeTo(parent);
        setResizable(true);
        setMinimumSize(new Dimension(600, 400));

        // Main container
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(AppTheme.MC_WHITE);

        // ── Header ───────────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setBackground(AppTheme.MC_RED);
        header.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("📋  Order History");
        title.setFont(AppTheme.getFontBold(20));
        title.setForeground(AppTheme.MC_WHITE);
        header.add(title, BorderLayout.WEST);

        // Search field
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        searchPanel.setOpaque(false);

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(AppTheme.getFontPlain(16));
        searchPanel.add(searchIcon);

        JTextField searchField = new JTextField(14);
        searchField.setFont(AppTheme.getFontPlain(13));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 1),
            new EmptyBorder(6, 10, 6, 10)
        ));
        searchField.setBackground(new Color(255, 255, 255, 30));
        searchField.setForeground(AppTheme.MC_WHITE);
        searchField.setCaretColor(AppTheme.MC_WHITE);
        searchField.putClientProperty("JTextField.placeholderText", "Search by Token...");

        // Live search filtering
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { applyFilter(searchField.getText()); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { applyFilter(searchField.getText()); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { applyFilter(searchField.getText()); }
        });

        searchPanel.add(searchField);
        header.add(searchPanel, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        // ── Table ────────────────────────────────────────────────────────────────
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Token, Qty, Total are numeric — enables proper sorting
                if (columnIndex == 0 || columnIndex == 3 || columnIndex == 4) return Integer.class;
                return String.class;
            }
        };

        table = new JTable(tableModel);
        table.setFont(AppTheme.getFontPlain(13));
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(AppTheme.MC_LIGHT_RED);
        table.setSelectionForeground(AppTheme.TEXT_DARK);
        table.setFillsViewportHeight(true);
        table.setBackground(AppTheme.MC_WHITE);

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new AlternatingRowRenderer());
        table.setDefaultRenderer(Integer.class, new AlternatingRowRenderer());

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(60);   // Token
        table.getColumnModel().getColumn(1).setPreferredWidth(140);  // Date
        table.getColumnModel().getColumn(2).setPreferredWidth(240);  // Items
        table.getColumnModel().getColumn(3).setPreferredWidth(40);   // Qty
        table.getColumnModel().getColumn(4).setPreferredWidth(70);   // Total
        table.getColumnModel().getColumn(5).setPreferredWidth(70);   // Payment
        table.getColumnModel().getColumn(6).setPreferredWidth(80);   // Status

        // Styled header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(AppTheme.getFontBold(12));
        tableHeader.setBackground(AppTheme.MC_GRAY);
        tableHeader.setForeground(AppTheme.TEXT_DARK);
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER));
        tableHeader.setReorderingAllowed(false);

        // Row sorter for search and sorting
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppTheme.MC_WHITE);
        root.add(scrollPane, BorderLayout.CENTER);

        // ── Footer ───────────────────────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout(0, 0));
        footer.setBackground(AppTheme.MC_GRAY);
        footer.setBorder(new EmptyBorder(10, 20, 10, 20));

        statusLabel = new JLabel("Loading...");
        statusLabel.setFont(AppTheme.getFontPlain(12));
        statusLabel.setForeground(AppTheme.TEXT_MUTED);
        footer.add(statusLabel, BorderLayout.WEST);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);

        JButton refreshBtn = new RoundedButton(
            "↻ Refresh",
            AppTheme.MC_YELLOW,
            new Color(255, 210, 30),
            new Color(200, 160, 0),
            20
        );
        refreshBtn.setFont(AppTheme.getFontBold(12));
        refreshBtn.setForeground(AppTheme.TEXT_DARK);
        refreshBtn.setPreferredSize(new Dimension(100, 32));
        refreshBtn.addActionListener(e -> loadData());

        JButton closeBtn = new RoundedButton(
            "Close",
            AppTheme.MC_RED,
            new Color(200, 20, 20),
            new Color(180, 0, 0),
            20
        );
        closeBtn.setFont(AppTheme.getFontBold(12));
        closeBtn.setForeground(AppTheme.MC_WHITE);
        closeBtn.setPreferredSize(new Dimension(80, 32));
        closeBtn.addActionListener(e -> dispose());

        buttons.add(refreshBtn);
        buttons.add(closeBtn);
        footer.add(buttons, BorderLayout.EAST);
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
        loadData();
        setVisible(true);
    }

    /**
     * Loads order data from the backend and populates the table.
     */
    private void loadData() {
        tableModel.setRowCount(0);

        List<OrderRecord> records = controller.loadOrderHistory();

        if (records.isEmpty()) {
            statusLabel.setText("No orders found. Place your first order!");
        } else {
            for (OrderRecord r : records) {
                tableModel.addRow(new Object[]{
                    r.getToken(),
                    r.getDateTime(),
                    r.getItems(),
                    r.getItemCount(),
                    r.getTotal(),
                    r.getPaymentMethod(),
                    r.getStatus()
                });
            }
            statusLabel.setText(records.size() + " order" + (records.size() != 1 ? "s" : "") + " found");
        }
    }

    /**
     * Filters table rows by token number (column 0).
     */
    private void applyFilter(String text) {
        if (text == null || text.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            // Filter on the Token column (index 0) — case-insensitive partial match
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text.trim(), 0));
        }
        // Update status
        int visible = table.getRowCount();
        int total = tableModel.getRowCount();
        if (text != null && !text.trim().isEmpty()) {
            statusLabel.setText(visible + " of " + total + " orders shown");
        } else {
            statusLabel.setText(total + " order" + (total != 1 ? "s" : "") + " found");
        }
    }

    /**
     * Custom renderer for alternating row backgrounds + status color coding.
     */
    private static class AlternatingRowRenderer extends DefaultTableCellRenderer {
        private static final Color EVEN_ROW = AppTheme.MC_WHITE;
        private static final Color ODD_ROW  = new Color(250, 250, 248);

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setFont(AppTheme.getFontPlain(12));
            setBorder(new EmptyBorder(0, 8, 0, 8));

            if (isSelected) {
                c.setBackground(AppTheme.MC_LIGHT_RED);
                c.setForeground(AppTheme.TEXT_DARK);
            } else {
                c.setBackground(row % 2 == 0 ? EVEN_ROW : ODD_ROW);
                c.setForeground(AppTheme.TEXT_DARK);
            }

            // Color-code the Status column
            if (column == 6 && value != null) {
                String status = value.toString();
                if ("Completed".equalsIgnoreCase(status)) {
                    c.setForeground(new Color(59, 109, 17));
                } else if ("Cancelled".equalsIgnoreCase(status)) {
                    c.setForeground(AppTheme.MC_RED);
                }
            }

            // Right-align numeric columns
            if (column == 0 || column == 3 || column == 4) {
                setHorizontalAlignment(SwingConstants.CENTER);
            } else {
                setHorizontalAlignment(SwingConstants.LEFT);
            }

            return c;
        }
    }
}
