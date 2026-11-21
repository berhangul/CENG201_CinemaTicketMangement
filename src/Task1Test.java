void main(){

    // create a linkedList(ticket list)
    TicketList list1 = new TicketList();


    // create 5 tickets and add them to the linked list
    list1.addTicket(new Ticket("Berhan gül",
            "Interstellar","k-12","13.40",1));

    list1.addTicket(new Ticket("erhan gül",
            "Good fellas","k-13","14.40",1));

    list1.addTicket(new Ticket("Serhan gül",
            "Irishman","k-14","15.40",3));

    list1.addTicket(new Ticket("Orhan gül",
            "Casino","k-15","13.40",5));

    list1.addTicket(new Ticket("Serpil gül",
            "Godfather","k-16","16.40",6));

    System.out.println();


    // print all list
    list1.printList();
    System.out.println();

    // remove a ticket with its id
    list1.removeTicket(2);
    System.out.println();

    // print a ticket with its id
    System.out.println("//Find ticket with id 4");
    System.out.println(list1.findTicket(4));
    System.out.println();

    // print all list
    list1.printList();








}