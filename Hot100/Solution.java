package Hot100;


import com.sun.source.tree.Tree;

import java.util.*;

public class Solution {
//辅助哈希表求两数之和，一定要先查后加，否则会自我匹配
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if (map.containsKey(target-nums[i])){
                return new int[]{i,map.get(target-nums[i])};
            }
            map.put(nums[i],i);
        }
        return new int[]{-1,-1};
    }
//不同组成的字母，排序就一定相同。善用集合的computeifabsent
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> strArr = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            char[] chars =  strs[i].toCharArray();
            Arrays.sort(chars);
            strArr.computeIfAbsent(String.valueOf(chars),key->new ArrayList<>()).add(strs[i]);
        }
        return new ArrayList<>(strArr.values());
    }
//最长路径在于定义起点（没有自己-1），然后找起点，累加计数。关键要去重
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int num:nums){
            set.add(num);
        }
        int count = 0;
        for(int num:set){
            if(set.contains(num-1)){
                continue;
            }
            int x =  num +1;
            while(set.contains(x)){
                x++;
            }
            count = Math.max(count,x-num);
            if (count *2  > set.size()){
                return count;
            }
        }
        return count;
    }

    public void moveZeroes(int[] nums) {
    int i = 0;
    int j = i;
    while(j<nums.length){
        if(nums[j]!=0){
            nums[i]=nums[j];
            i++;
        }
        j++;
    }
    while(i<nums.length){
        nums[i]=0;
        i++;
    }
    }
//短板理论，移动的是两端更低的那个，不断更新，移动一次之后一定可以找到
    public int maxArea(int[] height) {
        int max = 0;
        int left = 0;
        int right = height.length - 1;
        while(left < right){
            int leftHeight = height[left];
            int rightHeight = height[right];
            int area = Math.min(leftHeight, rightHeight)*(right-left);
            max = Math.max(max,area);
            if (leftHeight > rightHeight) {
                right--;
            } else {
                left++;
            }
        }
        return max;
    }


    public List<List<Integer>> threeSum(int[] nums) {
        int i = 0;
        Arrays.sort(nums);
        Set<List<Integer>> res = new HashSet<>();
        while (i < nums.length) {
            int left = i+1;
            int right = nums.length - 1;
            while(left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                } else if (sum>0) {
                    right--;
                }else  {
                    left++;
                }
            }
           i++;
        }
        return new ArrayList<>(res);
    }
//接雨水：计算左右最高的墙壁高度，就能算出当前竖柱的雨水量:1.辅助数组存储前缀高度，后缀高度；2.相向双指针遍历
    public int trap(int[] height) {
        Map<Integer, Integer> leftMax = new HashMap<>();
        Map<Integer, Integer> rightMax = new HashMap<>();
        leftMax.put(0,height[0]);
        for (int i = 1; i < height.length; i++) {
            leftMax.put(i,Math.max(leftMax.get(i-1), height[i]));
        }
        rightMax.put(height.length-1,height[height.length-1]);
        for (int i = height.length-2; i >= 0; i--) {
            rightMax.put(i,Math.max(rightMax.get(i+1),height[i]));
        }
        int ans = 0;
        for (int i = 1; i < height.length-1; i++) {
            ans += Math.min(leftMax.get(i),rightMax.get(i))-height[i];
        }
        return ans;
    }
    public int trap02(int[] height) {
        int leftMax = height[0];
        int rightMax = height[height.length-1];
        int ans = 0;
        int left = 0;
        int right = height.length-1;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (leftMax < rightMax) {
//                如果左边的比右边的小，那么肯定能知道左边的桶的雨水量
                ans+=leftMax-height[left];
                left++;
            }else {
//                如果右边比左边小或者相等，那么可以计算右边的雨水量
                ans+=rightMax-height[right];
                right--;
            }
        }

        return ans;
    }
