package list;

public class ListSolution {
    public int removeElement(int[] nums, int val) {
        int first = 0;
        int last = 0;
        while (last < nums.length) {
            if (nums[last] != val) {
                nums[first] = nums[last];
                first++;
            }
            last++;
        }
        return first; // ✅ 正确返回新长度
    }
    public int[] sortedSquares(int[] nums) {
        int [] result = new int[nums.length];
        int index = result.length-1;
        int first = 0;
        int last = nums.length-1;
        while(first <= last){
            if(Math.abs(nums[first]) < Math.abs(nums[last])){
                result[index--] = nums[last] * nums[last];
                last--;
            }else {
                result[index--] = nums[first] * nums[first];
                first++;
            }
        }
        return result;
    }

    public int minSubArrayLen(int target, int[] nums) {
        int first = 0;
        int last = 0;
        int count = Integer.MAX_VALUE;
        int sum = 0;
        for (;last< nums.length;last++){
            sum+=nums[last];
            while (sum >= target ){
                count = Math.min(count,last-first+1);
                sum -= nums[first++];

            }
        }
        return count==Integer.MAX_VALUE ? 0:count ;
    }

    public int numberOfPairs(int[][] points) {
     int count = 0;


     return  count;
    }



    public static void main(String[] args) { // ✅ 添加 static
        int[] nums = {-4,-1,0,3,10};

        ListSolution sol = new ListSolution();
        int[] res = sol.sortedSquares(nums);
        for (int i = 0; i < res.length; i++) { // ✅ 只打印有效部分
            System.out.println(res[i]);
        }
    }
}