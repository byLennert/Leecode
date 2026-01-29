package Hot100;

import java.util.*;

class LRUCache {
    //定义双向链表
    private static class Node{
        int key;
        int value;
        Node pre,next;
        Node(int key,int value){
            this.key=key;
            this.value=value;
        }
    }
    Node dummy = new Node(-1,-1);
    int  capacity;
    private Map<Integer,Node> map = new HashMap<>();
    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummy.next = dummy;
        dummy.pre = dummy;
    }

    public int get(int key) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            //从原来的位置拿走
            removeNode(node);
            //再放到开头
            pushFirst(node);

            return  node.value;
        }
        return -1;
    }

    public void put(int key, int value) {

        if(map.containsKey(key)){
            //如果存在，更新
            Node node = map.get(key);
            removeNode(node);

            node.value = value;
            map.put(key,node);
            //再放到开头
            pushFirst(node);

        }else {
            if(map.size()==capacity){
                int old_key =  dummy.pre.key;
                Node node = map.get(old_key);
                removeNode(node);
                map.remove(old_key);//删除最久未使用的
            }
            //如果不存在，就增加新的
            Node node = new Node(key,value);
            map.put(key,node);
            //再放到开头
            pushFirst(node);
        }
    }

    private void removeNode( Node node){
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private void pushFirst(Node node){
        node.next = dummy.next;
        node.next.pre = node;
        dummy.next = node;
        node.pre = dummy;
    }









    public static void main(String[] args) {
        // 创建LRUCache，容量为2
        LRUCache cache = new LRUCache(2);

        // 按照给定序列执行操作
        System.out.println("LRUCache(2) - 创建容量为2的缓存");

        cache.put(1, 1);
        System.out.println("put(1,1) - 插入1:1");

        cache.put(2, 2);
        System.out.println("put(2,2) - 插入2:2");

        int result1 = cache.get(1);
        System.out.println("get(1) - 获取键1，结果: " + result1);  // 应该返回1

        cache.put(3, 3);
        System.out.println("put(3,3) - 插入3:3，会移除最近最少使用的2");

        int result2 = cache.get(2);
        System.out.println("get(2) - 获取键2，结果: " + result2);  // 应该返回-1

        cache.put(4, 4);
        System.out.println("put(4,4) - 插入4:4，会移除最近最少使用的1");

        int result3 = cache.get(1);
        System.out.println("get(1) - 获取键1，结果: " + result3);  // 应该返回-1

        int result4 = cache.get(3);
        System.out.println("get(3) - 获取键3，结果: " + result4);  // 应该返回3

        int result5 = cache.get(4);
        System.out.println("get(4) - 获取键4，结果: " + result5);  // 应该返回4
    }
}