//滑动窗口：最长不同子串长度，哈希表判重
    public int lengthOfLongestSubstring(String s) {
        char[] str = s.toCharArray();
        int ans = 0;
        int left = 0;
        int right = 0;
        Set<Character> set = new HashSet<>();
        while (left < str.length && right < str.length) {
            while (set.contains(str[right])) {
                if(right > left){
                    set.remove(str[left]);
                    left++;
                }
            }
            set.add(str[right]);
            ans = Math.max(ans,right-left+1);
            right++;
        }
        return ans;
    }
//438滑动窗口：子串异位词组合,第一个思路本来是用集合验证是否存在字符串。但是效率有点低，O（mnlogn）。正确应该使用数组统计各个字符的次数，使用array.equals方法
    public List<Integer> findAnagrams0(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        Set<String> set = new HashSet<>();
        char[] pStr = p.toCharArray();
        Arrays.sort(pStr);
        set.add(new String(pStr));
        int length = p.length();
        int left = 0;
        int right = left+length - 1;
        while (right < s.length()) {
            char[] temp = s.substring(left, right + 1).toCharArray();
            Arrays.sort(temp);
            if (set.contains(new String(temp))) {
                ans.add(left);
            }
            left++;
            right++;
        }
        return ans;
    }

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        int[] countP = new int[26];
        int[] countS = new int[26];
        for (int i = 0; i < p.length(); i++) {
            countP[p.charAt(i) - 'a']++;
        }
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            countS[s.charAt(right) - 'a']++;

            left = right - p.length()+1;
            right++;
            if (left< 0) {
                continue;
            }
            if (Arrays.equals(countP, countS)) {
                ans.add(left);
            }
            countS[s.charAt(left) - 'a']--;

        }

        return ans;
    }
//s[j] = k + s[i]  => s[j] = k + s[i]
    public int subarraySum(int[] nums, int k) {
        int [] s = new int[nums.length+1];
        for (int i=0;i<nums.length;i++){
            s[i+1] =  s[i]+nums[i];
        }
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <s.length ; i++) {
            ans += map.getOrDefault(s[i]-k,0);
            map.put(s[i],map.getOrDefault(s[i],0)+1);
        }
        return ans;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] ans = new int[nums.length-k+1];
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
//           进
            while (!q.isEmpty() && nums[q.getLast()] < nums[i] ) {
                //后来者更大，需要删除已有的最新的，保证只留下最可能成为最大值的,可能要去掉多个所以用的是while而不是if
                q.removeLast();
            }
            q.addLast(i);
//            出，能出or该出就出
            int left = i-k+1;
            if (left >= q.getFirst() ) {
                q.removeFirst();
            }
//            记录元素
            if (left>=0){
                ans[left]=nums[q.getFirst()];
            }
        }
        return ans;
    }

    private boolean isCovered(int[] cntS, int[] cntT) {
        for (int i = 'A'; i <= 'Z'; i++) {
            if (cntS[i] < cntT[i]) {
                return false;
            }
        }
        for (int i = 'a'; i <= 'z'; i++) {
            if (cntS[i] < cntT[i]) {
                return false;
            }
        }
        return true;
    }

    public String minWindow(String s, String t) {
        int [] countT = new int[128];
        int [] countS = new int[128];
        Map<Integer,String> map = new HashMap<>();
        int min = Integer.MAX_VALUE;
        for (int i=0;i<t.length();i++){
            countT[t.charAt(i)]++;
        }//统计出来每个字母的频率
        int left = 0;
        int right = 0;
        while (right < s.length()){
            countS[s.charAt(right)]++;
            right++;
            if (isCovered(countS, countT)){
                while(left<right && isCovered(countS, countT)){
                    countS[s.charAt(left)]--;
                    left++;
                }
                map.put(right-left+1,s.substring(left-1,right)) ;
                min = Math.min(min,right-left+1);
            }
        }
        if (map.containsKey(min)){
            return map.get(min);
        }
    return "";
    }

