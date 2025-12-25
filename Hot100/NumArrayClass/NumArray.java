package Hot100.NumArrayClass;
//leecode303.区域和检索：计算前缀和的模版实现

public class NumArray {
    private final int [] s;
    public NumArray(int [] nums) {
        s = new int [nums.length+1];
        for (int i = 0; i <nums.length; i++) {
            s[i+1] =s[i] + nums[i];//默认定义了s【0】为0
        }
    }
    public int sumRange(int left, int right) {
        return s[right+1]-s[left];
    }
}
