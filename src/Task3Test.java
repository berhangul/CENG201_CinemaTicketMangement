void main(){

    Ticket ticket1 = new Ticket("berhan",
            "Joker","K11","12:30",10);
    Ticket ticket2 = new Ticket("erhan",
            "Whisper","A9","16:00",3);
    Ticket ticket3 = new Ticket("serhan",
            "Batman","L2","11:30",4);

    CancellationRecord record1 = new CancellationRecord(ticket1,"wrong movie");
    CancellationRecord record2 = new CancellationRecord(ticket2,"payment problem");
    CancellationRecord record3 = new CancellationRecord(ticket3,"Time changing");

    CancellationStack cancellationStack = new CancellationStack();

    cancellationStack.push(record1);
    cancellationStack.push(record2);
    cancellationStack.push(record3);

    System.out.println(" ".repeat(26) + "------ALL RECORDS------");
    cancellationStack.printStack();

    System.out.println();

    System.out.println("//Peek record");
    System.out.println(cancellationStack.peek());

    System.out.println();

    System.out.println("//popped record");
    System.out.println(cancellationStack.pop());

    System.out.println();

    cancellationStack.pop();
    cancellationStack.pop();

    System.out.println("//popping from empty stack");
    cancellationStack.pop();


}