//被覆盖的城市
    public int countCoveredBuildings(int n, int[][] buildings) {
        int []rowMin = new int[n+1]; // 同行最左
        int []colMin = new int[n+1]; // 同列最低
        int []rowMax = new int[n+1]; // 同行最右
        int []colMax = new int[n+1]; // 同行最高

        Arrays.fill(rowMin,Integer.MAX_VALUE);
        Arrays.fill(colMin,Integer.MAX_VALUE);

        for(int[] building:buildings){
           int x =  building[0];
           int y = building[1];
           if (rowMax[y] < x){
               rowMax[y] = x;
           }
           if (colMax[x] < y){
               colMax[x] = y;
           }
           if (rowMin[y] > x){
               rowMin[y] = x;
           }
           if (colMin[x] > y){
               colMin[x] = y;
           }
        }
        int ans = 0;
        for (int[] building:buildings ){
            int x = building[0];
            int y = building[1];
            if( x < rowMax[y] && x > rowMin[y]&& y < colMax[x] && y > colMin[x]){
                ans++;
            }
        }
        return ans;
    }

    public int maxSubArray(int[] nums) {
           int []sum =  new int[nums.length+1];
           for (int i = 0; i < nums.length; i++) {
                    sum[i+1] = sum[i] + nums[i];
           }

           int min = 0;
           int maxVal = Integer.MIN_VALUE;
           for (int i = 1; i < sum.length; i++) {
               if (sum[i]-min > maxVal){
                   maxVal = sum[i]-min;
               }
               if (sum[i] < min){
                   min = sum[i];
               }
           }
           return maxVal;
    }
