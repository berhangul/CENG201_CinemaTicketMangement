public class CinemaSystem {

    private TicketList activeTickets;
    private SeatQueue waitingCustomers;
    private CancellationStack cancellationHistory;

    public CinemaSystem(){
        this.activeTickets = new TicketList();
        this.waitingCustomers = new SeatQueue();
        this.cancellationHistory = new CancellationStack();
    }

    public void addTicket(Ticket ticket){
        activeTickets.addTicket(ticket);
    }

    public void addSeatRequest(SeatRequest request){
        waitingCustomers.enqueue(request);
    }

    public void addCancellationRecord(CancellationRecord record){
        cancellationHistory.push(record);
    }

    public void processWaitingCustomer(){
        if(waitingCustomers.isEmpty()){
            System.out.println("[WARNING] No waiting customers to process. The seat queue is currently empty.");
        }else{
            SeatRequest request = waitingCustomers.dequeue();
            System.out.println("[INFO] Customer ID " + request.getCustomerId() +
                    " (Movie: " + request.getMovieName() +
                    ") has been removed from the waiting queue and processed.");
        }

    }

    public void printSystemState(){
        System.out.println();
        System.out.println("=".repeat(32) + " CINEMA SYSTEM STATE " + "=".repeat(32));
        System.out.println();

        System.out.printf("%s --- Active Tickets --- (total: %d)\n"," ".repeat(30), activeTickets.getSize());
        System.out.println();
        activeTickets.printList();
        System.out.println();

        System.out.printf("%s --- Waiting Queue --- (%d requests)\n"," ".repeat(29), waitingCustomers.getSize());
        System.out.println();
        waitingCustomers.printQueue();
        System.out.println();

        System.out.printf("%s --- Cancellation History --- (%d records)\n"," ".repeat(25),
                cancellationHistory.getSize());
        System.out.println();
        cancellationHistory.printStack();
        System.out.println();

        System.out.println("=".repeat(85));
        System.out.println();
    }

    public void cancelTicket(int ticketId, String reason){

        Ticket cancelledTicket = activeTickets.findTicket(ticketId);

        if(cancelledTicket == null){
            System.out.println("[WARNING] The ticket id not found");
            return;
        }

        activeTickets.removeTicket(ticketId);

        CancellationRecord cancellationRecord = new CancellationRecord(cancelledTicket,reason);
        addCancellationRecord(cancellationRecord);
        System.out.printf("[SUCCESS] The ticket id %d cancelled and added cancellation history.\n",ticketId);

    }

    public void sortTicketsByShowTime() {
        activeTickets.sortByShowTime();
    }




}
