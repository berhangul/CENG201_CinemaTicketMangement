public class CinemaSystem {

    private TicketList activeTickets;
    private SeatQueue waitingCustomers;
    private CancellationStack cancellationHistory;
    private Movie[] movies;

    public CinemaSystem() {
        this.activeTickets = new TicketList();
        this.waitingCustomers = new SeatQueue();
        this.cancellationHistory = new CancellationStack();
        this.movies = generateMovies();
    }

    public CinemaSystem(Movie[] movies) {
        super();
        this.movies = movies;
    }

    public void addTicket(Ticket ticket) {
        activeTickets.addTicket(ticket);
    }

    public void addSeatRequest(SeatRequest request) {
        waitingCustomers.enqueue(request);
    }

    public void addCancellationRecord(CancellationRecord record) {
        cancellationHistory.push(record);
    }

    public SeatRequest processWaitingCustomer() {
        if (waitingCustomers.isEmpty()) {
            System.out.println("[WARNING] No waiting customers to process. The seat queue is currently empty.");
            return null;
        } else {
            SeatRequest request = waitingCustomers.dequeue();
            System.out.println("[INFO] Customer ID " + request.getCustomerId() +
                    " (Movie: " + request.getMovieName() +
                    ") has been removed from the waiting queue and processed.");
            return request;
        }

    }

    public void printSystemState() {
        System.out.println();
        System.out.println("=".repeat(32) + " CINEMA SYSTEM STATE " + "=".repeat(32));
        System.out.println();

        System.out.printf("%s --- Active Tickets --- (total: %d)\n", " ".repeat(30), activeTickets.getSize());
        System.out.println();
        activeTickets.printList();
        System.out.println();

        System.out.printf("%s --- Waiting Queue --- (%d requests)\n", " ".repeat(29), waitingCustomers.getSize());
        System.out.println();
        waitingCustomers.printQueue();
        System.out.println();

        System.out.printf("%s --- Cancellation History --- (%d records)\n", " ".repeat(25),
                cancellationHistory.getSize());
        System.out.println();
        cancellationHistory.printStack();
        System.out.println();

        System.out.println("=".repeat(85));
        System.out.println();
    }

    public boolean cancelTicket(int ticketId, String reason) {

        Ticket cancelledTicket = activeTickets.findTicket(ticketId);

        if (cancelledTicket == null) {
            System.out.println("[WARNING] The ticket id not found");
            return false;
        }

        activeTickets.removeTicket(ticketId);

        CancellationRecord cancellationRecord = new CancellationRecord(cancelledTicket, reason);
        addCancellationRecord(cancellationRecord);
        System.out.printf("[SUCCESS] The ticket id %d cancelled and added cancellation history.\n", ticketId);
        return true;

    }

    public void sortTicketsByShowTime() {
        activeTickets.sortByShowTime();
    }

    // Getter methods for GUI

    public Ticket[] getActiveTickets() {
        return activeTickets.toArray();
    }

    public SeatRequest[] getWaitingCustomer() {
        return waitingCustomers.toArray();
    }

    public CancellationRecord[] getCancellationHistory() {
        return cancellationHistory.toArray();
    }

    public int getActiveAccount() {
        return activeTickets.getSize();
    }

    public int getWaitingAccount() {
        return waitingCustomers.getSize();
    }

    public int getCancellationCount() {
        return cancellationHistory.getSize();
    }

    public Movie[] getMovies() {
        return movies;
    }


    private Movie[] generateMovies() {

        Movie interstellar = new Movie(
                "Interstellar",
                "src/posters/interstellar.jpg",
                new int[]{1, 2},
                new String[]{"09:30", "13:40", "18:15"});

        Movie darkKnight = new Movie(
                "The Dark Knight",
                "src/posters/the_dark_knight.jpg",
                new int[]{1, 3},
                new String[]{"12:15", "17:30", "21:45"});

        Movie movie1917 = new Movie(
                "1917",
                "src/posters/1917.jpg",
                new int[]{1, 3},
                new String[]{"10:00", "14:30", "19:00"});

        Movie casino = new Movie(
                "Casino",
                "src/posters/casino.jpg",
                new int[]{2, 4},
                new String[]{"13:40", "17:30", "21:15"});

        Movie eyesWideShut = new Movie(
                "Eyes Wide Shut",
                "src/posters/eyes_wide_shut.jpg",
                new int[]{2, 3},
                new String[]{"16:50", "21:00", "22:00"});

        Movie goodfellas = new Movie(
                "Goodfellas",
                "src/posters/goodfellas.jpg",
                new int[]{1, 4},
                new String[]{"11:40", "16:50", "20:30"});

        Movie obsession = new Movie(
                "Obsession",
                "src/posters/obsession.jpg",
                new int[]{3, 4},
                new String[]{"12:15", "15:45", "19:30"});

        Movie se7en = new Movie(
                "Se7en",
                "src/posters/se7en.jpg",
                new int[]{2, 3},
                new String[]{"09:30", "14:30", "21:00"});

        Movie sinners = new Movie(
                "Sinners",
                "src/posters/sinners.jpg",
                new int[]{1, 4},
                new String[]{"11:40", "18:15", "21:45"});

        Movie warDogs = new Movie(
                "War Dogs",
                "src/posters/war_dogs.jpg",
                new int[]{2, 4},
                new String[]{"10:30", "15:00", "20:00"});

        return new Movie[]{interstellar, darkKnight, movie1917,
                casino, eyesWideShut, goodfellas, obsession, se7en, sinners, warDogs};

    }

    public boolean isSeatTaken(String movieName, int hallNumber, String showTime, String seatNumber) {
        return activeTickets.isSeatTaken(movieName, hallNumber, showTime, seatNumber);
    }

}