//合并区间，先按照左端点排序，然后就可以仅仅比较右端点和下一个左端点了。可以合并或直接确定是结果。于是切换到下一个作为基准继续比较。
    public int[][] merge(int[][] intervals) {
        List<int []> ans = new ArrayList<>();
        Arrays.sort(intervals,(a,b)->a[0]-b[0]);//正序排序左端点
        for (int i = 0; i < intervals.length; i++) {
            if (!ans.isEmpty() && ans.getLast()[1]>=intervals[i][0]){
                ans.getLast()[1]=Math.max(ans.getLast()[1],intervals[i][1]);
            }else  {
                ans.addLast(intervals[i]);
            }
        }
        return ans.toArray(new int[ans.size()][]);
    }

    public int[] productExceptSelf(int[] nums) {
//        先计算前缀后缀
        int [] pre =  new int[nums.length+1];
        pre[0] = 1;
        int [] suf =  new int[nums.length+1];
        suf[nums.length-1] =1;
        for (int i = 1; i <=nums.length; i++){
            pre[i] = pre[i-1]*nums[i-1];
        }
        for (int i = nums.length-2; i >=0; i--){
            suf[i] = suf[i+1]*nums[i+1];
        }
        int []res =  new int[nums.length];
        for(int i =0;i<nums.length;i++){
            res[i] = pre[i]*suf[i];
        }
        return res;
    }

    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while(nums[i]>=1 && nums[i]<=nums.length && nums[i]!=nums[nums[i]-1]){
                int temp = nums[nums[i]-1];
                nums[nums[i]-1] = nums[i];
                nums[i] = temp;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i+1) {
                return i+1;
            }
        }
        return nums.length+1;
    }

    public void setZeroes(int[][] matrix) {
        Set<Integer> row = new HashSet<>();
        Set<Integer> col = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    row.add(i);
                    col.add(j);
                }
            }
        }
        for (Integer i : row) {
            Arrays.fill(matrix[i], 0);
        }
        for (Integer i : col) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[j][i] = 0;
            }
        }
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int m =  matrix.length;
        int n = matrix[0].length;
        List<Integer> ans = new ArrayList<>();
        int[][] copy = new int[m][n];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
        int [][]DIRECTION = new int [][]{{0,1},{1,0},{0,-1},{-1,0}};
        int i=0,j=0;
        int di = 0;
        for (int k = 0; k < n*m; k++) {
            ans.add(matrix[i][j]);
            copy[i][j] = Integer.MAX_VALUE;
            int x = i+DIRECTION[di][0];
            int y = j+DIRECTION[di][1];
            if (x<0 || x>=m || y<0 || y>=n || copy[x][y]==Integer.MAX_VALUE) {
                di = (di+1) % 4;
            }
            i = i+DIRECTION[di][0];
            j = j+DIRECTION[di][1];
        }

        return ans;
    }

    public void rotate(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length/2; j++) {
                int temp  = matrix[i][j];
                matrix[i][j] = matrix[i][matrix[i].length-j-1];
                matrix[i][matrix[i].length-j-1] = temp;
            }
        }
    }
    public boolean searchMatrix(int[][] matrix, int target) {
       int i = 0;
       int j = matrix[0].length-1;
       while (i < matrix.length && j >= 0){
           if (matrix[i][j] == target){
               return true;
           }else if (matrix[i][j] > target){
               j--;
           }else {
               i++;
           }
       }
        return false;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = 1, lenB = 1;
        ListNode a = headA, b = headB;
        while (a!=null){
            a = a.next;
            lenA++;
        }
        while (b!=null){
            b = b.next;
            lenB++;
        }
        if (lenB>lenA){
            for (int i = 0; i < (lenB-lenA); i++) {
                headB = headB.next;
            }
        }else {
            for (int i = 0; i < (lenA-lenB); i++) {
                headA = headA.next;
            }
        }
        while (headA!=headB){

            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }

    public ListNode reverseList(ListNode head) {
        ListNode temp = new ListNode(0);

        while(head!=null){
            ListNode next = head.next;
            head.next = temp.next;
            temp.next = head;
            head = next;
        }
        return temp.next;
    }

    public ListNode getMiddleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast!=null && fast.next != null ) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    public boolean isPalindrome(ListNode head) {
        ListNode midNode = getMiddleNode(head);
        ListNode headB =  reverseList(midNode);
        while(headB!=null && head!=null){
            if (headB.val != head.val ) {
                return false;
            }
            headB = headB.next;
            head = head.next;
        }
        return true;
    }

    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast!=null && fast.next != null ) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode temp = head;
                while (temp != slow) {
                    temp = temp.next;
                    slow = slow.next;
                }
                return temp;
            }
        }
        return null;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(0);
        ListNode p = head;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                p.next = list1;
                p = p.next;
                list1 = list1.next;
            }else  {
                p.next = list2;
                p = p.next;
                list2 = list2.next;
            }
        }
        if (list1 != null) {
            p.next = list1;
        }else  {
            p.next = list2;
        }
        return head.next;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        boolean addtion = false;
        ListNode head = new ListNode(0);
        ListNode p = head;
        while (l1 != null && l2 != null) {
            if(addtion){
                //当前计算进位
                if (l1.val + l2.val +1 < 10) {
                    addtion = false;
                }
                p.next = new ListNode((l1.val + l2.val+1)%10);
            }else  {
                //当前计算没有进位
                if (l1.val + l2.val >= 10) {
                    addtion = true;
                }
                p.next = new ListNode((l1.val + l2.val)%10);
            }
            p = p.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            if(addtion){
                //当前计算进位
                if (l1.val +1 < 10) {
                    addtion = false;
                }
                p.next = new ListNode((l1.val +1)%10);
            }else  {
                //当前计算没有进位

                p.next = l1;
            }
            p = p.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            if(addtion){
                //当前计算进位
                if (l2.val +1 < 10) {
                    addtion = false;
                }
                p.next = new ListNode((l2.val +1)%10);
            }else  {
                //当前计算没有进位

                p.next = l2;
            }
            p = p.next;
            l2 = l2.next;
        }
        if (addtion) {
            p.next = new ListNode(1);
            p = p.next;
        }
        return head.next;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pHead = new ListNode(0);
        pHead.next = head;
        ListNode fast = pHead;
        ListNode slow = pHead;
        int count = 0;
        while(fast!=null&&count<n){
            fast = fast.next;
            count++;
        }
        while(fast!=null && fast.next!=null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;

        return pHead.next;
    }
    public ListNode swapPairs(ListNode head) {
        ListNode pHead = new ListNode(0);
        pHead.next = head;
        ListNode p = pHead;
        while (p.next != null && p.next.next != null) {
            ListNode next = p.next.next.next;
            ListNode temp = p.next;
            p.next = p.next.next;
            p.next.next = temp;
            p = temp;
            p.next = next;
        }
        return pHead.next;
    }
