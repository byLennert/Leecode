package LinkedList;

public class MyLinkedList {
    //定义节点
    class LinkNode{
        int val;
        LinkNode next;
        LinkNode(int val){
            this.val = val;
        }
    }

    private int size = 0;
    private LinkNode head;


    public MyLinkedList() {
        this.head =new LinkNode(0);
    }

    public int get(int index) {
        if(index >= size||index<0){
            return -1;
        }
        LinkNode cur = this.head;
        for (int i=0;i<=index;i++){
            cur = cur.next;
        }
        return cur.val;
    }

    public void addAtHead(int val) {
        LinkNode pNode = new LinkNode(val);
        LinkNode cur= this.head;
        pNode.next = cur.next;
        cur.next = pNode;
        size++;
    }

    public void addAtTail(int val) {
        LinkNode cur = this.head;
        while(cur.next!=null){
            cur = cur.next;
        }
        cur.next = new LinkNode(val);
        cur =cur.next;
        cur.next = null;
        size++;
    }

    public void addAtIndex(int index, int val) {
        if (index == size){
            addAtTail(val);
            return;
        }
        if (index > size||index < 0){
            return;
        }
        LinkNode cur = this.head;
        for (int i =0;i<index;i++){
            cur=cur.next;
        }
        LinkNode pNode = new LinkNode(val);
        pNode.next = cur.next;
        cur.next = pNode;
        size++;
    }

    public void deleteAtIndex(int index) {
        if (index >= size||index < 0){
            return;
        }
        LinkNode pre = this.head;
        for (int i=0;i<index;i++){
            pre = pre.next;
        }

        pre.next = pre.next.next;
    }
//使用哨兵（虚拟头结点会容易很多，节省多余的判断）
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //[1,2,3,4,5]
        ListNode pNode = head;
        ListNode pre = head;
        ListNode last = head.next;
        if (last==null){
            return null;
        }
        int count = 1;
        while (count<=n){
            if (last==null){
                return pre.next;
            }
            last = last.next;
            count++;
        }
        //删头结点

        while (last!=null){
            pre = pre.next;
            last = last.next;
        }
        pre.next = pre.next.next;
        return pNode;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = 0;
        int lenB = 0;
        ListNode pNodeA = headA;
        ListNode pNodeB = headB;
        if (headA != null && headB != null) {
            //两个链表非空才对
            while (pNodeA != null) {
                pNodeA = pNodeA.next;
                lenA++;
            }
            while (pNodeB != null) {
                pNodeB = pNodeB.next;
                lenB++;
            }
            int length = Math.abs(lenA - lenB) ;
            if (lenA > lenB) {
                //A先走
                while (length > 0) {
                    headA = headA.next;
                    length--;
                }

            } else {
                while (length > 0) {
                    headB = headB.next;
                    length--;
                }
            }
            while (headA != null && headB != null) {
                if (headA == headB) {
                    return headA;
                }
                headA = headA.next;
                headB = headB.next;
            }
            return null;
        }
        return null;
    }
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while( fast!=null  && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            //环中相遇
            if (slow == fast){
                //两个指针同步走
                ListNode temp = head;
                while (temp != slow ){
                    temp = temp.next;
                    slow = slow.next;
                }
                //相遇时候就是环入口节点
                return temp;
            }
        }
        //不存在环
        return null;
    }






}



