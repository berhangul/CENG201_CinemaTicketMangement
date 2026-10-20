
public class TicketList {

    private TicketNode head;
    private TicketNode tail;

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
        return true;
     }





    // To remove a Node that is given id from a Linked list.
    public boolean removeTicket(int id){

         // store head ticket node.
        TicketNode currNode = head, prev = null;

        //CASE 1:
        // if removing element is head
         if(currNode != null && currNode.ticket.ticketId == id){

             // if there is only one ticketNode in the linked list.
             if(currNode == tail){
                 tail = null;
             }

             // change head's reference with its next to delete.
             head = currNode.next;

             // display the message.
             System.out.printf("[Id: %d] found and deleted\n",id);

             return true;
         }

         // CASE 2: if the id is somewhere other than at head

         // Search for the id to be deleted
         // keep track of the previous node
         // as it needs to change currNode.next
         while (currNode != null && currNode.ticket.ticketId != id){

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
                 System.out.printf("[Id: %d] found and deleted\n",id);
                 return true;

             }

             //since the key is at currNode
             //Unlink currNode from a linked list
             prev.next = currNode.next;

             // display the message.
             System.out.printf("[Id: %d] found and deleted\n",id);

             return true;
         }

         //CASE 3: The key is not present

        // display the message.
        System.out.printf("[Id: %d] not found\n",id);

         return false;

    }




    // to find a ticket with its id in a linked list
    public Ticket findTicket(int id){

         // initialize current
        TicketNode currNode = head;

        while (currNode != null && currNode.ticket.ticketId != id){
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
             System.out.println("[WARN]: Ticket list is empty");
             return;
         }

         // Traverse till the last node
         while (curr != null){

             // print all ticket information with using toString()
             System.out.println(curr.ticket.toString());

             // go next ticket after print
             curr = curr.next;
         }

    }











}