public class SeatRequest {

    private int customerId;
    private int hallNumber;
    private String customerName;
    private String movieName;
    private String desiredSeat;

    public SeatRequest(int customerId, int hallNumber ,String customerName,
                       String movieName, String desiredSeat){

        this.customerId = customerId;
        this.hallNumber = hallNumber;
        this.customerName = customerName;
        this.movieName = movieName;
        this.desiredSeat = desiredSeat;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setRequestId(int customerId) {
        this.customerId = customerId;
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
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

    public String getDesiredSeat() {
        return desiredSeat;
    }

    public void setDesiredSeat(String desiredSeat) {
        this.desiredSeat = desiredSeat;
    }


    @Override
    public String toString() {
        return String.format("Request[ID=%-3d | %-14s | %-15s | Desired: %-4s (Hall %d)]",
                customerId, customerName, movieName, desiredSeat, hallNumber);
    }

}
