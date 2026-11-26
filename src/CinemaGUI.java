import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CinemaGUI extends JFrame {
    private static final int ROWS = 6, COLS = 8;
    private static final Color ACCENT = new Color(229, 169, 59), TAKEN = new Color(192, 57, 43);
    private static final Color FREE_DARK = new Color(52, 61, 74), FREE_LIGHT = new Color(224, 230, 237);

    private final CinemaSystem sys = new CinemaSystem();
    private final Map<String, ImageIcon> posters = new HashMap<>();
    private final JButton[][] seats = new JButton[ROWS][COLS];

    private final JLabel statsLbl = new JLabel(), posterLbl = new JLabel("No Poster", SwingConstants.CENTER);
    private final JLabel seatLbl = new JLabel("Select a seat from the map");
    private final JComboBox<Movie> movieBox = new JComboBox<>();
    private final JComboBox<String> hallBox = new JComboBox<>(), timeBox = new JComboBox<>();
    private final JTextField nameField = new JTextField(), searchField = new JTextField();
    private final JButton bookBtn = btn("Book Ticket", ACCENT, Color.BLACK, e -> bookTicket());
    private final JButton waitBtn = btn("Join Waitlist", null, null, e -> joinWaitlist());

    private final DefaultTableModel ticketModel = roModel("ID", "Customer", "Movie", "Hall", "Time", "Seat");
    private final DefaultTableModel queueModel = roModel("#", "Cust ID", "Customer", "Movie", "Hall", "Desired Seat");
    private final DefaultTableModel cancelModel = roModel("Stack", "ID", "Customer", "Movie", "Hall", "Seat", "Time", "Reason", "Cancelled At");
    private final JTable ticketTable = table(ticketModel);

    private String selectedSeat;
    private boolean syncing, dark = true;
    private int nextCustId = 101;

    public CinemaGUI() {
        super("Cinema Ticket & Seat Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1120, 720);
        setMinimumSize(new Dimension(940, 620));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 8));

        add(buildHeader(), BorderLayout.NORTH);
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBorder(new EmptyBorder(0, 12, 12, 12));
        tabs.addTab("Book & Seat Map", buildBookingTab());
        tabs.addTab("Active Tickets", buildTicketsTab());
        tabs.addTab("Waiting Queue", buildQueueTab());
        tabs.addTab("Cancellation History", buildCancelTab());
        add(tabs, BorderLayout.CENTER);

        for (Movie m : sys.getMovies()) movieBox.addItem(m);
        onMovieChanged();
        refreshAll();
    }

    private JPanel buildHeader() {
        JPanel h = new JPanel(new BorderLayout(12, 0));
        h.setBorder(new EmptyBorder(12, 16, 6, 16));
        JLabel title = new JLabel("CINEMA MANAGEMENT");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(ACCENT);
        statsLbl.setFont(new Font("SansSerif", Font.BOLD, 13));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.add(statsLbl);
        right.add(btn("Demo Data", null, null, e -> loadDemo((JButton) e.getSource())));
        right.add(btn("Light Mode", null, null, e -> toggleTheme((JButton) e.getSource())));
        h.add(title, BorderLayout.WEST);
        h.add(right, BorderLayout.EAST);
        return h;
    }

    private JPanel buildBookingTab() {
        JPanel root = new JPanel(new BorderLayout(16, 0));
        root.setBorder(new EmptyBorder(12, 4, 4, 4));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(300, 0));
        left.setBorder(new EmptyBorder(0, 8, 0, 8));

        Dimension pDim = new Dimension(170, 255);
        posterLbl.setPreferredSize(pDim);
        posterLbl.setMaximumSize(pDim);
        posterLbl.setAlignmentX(CENTER_ALIGNMENT);
        posterLbl.setBorder(BorderFactory.createLineBorder(ACCENT, 2));

        nameField.putClientProperty("JTextField.placeholderText", "Enter customer name...");
        movieBox.addActionListener(e -> onMovieChanged());
        hallBox.addActionListener(e -> { if (!syncing) resetSeat(); });
        timeBox.addActionListener(e -> { if (!syncing) resetSeat(); });
        seatLbl.setAlignmentX(CENTER_ALIGNMENT);
        seatLbl.setFont(new Font("SansSerif", Font.BOLD, 12));

        left.add(posterLbl);
        left.add(Box.createVerticalStrut(12));
        left.add(fieldRow("Movie", movieBox));
        left.add(Box.createVerticalStrut(6));
        left.add(fieldRow("Hall", hallBox));
        left.add(Box.createVerticalStrut(6));
        left.add(fieldRow("Show Time", timeBox));
        left.add(Box.createVerticalStrut(6));
        left.add(fieldRow("Customer", nameField));
        left.add(Box.createVerticalStrut(10));
        left.add(seatLbl);
        left.add(Box.createVerticalStrut(10));

        JPanel btns = new JPanel(new GridLayout(1, 2, 8, 0));
        btns.setMaximumSize(new Dimension(290, 36));
        btns.add(bookBtn);
        btns.add(waitBtn);
        left.add(btns);

        JPanel right = new JPanel(new BorderLayout(0, 12));
        right.setBorder(new EmptyBorder(4, 8, 4, 8));
        JLabel screen = new JLabel("S C R E E N", SwingConstants.CENTER);
        screen.setOpaque(true);
        screen.setBackground(ACCENT);
        screen.setForeground(Color.BLACK);
        screen.setFont(new Font("SansSerif", Font.BOLD, 13));
        screen.setPreferredSize(new Dimension(0, 28));

        JPanel grid = new JPanel(new GridLayout(ROWS, COLS, 8, 8));
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++) {
                String code = String.format("%c-%02d", 'A' + r, c + 1);
                JButton b = btn(code, null, null, e -> selectSeat(code));
                b.setFocusPainted(false);
                grid.add(seats[r][c] = b);
            }

        JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 4));
        legend.add(chip("Available", FREE_DARK));
        legend.add(chip("Selected", ACCENT));
        legend.add(chip("Occupied (click to join waitlist)", TAKEN));

        right.add(screen, BorderLayout.NORTH);
        right.add(grid, BorderLayout.CENTER);
        right.add(legend, BorderLayout.SOUTH);
        root.add(left, BorderLayout.WEST);
        root.add(right, BorderLayout.CENTER);
        return root;
    }

    private JPanel buildTicketsTab() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBorder(new EmptyBorder(12, 4, 4, 4));
        searchField.putClientProperty("JTextField.placeholderText", "Filter by customer, movie, seat, or time...");
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { refreshTickets(); }
            public void removeUpdate(DocumentEvent e) { refreshTickets(); }
            public void changedUpdate(DocumentEvent e) { refreshTickets(); }
        });
        JPanel acts = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        acts.add(btn("Sort by Time (Merge Sort)", null, null, e -> { sys.sortTicketsByShowTime(); refreshTickets(); }));
        acts.add(btn("Cancel Selected", TAKEN, Color.WHITE, e -> cancelSelected()));

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.add(searchField, BorderLayout.CENTER);
        top.add(acts, BorderLayout.EAST);
        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(ticketTable), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildQueueTab() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBorder(new EmptyBorder(12, 4, 4, 4));
        JPanel acts = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        acts.add(btn("Add Request", null, null, e -> addRequestDialog()));
        acts.add(btn("Process Next (FIFO)", ACCENT, Color.BLACK, e -> processNext()));

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Waiting customers are served in FIFO order."), BorderLayout.WEST);
        top.add(acts, BorderLayout.EAST);
        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(table(queueModel)), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildCancelTab() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBorder(new EmptyBorder(12, 4, 4, 4));
        p.add(new JLabel("Cancellation history in LIFO (Stack) order — newest at the top."), BorderLayout.NORTH);
        p.add(new JScrollPane(table(cancelModel)), BorderLayout.CENTER);
        return p;
    }

    private void onMovieChanged() {
        Movie m = (Movie) movieBox.getSelectedItem();
        if (m == null) return;
        syncing = true;
        hallBox.removeAllItems();
        for (int h : m.getAvailableHalls()) hallBox.addItem("Hall " + h);
        timeBox.removeAllItems();
        for (String t : m.getShowTimes()) timeBox.addItem(t);
        syncing = false;
        loadPoster(m.getPosterPath());
        resetSeat();
    }

    private void resetSeat() {
        selectedSeat = null;
        seatLbl.setText("Select a seat from the map");
        bookBtn.setEnabled(true);
        refreshSeats();
    }

    private void selectSeat(String code) {
        selectedSeat = code;
        boolean taken = isTaken(code);
        bookBtn.setEnabled(!taken);
        seatLbl.setText(taken ? code + " is OCCUPIED — click Join Waitlist" : "Selected Seat: " + code + " (Available)");
        refreshSeats();
    }

    private void bookTicket() {
        Movie m = (Movie) movieBox.getSelectedItem();
        String name = nameField.getText().trim();
        if (m == null || selectedSeat == null || name.isEmpty()) { warn("Enter customer name and select an available seat."); return; }
        String time = (String) timeBox.getSelectedItem();
        if (isTaken(selectedSeat)) { warn("Seat " + selectedSeat + " is taken. Join the waitlist instead."); return; }
        Ticket t = new Ticket(name, m.getTitle(), selectedSeat, time, selectedHall());
        sys.addTicket(t);
        nameField.setText("");
        selectedSeat = null;
        seatLbl.setText("Booked Ticket #" + t.getTicketId() + " (" + t.getSeatNumber() + ")");
        refreshAll();
    }

    private void joinWaitlist() {
        Movie m = (Movie) movieBox.getSelectedItem();
        String name = nameField.getText().trim();
        if (m == null || selectedSeat == null || name.isEmpty()) { warn("Enter customer name and pick a desired seat."); return; }
        SeatRequest r = new SeatRequest(nextCustId++, selectedHall(), name, m.getTitle(), selectedSeat);
        sys.addSeatRequest(r);
        nameField.setText("");
        JOptionPane.showMessageDialog(this, "Added to Waiting Queue:\n" + r, "Waiting Queue", JOptionPane.INFORMATION_MESSAGE);
        refreshAll();
    }

    private void cancelSelected() {
        int row = ticketTable.getSelectedRow();
        if (row < 0) { warn("Select a ticket to cancel."); return; }
        int id = (int) ticketModel.getValueAt(row, 0);
        String reason = JOptionPane.showInputDialog(this, "Cancellation reason for Ticket #" + id + ":", "Schedule conflict");
        if (reason != null && !reason.trim().isEmpty() && sys.cancelTicket(id, reason.trim())) refreshAll();
    }

    private void addRequestDialog() {
        JTextField nIn = new JTextField();
        JComboBox<Movie> mIn = new JComboBox<>(sys.getMovies());
        JComboBox<Integer> hIn = new JComboBox<>();
        JComboBox<String> sIn = new JComboBox<>();
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++) sIn.addItem(String.format("%c-%02d", 'A' + r, c + 1));
        Runnable sync = () -> {
            hIn.removeAllItems();
            Movie mv = (Movie) mIn.getSelectedItem();
            if (mv != null) for (int h : mv.getAvailableHalls()) hIn.addItem(h);
        };
        mIn.addActionListener(e -> sync.run());
        sync.run();

        Object[] form = {"Customer Name:", nIn, "Movie:", mIn, "Hall:", hIn, "Desired Seat:", sIn};
        if (JOptionPane.showConfirmDialog(this, form, "Add Seat Request", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String name = nIn.getText().trim();
            Movie mv = (Movie) mIn.getSelectedItem();
            if (name.isEmpty() || mv == null || hIn.getSelectedItem() == null) { warn("Customer name is required."); return; }
            sys.addSeatRequest(new SeatRequest(nextCustId++, (int) hIn.getSelectedItem(), name, mv.getTitle(), (String) sIn.getSelectedItem()));
            refreshAll();
        }
    }

    private void processNext() {
        SeatRequest req = sys.processWaitingCustomer();
        if (req == null) { warn("Waiting queue is empty."); return; }
        String[] times = {"12:00", "15:00", "18:00", "21:00"};
        for (Movie m : sys.getMovies()) if (m.getTitle().equalsIgnoreCase(req.getMovieName())) { times = m.getShowTimes(); break; }

        JComboBox<String> tIn = new JComboBox<>(times);
        Object[] msg = {"Dequeued (FIFO): " + req.getCustomerName() + " — " + req.getMovieName()
                + " (Hall " + req.getHallNumber() + ", Seat " + req.getDesiredSeat() + ")", "Issue ticket now? Choose showtime:", tIn};
        if (JOptionPane.showConfirmDialog(this, msg, "Process Waiting Customer", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String time = (String) tIn.getSelectedItem();
            if (sys.isSeatTaken(req.getMovieName(), req.getHallNumber(), time, req.getDesiredSeat()))
                warn("Seat " + req.getDesiredSeat() + " is still occupied at " + time + ".");
            else
                sys.addTicket(new Ticket(req.getCustomerName(), req.getMovieName(), req.getDesiredSeat(), time, req.getHallNumber()));
        }
        refreshAll();
    }

    private void refreshAll() {
        statsLbl.setText(String.format("Active: %d  |  Waiting: %d  |  Cancelled: %d",
                sys.getActiveAccount(), sys.getWaitingAccount(), sys.getCancellationCount()));
        refreshSeats();
        refreshTickets();

        queueModel.setRowCount(0);
        SeatRequest[] q = sys.getWaitingCustomer();
        for (int i = 0; i < q.length; i++)
            queueModel.addRow(new Object[]{i == 0 ? "1 (FRONT)" : i + 1, q[i].getCustomerId(), q[i].getCustomerName(), q[i].getMovieName(), "Hall " + q[i].getHallNumber(), q[i].getDesiredSeat()});

        cancelModel.setRowCount(0);
        CancellationRecord[] st = sys.getCancellationHistory();
        for (int i = 0; i < st.length; i++) {
            Ticket t = st[i].getCancelledTicket();
            cancelModel.addRow(new Object[]{i == 0 ? "TOP" : i + 1, t.getTicketId(), t.getCustomerName(), t.getMovieName(), "Hall " + t.getHallNumber(), t.getSeatNumber(), t.getShowTime(), st[i].getCancellationReason(), st[i].getFormattedTime()});
        }
    }

    private void refreshTickets() {
        ticketModel.setRowCount(0);
        String q = searchField.getText().trim().toLowerCase();
        for (Ticket t : sys.getActiveTickets())
            if (q.isEmpty() || t.getCustomerName().toLowerCase().contains(q) || t.getMovieName().toLowerCase().contains(q)
                    || t.getSeatNumber().toLowerCase().contains(q) || t.getShowTime().contains(q))
                ticketModel.addRow(new Object[]{t.getTicketId(), t.getCustomerName(), t.getMovieName(), "Hall " + t.getHallNumber(), t.getShowTime(), t.getSeatNumber()});
    }

    private void refreshSeats() {
        Color fBg = dark ? FREE_DARK : FREE_LIGHT, fFg = dark ? Color.WHITE : Color.DARK_GRAY;
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++) {
                JButton b = seats[r][c];
                String code = b.getText();
                boolean sel = code.equals(selectedSeat), taken = isTaken(code);
                b.setBackground(sel ? ACCENT : taken ? TAKEN : fBg);
                b.setForeground(sel ? Color.BLACK : taken ? Color.WHITE : fFg);
            }
    }

    private boolean isTaken(String seat) {
        Movie m = (Movie) movieBox.getSelectedItem();
        String t = (String) timeBox.getSelectedItem();
        return m != null && t != null && hallBox.getSelectedItem() != null
                && sys.isSeatTaken(m.getTitle(), selectedHall(), t, seat);
    }

    private int selectedHall() {
        String s = (String) hallBox.getSelectedItem();
        return s == null ? 1 : Integer.parseInt(s.replace("Hall ", ""));
    }

    private void loadPoster(String path) {
        ImageIcon icon = posters.computeIfAbsent(path, p -> {
            try {
                File f = new File(p);
                if (!f.exists()) f = new File(p.replaceFirst("^src[/\\\\]", ""));
                BufferedImage img = f.exists() ? ImageIO.read(f) : null;
                return img == null ? null : new ImageIcon(img.getScaledInstance(170, 255, Image.SCALE_SMOOTH));
            } catch (Exception e) { return null; }
        });
        posterLbl.setIcon(icon);
        posterLbl.setText(icon == null ? "No Poster" : null);
    }

    private void loadDemo(JButton b) {
        sys.addTicket(new Ticket("Berhan Gul", "Interstellar", "C-04", "13:40", 1));
        sys.addTicket(new Ticket("Ali Ayik", "Interstellar", "C-05", "13:40", 1));
        sys.addTicket(new Ticket("Zeynep Demir", "The Dark Knight", "B-03", "21:45", 3));
        sys.addTicket(new Ticket("Mehmet Kaya", "Se7en", "A-02", "09:30", 2));
        Ticket c = new Ticket("Can Yilmaz", "Goodfellas", "D-06", "16:50", 1);
        sys.addTicket(c);
        sys.cancelTicket(c.getTicketId(), "Schedule conflict");
        sys.addSeatRequest(new SeatRequest(nextCustId++, 1, "Elif Sahin", "Interstellar", "C-04"));
        sys.addSeatRequest(new SeatRequest(nextCustId++, 2, "Deniz Arslan", "Se7en", "A-02"));
        b.setEnabled(false);
        refreshAll();
    }

    private void toggleTheme(JButton b) {
        dark = !dark;
        try {
            UIManager.setLookAndFeel(dark ? new FlatDarkLaf() : new FlatLightLaf());
            FlatLaf.updateUI();
            b.setText(dark ? "Light Mode" : "Dark Mode");
            refreshSeats();
        } catch (Exception ignored) {}
    }

    private static JButton btn(String text, Color bg, Color fg, java.awt.event.ActionListener al) {
        JButton b = new JButton(text);
        if (bg != null) { b.setBackground(bg); b.setForeground(fg); b.setFont(b.getFont().deriveFont(Font.BOLD)); }
        b.addActionListener(al);
        return b;
    }

    private static JPanel fieldRow(String label, JComponent c) {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setMaximumSize(new Dimension(290, 32));
        JLabel l = new JLabel(label);
        l.setPreferredSize(new Dimension(72, 26));
        p.add(l, BorderLayout.WEST);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    private static JPanel chip(String text, Color bg) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        JLabel box = new JLabel();
        box.setPreferredSize(new Dimension(14, 14));
        box.setOpaque(true);
        box.setBackground(bg);
        box.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p.add(box);
        p.add(new JLabel(text));
        return p;
    }

    private static DefaultTableModel roModel(String... cols) {
        return new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
    }

    private static JTable table(DefaultTableModel m) {
        JTable t = new JTable(m);
        t.setRowHeight(28);
        t.setShowHorizontalLines(true);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setFillsViewportHeight(true);
        t.getTableHeader().setReorderingAllowed(false);
        return t;
    }

    private void warn(String msg) { JOptionPane.showMessageDialog(this, msg, "Notice", JOptionPane.WARNING_MESSAGE); }

    public static void main(String[] args) {
        try {
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
            FlatDarkLaf.setup();
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new CinemaGUI().setVisible(true));
    }
}
