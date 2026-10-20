void main(){


    //Create a queue to store requests.

    SeatQueue queue1 = new SeatQueue();


    // Create 3 requests to add queue.
    SeatRequest request1 = new SeatRequest(1,3,
            "berhan", "Home Alone","C-2");

    SeatRequest request2 = new SeatRequest(2,5,
            "erhan", "Spiderman 2","A-5");

    SeatRequest request3 = new SeatRequest(3,3,
            "serhan", "Whisper man","G-23");


    // Add requests
    queue1.enqueue(request1);
    queue1.enqueue(request2);
    queue1.enqueue(request3);

    //print current size.
    System.out.println("[INFO] Current Queue Size:");
    System.out.println(queue1.getSize());

    // print all requests which in the queue.
    queue1.printQueue();

    System.out.println();

    // Remove first request.
    System.out.println("[ACTION] Dequeued Request:");
    System.out.println(queue1.dequeue());
    System.out.println();

    // print the size of queue after delete first request.
    System.out.println("[INFO] Current Queue Size:");
    System.out.println(queue1.getSize());


    // print remain requests.
    queue1.printQueue();
    System.out.println()
    ;

    // remove all requests in the queue.
    System.out.println("[ACTION] Dequeued Request:");
    System.out.println(queue1.dequeue());
    System.out.println(queue1.dequeue());
    System.out.println();

    // check the state that if the queue is empty.
    System.out.println("[TEST] Trying to dequeue from empty queue:");
    queue1.dequeue();


}
