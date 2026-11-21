
public class TicketList {

    private TicketNode head;
    private TicketNode tail;
    private int size = 0;

    // Linked list node
     private static class TicketNode {
        Ticket ticket;
        TicketNode next;
        public TicketNode(Ticket ticket){
            this.ticket = ticket;
            this.next = null;
        }
    }




    // To add a new ticket inside Linked List.
    public boolean addTicket(Ticket t){

         if(t == null){
             return false;
         }

         // Create a new node with given data.
        TicketNode newNode = new TicketNode(t);

        // if the linked list is empty.
        if(head == null){
            head = newNode;
        }else{
            // else add newNode as a next of tail.
            tail.next = newNode;
        }
        // in the last upgrade the tail for two situations.
        tail = newNode;
        System.out.printf("[INFO] Ticket added -> ID: %-4d | Movie: %-15s | Seat: %-5s | Time: %-5s\n"
                ,t.getTicketId(),t.getMovieName(),t.getSeatNumber(),t.getShowTime());
        size++;
        return true;
     }





    // To remove a Node that is given id from a Linked list.
    public boolean removeTicket(int id){

         // store head ticket node.
        TicketNode currNode = head, prev = null;

        //CASE 1:
        // if removing element is head
         if(currNode != null && currNode.ticket.getTicketId() == id){

             // if there is only one ticketNode in the linked list.
             if(currNode == tail){
                 tail = null;
             }

             // change head's reference with its next to delete.
             head = currNode.next;

             // display the message.
             System.out.printf("[INFO] Id: %d found and deleted\n",id);
             size--;

             return true;
         }

         // CASE 2: if the id is somewhere other than at head

         // Search for the id to be deleted
         // keep track of the previous node
         // as it needs to change currNode.next
         while (currNode != null && currNode.ticket.getTicketId() != id){

             // if currNode does not hold id
             // continue to next node
             prev = currNode;
             currNode = currNode.next;
         }

         // If the id was present, it should be at currNode.
         // Therefore, the currNode shall not be null.
         if(currNode != null){

             // if deleting a node is tail.
             // The tail must be previous
             if(currNode == tail){
                 tail = prev;
                 prev.next = null;

                 // display the message.
                 System.out.printf("[INFO] Id: %d found and deleted\n",id);
                 size--;
                 return true;

             }

             //since the key is at currNode
             //Unlink currNode from a linked list
             prev.next = currNode.next;

             // display the message.
             System.out.printf("[INFO] Id: %d found and deleted\n",id);
             size--;

             return true;
         }

         //CASE 3: The key is not present

        // display the message.
        System.out.printf("[WARNING] Id: %d not found\n",id);

         return false;

    }




    // to find a ticket with its id in a linked list
    public Ticket findTicket(int id){

         // initialize current
        TicketNode currNode = head;

        while (currNode != null && currNode.ticket.getTicketId() != id){
            currNode = currNode.next;
        }

        // data found
        if(currNode != null){
            return currNode.ticket;
        }

        // data not found
        return null;
    }

    // to print all tickets in the ticket list
    public void printList(){

         // initial current
         TicketNode curr = head;

         // if the linked list is empty
         if(curr == null){
             System.out.println("[WARNING]: Ticket list is empty");
             return;
         }

         // Traverse till the last node
         while (curr != null){

             // print all ticket information with using toString()
             System.out.println(curr.ticket.toString());
             System.out.println("-".repeat(85));

             // go next ticket after print
             curr = curr.next;
         }

    }

    public int getSize() {
        return size;
    }

    private TicketNode getMiddle(TicketNode head){

         if(head == null){
             return null;
         }

         TicketNode fast = head.next;
         TicketNode slow = head;

         while (fast != null && fast.next != null){
             slow = slow.next;
             fast = fast.next.next;
         }

         return slow;

    }


    private TicketNode merge(TicketNode left, TicketNode right){

         if(left == null){
             return right;
         }else if (right == null) {
             return left;
         }

         if(left.ticket.getShowTime().compareTo(right.ticket.getShowTime()) <= 0 ){
             left.next = merge(left.next, right);
             return left;
         }else {
             right.next = merge(left,right.next);
             return right;
         }

    }


    private TicketNode mergeSort(TicketNode head){

         if(head == null || head.next == null){
             return head;
         }

         TicketNode middle = getMiddle(head);
         TicketNode nextOfMiddle = middle.next;

         middle.next = null;

         TicketNode left = mergeSort(head);
         TicketNode right = mergeSort(nextOfMiddle);

         return merge(left,right);

    }

    public void sortByShowTime(){
         if(head == null || head.next == null){
             return;
         }
         this.head = mergeSort(this.head);

         TicketNode temp = head;

         while (temp.next != null){
             temp = temp.next;
         }

         this.tail = temp;

    }

    public Ticket[] toArray(){

         int index = 0;
         Ticket[] a = new Ticket[size];
         TicketNode temp = head;

         while (temp != null){
             a[index++] = temp.ticket;
             temp = temp.next;
         }

         return a;

    }

    public boolean isSeatTaken(String movieName, int hallNumber, String showTime, String seatNumber) {
        TicketNode current = head;
        while (current != null) {
            Ticket t = current.ticket;
            if (t.getMovieName().equalsIgnoreCase(movieName)
                    && t.getHallNumber() == hallNumber
                    && t.getShowTime().equals(showTime)
                    && t.getSeatNumber().equalsIgnoreCase(seatNumber)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }





}