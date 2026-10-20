public class Ticket {

    // Fields
    int ticketId;
    private String customerName;
    private String movieName;
    private String seatNumber;
    private String showTime;
    private int hallNumber;


    // Constructor
    public Ticket(int ticketId, String customerName, String movieName, String seatNumber,
                  String showTime,int hallNumber) {
        this.ticketId = ticketId;
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

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
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
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", customerName='" + customerName + '\'' +
                ", movieName='" + movieName + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", showTime='" + showTime + '\'' +
                ", hallNumber='" + hallNumber + '\'' +
                '}';
    }
}