//反转链表2-片段反转  需要哨兵+pre /cur/next三个指针保证当前指向前面的，最后连接原链表和反转后的尾巴和头部。返回哨兵下一个
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new  ListNode(0) ;
        dummy.next = head;
        ListNode p = dummy;
        int count = 0;
        while (count < left-1) {
            p = p.next;
            count++;
        }//p在left前一个，从这里开始反转
        ListNode pre = null;
        ListNode cur = p.next;
        while (count < right) {//遍历最后一次结束cur是反转的下一个，pre时反转的最后一个
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            count++;
        }
        p.next.next = cur;
        p.next = pre;
        return dummy.next;
    }
//K个一组反转链表-多次调用reverseBetween
    public ListNode reverseKGroup(ListNode head, int k) {
        int length = 0;
        ListNode p =  head;
        while (p!= null) {
            length++;
            p = p.next;
        }
        ListNode dummy = new  ListNode(0) ;
        dummy.next = head;
        int curLocation = 1;
        while (length-curLocation+1 >= k) {
            dummy.next = reverseBetween(dummy.next,curLocation,curLocation+k-1);
            curLocation +=k;
        }
        return dummy.next;
    }
//复制带随机指针的链表-哈希表存储映射关系
    public Node copyRandomList(Node head) {
        Map<Node, Integer> oldNodeMap = new HashMap<>();
        Map<Integer, Node> newNodeMap = new HashMap<>();
        Node newHead = new Node(0);
        Node p = head;
        Node cur = newHead;
        int index = 0;
        while (p != null) {
            Node nxt = new Node(p.val);
            oldNodeMap.put(p,index);
            newNodeMap.put(index++, nxt);
            cur.next = nxt;
            cur = cur.next;
            p = p.next;
        }
        p = head;
        cur = newHead.next;
        while (p != null) {
          cur.random =  newNodeMap.get(oldNodeMap.get(p.random));
          p = p.next;
          cur = cur.next;
        }
        return newHead.next;
    }

//链表元素排序-递归变成合并两个有序链表 - mergeTwoLists - middleNode
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode second = middleNode(head), first = head;
        ListNode nxt = second.next;
        second.next = null;
        ListNode second_plus =  sortList(nxt);
        ListNode first_plus =  sortList(first);
        return mergeTwoLists(second_plus,first_plus);
    }


//    获取链表的中间节点
    public ListNode middleNode(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = dummy;
        while (fast != null && fast.next!= null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
//合并K个有序链表-两两合并
    public ListNode mergeKLists01(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        ListNode result = lists[0];
        for (int i = 1; i < lists.length; i++) {
          result =  mergeTwoLists(result,lists[i]);
        }
        return result;
    }
//优先队列
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b) -> a.val - b.val);
        for (ListNode node : lists) {
            if (node != null) {
                pq.offer(node);
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            cur.next = node;
            cur = cur.next;
            if (node.next != null) {
                pq.offer(node.next);
            }
        }
        return dummy.next;
    }

//从现在开始变成了树部分，链表部分全部都写完，最后一道LRU单独写在一个文件当中。

//中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result);
        return result;
    }

    public void dfs(TreeNode root,List<Integer> result) {
        if (root == null) {
            return;
        }
        dfs(root.left, result);
        result.add(root.val);
        dfs(root.right, result);
    }
    //最大高度
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left =  maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left,right) + 1;
    }
