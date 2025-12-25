package LinkedList;
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; next = null; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
class Solution {

    public ListNode removeElements(ListNode head, int val) {
        ListNode pNode  = new ListNode();
        pNode.next = head;

        ListNode temp = head;
        ListNode pre = pNode;
        while(temp != null){
            if(temp.val == val){
                pre.next = temp.next;
                temp = temp.next;
            }else{
                pre = temp;
                temp = temp.next;

            }

        }
        return pNode.next;
    }

    public ListNode reverseList(ListNode head) {
        ListNode pNode = head;
        if (head!=null){
            ListNode last = head;
            ListNode pre = last.next;
            ListNode temp=null;
            while(pre!=null){
                last.next = temp;
                temp = last;
                last = pre;
                pre = pre.next;
            }
            last.next = temp;
            pNode = last;

        }
        return pNode;
    }

    public ListNode swapPairs(ListNode head) {
        ListNode pNode = null;
        if (head!=null && head.next!=null){
            ListNode last = head;
            ListNode pre = last.next;
            while (last!=null && last.next!=null){
                head=pre;
                pre = last.next;
                ListNode temp = pre.next;
                pre.next=last;
                last.next=null;
                if (pNode!=null){
                    pNode.next=pre;
                }
                pNode = last;
                last = temp;
            }
            if (last!=null){
                pNode.next=last;
            }
        }

        return  head;
    }

    public static void main(String[] args) {
        // 1. 根据数组 [1,2,3,4] 建链表
        int[] arr = {1, 2, 3, 4};
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int v : arr) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }

        // 2. 调用你的算法
        ListNode newHead = new Solution().swapPairs(dummy.next);

        // 3. 打印结果，一眼看出对不对
        System.out.print("swap 后: ");
        while (newHead != null) {
            System.out.print(newHead.val + " -> ");
            newHead = newHead.next;
        }
        System.out.println("null");
    }
}
