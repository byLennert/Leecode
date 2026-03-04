package Hot100;

import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack {
    private Deque<Integer> stack;
    private Deque<Integer> min_stack;
    public MinStack() {
        this.min_stack = new ArrayDeque<>();
        this.stack = new ArrayDeque<>();
    }
    public void push(int val) {
        stack.push(val);
        if (min_stack.isEmpty()||val <= min_stack.peek()) {
            min_stack.push(val);
        }
    }
    public void pop() {
       int val =  this.stack.pop();
         if(!min_stack.isEmpty() && val == min_stack.peek()){
              min_stack.pop();
         }
    }
    public int top() {
        if (stack.isEmpty()) {
            return -1;
        }
        return this.stack.peek();
    }
    public int getMin() {
        if (min_stack.isEmpty()) {
            return -1;
        }
        return this.min_stack.peek();
    }
}