// 翻转二叉树
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode temp = invertTree(root.left);
        root.left = invertTree(root.right);
        root.right = temp;
        return root;
    }
//检查轴对称
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left == null && root.right == null) {
            return true;
        }
        if (root.left != null && root.right != null) {
            return isMirror(root.left, root.right);
        }
        return false;
    }
    public boolean isMirror(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null) return false;
        return root1.val == root2.val
                && isMirror(root1.left, root2.right)
                && isMirror(root1.right, root2.left);
    }
//最长路径长度
    int max = 0;
    public int diameterOfBinaryTree(TreeNode root) {
      linkLength(root);
      return max;
    }
//   求root的最长链长度
    public int linkLength(TreeNode root) {
       if (root == null) return -1;//这样如果只有一个根节点，恰好算出来链长是0，对于叶子来说，链长就是 -1+1=0
       int left =  linkLength(root.left)+1;// 左子树最大链长+1（已经考虑了如果左子树为空的情况)
       int right = linkLength(root.right)+1;
       max =  Math.max(max,left+right);//更新max，保证max拿到全局最长的直径
       return Math.max(left,right);//返回当前子树的最大链长
    }
//层次遍历

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(); // 替代 List.of()
        }
        List<List<Integer>> ans = new ArrayList<>();//结果数组
        List<TreeNode> cur = new ArrayList<>(); //当前层节点数组
        cur.add(root);

        while (!cur.isEmpty()) {
            List<TreeNode> nxt = new ArrayList<>();
            List<Integer> vals = new ArrayList<>(cur.size());
            for (TreeNode node : cur) {
                vals.add(node.val);
                if (node.left != null)  nxt.add(node.left);
                if (node.right != null) nxt.add(node.right);
            }
            cur = nxt;
            ans.add(vals);
        }
        return ans;
    }

//有序数组构建高度平衡二叉树
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums, 0, nums.length - 1);
    }
