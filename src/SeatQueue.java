public class SeatQueue {


    private static class QueueNode{
        SeatRequest request;
        QueueNode next;

        public QueueNode(SeatRequest request) {
            this.request = request;
            this.next = null;
        }
    }


    private QueueNode front;
    private QueueNode rear;
    private int size;


    public SeatQueue(){
        this.size = 0;
    }

    // Checking queue is empty
    public boolean isEmpty(){
        return (size == 0);
    }



    // Adding a new element into the queue
    public void enqueue(SeatRequest request){

        QueueNode newNode = new QueueNode(request);

        if(rear == null){
            front = rear = newNode;
            size++;
            return;
        }

        rear.next = newNode;
        rear = newNode;
        System.out.println("[INFO] Seat request added");

        size++;

    }


    // removing first element of queue
    public SeatRequest dequeue(){

        if(isEmpty()){
            System.out.println("[WARNING] The seat queue is empty !");
            return null;
        }

        SeatRequest removedRequest = front.request;
        front = front.next;

         if(front == null){
             rear = null;
         }

         size--;
         return removedRequest;
    }


    //getting first element
    public SeatRequest getFront(){

        if(isEmpty()){
            System.out.println("[WARNING] Seat queue is empty !");
            return null;
        }

        return front.request;
    }


    //getting last element
    public SeatRequest getRear(){

        if(isEmpty()){
            System.out.println("[WARNING] Seat queue is empty !");
            return null;
        }

        return rear.request;
    }

    //To print all queue elements
    public void printQueue(){

        if(isEmpty()){
            System.out.println("[WARNING] Seat request queue is empty.");
            return;
        }

        QueueNode curr = front;

        while (curr != null){
            System.out.println(curr.request.toString());
            System.out.println("-".repeat(85));
            curr = curr.next;
        }

    }

    // Getting size of elements in the queue
    public int getSize(){return size;}




}
