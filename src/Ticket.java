public class Ticket {

    // Fields
    private int ticketId;
    private static int idCounter = 1;
    private String customerName;
    private String movieName;
    private String seatNumber;
    private String showTime;
    private int hallNumber;


    // Constructor
    public Ticket(String customerName, String movieName, String seatNumber,
                  String showTime,int hallNumber) {
        this.ticketId = idCounter++;
        this.customerName = customerName;
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.showTime = showTime;
        this.hallNumber = hallNumber;
    }

    // Getter and setter methods

    public int getTicketId() {
        return ticketId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {this.seatNumber = seatNumber;}

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public void setHallNumber(int newHallNumber){this.hallNumber = newHallNumber;}

    public int getHallNumber(){
        return this.hallNumber;
    }

    @Override
    public String toString() {
        return String.format("Ticket[ID=%-2d | %-14s | %-15s | Seat: %-4s (Hall %d) | Time: %s]",
                ticketId, customerName, movieName, seatNumber, hallNumber, showTime);
    }
}
