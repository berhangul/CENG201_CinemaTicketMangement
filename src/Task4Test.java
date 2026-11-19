void main(){

            CinemaSystem system = new CinemaSystem();

            // 1. Add 10 tickets to the system
            System.out.println(">>> STEP 1: Adding 10 Tickets to the System");
            system.addTicket(new Ticket(1, "Berhan Gul", "Interstellar",
                    "K-12", "13:40", 1));
            system.addTicket(new Ticket(2, "Erhan Gul", "Goodfellas",
                    "K-13", "18:15", 1));
            system.addTicket(new Ticket(3, "Serhan Gul", "The Irishman",
                    "K-14", "11:40", 3));
            system.addTicket(new Ticket(4, "Ali Ayik", "Se7en",
                    "A-05", "09:30", 2));
            system.addTicket(new Ticket(5, "Mehmet Kaya", "Inception",
                    "B-10", "21:00", 4));
            system.addTicket(new Ticket(6, "Zeynep Demir", "The Godfather",
                    "C-04", "15:20", 2));
            system.addTicket(new Ticket(7, "Can Yilmaz", "Eyes Wide Shut",
                    "D-08", "22:30", 3));
            system.addTicket(new Ticket(8, "Elif Sahin", "The Matrix",
                    "A-11", "10:15", 1));
            system.addTicket(new Ticket(9, "Emre Aydin", "Pulp Fiction",
                    "E-02", "16:50", 4));
            system.addTicket(new Ticket(10, "Selin Koc", "Fight Club",
                    "F-07", "14:00", 2));

            // 2. Add 5 seat requests to the waiting queue
            System.out.println("\n>>> STEP 2: Adding 5 Seat Requests to Waiting Queue");
            system.addSeatRequest(new SeatRequest(101, 1, "Ali Veli",
                    "Interstellar", "K-12"));
            system.addSeatRequest(new SeatRequest(102, 2, "Deniz Arslan",
                    "Se7en", "A-05"));
            system.addSeatRequest(new SeatRequest(103, 1, "Burak Celik",
                    "Goodfellas", "K-13"));
            system.addSeatRequest(new SeatRequest(104, 4, "Merve Ozkan",
                    "Inception", "B-10"));
            system.addSeatRequest(new SeatRequest(105, 3, "Kaan Yildiz",
                    "The Irishman", "K-14"));

            // 3. Add 2 cancellation records
            System.out.println("\n>>> STEP 3: Cancelling 2 Tickets (Adding to Cancellation Stack)");
            system.cancelTicket(2, "Schedule conflict");
            system.cancelTicket(7, "Customer requested");

            // 4. Process 4 seat requests (FIFO)
            System.out.println("\n>>> STEP 4: Processing 4 Waiting Customers");
            system.processWaitingCustomer();
            system.processWaitingCustomer();
            system.processWaitingCustomer();
            system.processWaitingCustomer();

            // 5. Print the final system state
            System.out.println("\n>>> STEP 5: Final System State (Before Sorting)");
            system.printSystemState();

            // 6. Sort and print the tickets according to showTime
            System.out.println("\n>>> STEP 6: Sorting Active Tickets by ShowTime (Merge Sort) & Printing");
            system.sortTicketsByShowTime();
            system.printSystemState();

}