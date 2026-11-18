import java.time.LocalDateTime;

public class CancellationRecord{

    private final Ticket cancelledTicket;
    private final String cancellationReason;
    private final LocalDateTime cancelledTime;
    private final String formattedTime;

    public CancellationRecord(Ticket newCancelledTicket,String reason){

        if(newCancelledTicket == null){
            throw new IllegalArgumentException("Ticket cannot be null");
        }

        this.cancelledTicket = newCancelledTicket;
        this.cancellationReason = reason;
        this.cancelledTime = LocalDateTime.now();

        this.formattedTime = String.format("%d-%02d-%02d  %02d:%02d:%02d"
                ,cancelledTime.getYear(),cancelledTime.getMonthValue(),cancelledTime.getDayOfMonth(),
                cancelledTime.getHour(),cancelledTime.getMinute(),cancelledTime.getSecond());
    }


    public Ticket getCancelledTicket() {
        return cancelledTicket;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public LocalDateTime getCancelledTime() {
        return cancelledTime;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    @Override
    public String toString() {
        return String.format("Cancelled[ID=%-2d | %-10s | %-10s (%s, Hall %s)\n             " +
                        "   | Date: %-10s | Reason: %-10s]",
                cancelledTicket.getTicketId(), cancelledTicket.getCustomerName(),
                cancelledTicket.getMovieName(), cancelledTicket.getSeatNumber(),
                cancelledTicket.getHallNumber(), formattedTime, cancellationReason);
    }
}
