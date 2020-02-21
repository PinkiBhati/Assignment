class SpecialStack {
    private int array[];
    private static int top;
    private int capacity;
    int minelement;

    public SpecialStack(int size) {
        array = new int[size];
        capacity = size;
        top =-1;
    }
    static boolean isEmpty() {
        if (top == -1) {
            System.out.println("SpecialStack is empty");
            return true;
        }
        return false;
    }
    void isFull() {
        if (top == capacity - 1) {
            System.out.println("SpecialStack is full");
        }
    }
    void getMin() {
        if (isEmpty()) {
            System.out.println("SpecialStack is empty");
        } else {
            System.out.println("Current Minimum element is : " + minelement);
        }
    }
    void pop() {
        if (isEmpty()) {
            System.out.println("SpecialStack is empty\n");
            return;
        }
        System.out.print("Top element popped \t");
        int top1 = array[top--];

        if (top1 < minelement) {
            System.out.println(minelement);
            minelement = minelement *2 -top1;
        }
        else {
            System.out.print(top1+"\n");
        }
    }

    void push(int element) {
        if (isEmpty()) {
            minelement = element;
            array[++top] = element;
            System.out.println(element + " is inserted");
            return;
        }
        if (element < minelement) {
            array[++top] = 2*element - minelement;
            minelement = element;
        }
        else {
            array[++top] = element;
        }
        System.out.println(element + " is inserted");
    }
}

public class Q8{
    public static void main(String[] args) {
        SpecialStack specialStack = new SpecialStack(10);
        specialStack.push(45);
        specialStack.push(10);
        specialStack.push(67);
        specialStack.push(89);
        specialStack.pop();
        specialStack.pop();
        specialStack.getMin();
    }

}