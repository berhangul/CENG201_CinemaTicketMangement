
public class CancellationStack {

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

    public boolean isEmpty(){
        return size == 0;
    }

    public int getSize(){
        return size;
    }

    public void push(CancellationRecord record){

        if(record == null){
            throw new IllegalArgumentException("record is null");
        }

        StackNode newNode = new StackNode(record);

        newNode.next = top;
        top = newNode;
        size++;

    }

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

    public CancellationRecord peek(){
        if(isEmpty()){
            System.out.println("Cancellation stack is empty");
            return null;
        }

        return top.record;
    }


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