//辅助函数，传入数组以及当前处理的左右边界
    public TreeNode buildTree(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = buildTree(nums, left, mid - 1);
        node.right = buildTree(nums, mid + 1, right);
        return node;
    }

    public boolean isValidBST(TreeNode root) {
        return isValidBST2(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
//辅助函数，传入当前节点以及允许的最小值和最大值
    public  boolean isValidBST2(TreeNode root,long min, long max) {
        if (root == null) {
            return true;
        }
        if (root.val <= min || root.val >= max) {
            return false;
        }
        return isValidBST2(root.left, min, root.val) && isValidBST2(root.right, root.val, max);

    }

    int k;int ans;
    public int kthSmallest(TreeNode root, int k) {
        dfskth(root);
        return ans;
    }

    public void dfskth(TreeNode root) {
        if (root == null||k==0) {
            return;
        }
        dfskth(root.left);
        k--;
        if (k==0){
            ans = root.val;
            return;
        }
        dfskth(root.right);
    }

//二叉树的右视图
    //层次遍历，每层最后一个节点
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;//这里一定要注意排除根节点为空的情况，否则下面cur.getLast会报错
        List<TreeNode> cur = new ArrayList<>();
        List<TreeNode>nxt = new ArrayList<>();
        cur.add(root);
        while (!cur.isEmpty()) {
            //每层遍历
            //这层要做的事,添加最后一个不为空的节点
            ans.add(cur.getLast().val);
            for (TreeNode node : cur) {
                if (node.left != null) {
                    nxt.add(node.left);
                }
                if (node.right != null) {
                    nxt.add(node.right);
                }
            }
            cur = nxt;
            nxt = new ArrayList<>();
        }
        return ans;
    }
    TreeNode head;
    public void flatten(TreeNode root) {
        if (root == null) return;
        flatten(root.right);
        flatten(root.left);
        root.left = null;
        root.right = head;
        head = root;
    }
//中序+前序构建二叉树

    public TreeNode buildTree( int[] preorder, int[] inorder) {
        if (preorder.length==0 || inorder.length==0) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        int inRootIndex = 0;
        for (int i = 0; i <= inorder.length; i++) {
            if (inorder[i] == preorder[0]) {
                inRootIndex = i;
                break;
            }
        }
        int [] leftPreOrder = Arrays.copyOfRange(preorder, 1, inRootIndex + 1);
        int [] leftInOrder = Arrays.copyOfRange(inorder, 0, inRootIndex );
        int [] rightPreOrder = Arrays.copyOfRange(preorder, inRootIndex + 1, preorder.length);
        int [] rightInOrder = Arrays.copyOfRange(inorder, inRootIndex + 1, inorder.length);
        root.left = buildTree(leftPreOrder, leftInOrder);
        root.right = buildTree(rightPreOrder,rightInOrder);
        return root;
    }

//    树的路径总和3
    int totalCount = 0;
    Map<Long,Integer> map = new HashMap<>();
    public int pathSum(TreeNode root, int targetSum) {
        map.put(0L,1);
        dfsSum(root,targetSum,0L);
        return totalCount;
    }

    public void dfsSum(TreeNode root, int targetSum,Long sum){
        if (root == null) return ;
        sum+=root.val;
        if (map.containsKey(sum - targetSum)) {
            totalCount += map.get(sum - targetSum);
        }
        map.put(sum,map.getOrDefault(sum,0)+1);
        dfsSum(root.left,targetSum,sum);
        dfsSum(root.right,targetSum,sum);
        map.put(sum,map.get(sum)-1);
    }
//二叉树的最近公共祖先-方法一：路径法
    public TreeNode lowestCommonAncestor01(TreeNode root, TreeNode p, TreeNode q) {
        List <TreeNode> pathP = new LinkedList<>();
        List <TreeNode> pathQ = new LinkedList<>();
        dfsPath( root, p,pathP);
        dfsPath( root, q,pathQ);
        int length = Math.min(pathP.size(), pathQ.size());
        for (int i = 0; i < length ; i++) {
            if (pathP.get(i) != pathQ.get(i)) {
                return pathP.get(i-1);
            }
        }
        return pathP.get(length-1);
    }

    public void dfsPath(TreeNode root, TreeNode p,List<TreeNode> path){
        if (root == null) return ;
        path.add(root);
        if (root == p) return ;
        dfsPath(root.left,p,path);
        dfsPath(root.right,p,path);
        if (path.getLast() != p) {
            path.removeLast();//回溯
        }
    }
    //二叉树的最近公共祖先-方法二：递归法
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
       if (root == null||root == p||root==q) return root;
       TreeNode left = lowestCommonAncestor(root.left,p,q);
       TreeNode right = lowestCommonAncestor(root.right,p,q);
       if (left == null) return right;
       if (right == null) return left;
       return root;
    }
//二叉树的最大路径和
    Integer ansMaxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        dfsMaxPathSum(root);
        return ansMaxSum;
    }
    public int dfsMaxPathSum(TreeNode root) {
        if (root == null) return 0;
        int left =  dfsMaxPathSum(root.left);
        int right = dfsMaxPathSum(root.right);
        ansMaxSum = Math.max(left+right+root.val,ansMaxSum);
        return Math.max(Math.max(left,right)+root.val,0);
    }

    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left<=right){
            int mid = left + (right-left) /2;
            if (nums[mid] == target) {
                return mid;
            }else  if (nums[mid] < target) {
                left = mid + 1;
            }else  {
                right = mid - 1;
            }
        }
        return left;
    }










    // 添加main函数用于调试
    public static void main(String[] args) {
        Solution solution = new Solution();

        // 测试数据
        int[] nums = {1,-1,0};
        String s = "ADOBECODEBANC";
        String p = "ABC";
        // 调用并测试算法
        solution.minWindow(s,p);


    }





}
