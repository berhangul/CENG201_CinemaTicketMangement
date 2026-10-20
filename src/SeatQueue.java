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
    private int size = 0;


    public boolean isEmpty(){
        return (size == 0);
    }


    public void enqueue(SeatRequest request){

        QueueNode newNode = new QueueNode(request);

        if(rear == null){
            front = rear = newNode;
            size++;
            return;
        }

        rear.next = newNode;
        rear = newNode;

        size++;

    }



    public SeatRequest dequeue(){

        if(isEmpty()){
            System.out.println("The seat queue is empty !");
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



    public SeatRequest getFront(){

        if(isEmpty()){
            System.out.println("Seat queue is empty !");
            return null;
        }

        return front.request;
    }



    public SeatRequest getRear(){

        if(isEmpty()){
            System.out.println("Seat queue is empty !");
            return null;
        }

        return rear.request;
    }


    public void printQueue(){

        if(isEmpty()){
            System.out.println("[WARN] Seat request queue is empty.");
            return;
        }

        QueueNode curr = front;
        System.out.println("--- Pending Seat Requests ---");

        while (curr != null){
            System.out.println(curr.request.toString());
            curr = curr.next;
        }

    }


    public int getSize(){return size;}




}
