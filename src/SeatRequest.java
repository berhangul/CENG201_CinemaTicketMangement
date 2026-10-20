public class SeatRequest {

    private int requestId;
    private int hallNumber;
    private String customerName;
    private String movieName;
    private String desiredSeat;

    public SeatRequest(int requestId, int hallNumber ,String customerName,
                       String movieName, String desiredSeat){

        this.requestId = requestId;
        this.hallNumber = hallNumber;
        this.customerName = customerName;
        this.movieName = movieName;
        this.desiredSeat = desiredSeat;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
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
        return "Request{" +
                "requestId=" + requestId +
                ", customerName='" + customerName + '\'' +
                ", movieName='" + movieName + '\'' +
                ", desiredSeat='" + desiredSeat + '\'' +
                ", hallNumber='" + hallNumber + '\'' +
                '}';
    }

}
