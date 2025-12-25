package Hot100;

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
