
public class CancellationStack {

    // inner class StackNode to create a linked structure.
    private static class StackNode{
        CancellationRecord record;
        StackNode next;
        public StackNode(CancellationRecord record){
            this.record = record;
            this.next = null;
        }
    }

    private StackNode top;
    private int size = 0;

    // checking for the cancellation stack is empty.
    public boolean isEmpty(){return size == 0;}

    // getting size of stack (element number of stack).
    public int getSize(){
        return size;
    }

    // Adding a new element to the end of a stack with giving a record
    public void push(CancellationRecord record){

        if(record == null){
            throw new IllegalArgumentException("record is null");
        }

        StackNode newNode = new StackNode(record);

        newNode.next = top;
        top = newNode;
        size++;

    }

    // Popping an element from the end of a stack
    public CancellationRecord pop(){

        if(isEmpty()){
            System.out.println("Cancellation stack is empty");
            return null;
        }

        CancellationRecord deleted = top.record;

        top = top.next;
        size--;
        return deleted;

    }

    // returns the peek element of stack.
    public CancellationRecord peek(){
        if(isEmpty()){
            System.out.println("Cancellation stack is empty");
            return null;
        }

        return top.record;
    }


    // Printing all elements in the stack by one by.
    public void printStack(){

        if (isEmpty()){
            System.out.println("[WARNING] Cancellation stack is empty");
            return;
        }

        StackNode curr = top;

        while (curr != null){
            System.out.println(curr.record);
            System.out.println("-".repeat(85));
            curr = curr.next;
        }

    }

    // Creates an array that includes all elements in the stack.
    public CancellationRecord[] toArray(){

        CancellationRecord[] a = new CancellationRecord[size];
        int index = 0;
        StackNode temp = top;

        while (temp != null){
            a[index++] = temp.record;
            temp = temp.next;
        }

        return a;
    }







}
