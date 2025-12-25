package backtrace;

import java.util.ArrayList;
import java.util.List;

public class backTracking {

    List<List<Integer>> result = new ArrayList<>();
    static List<Integer>  current = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {

    backtrack(1, n, k);
    return result;
    }

    public void backtrack(int start, int n, int k) {
        //终止条件是当前数量等于k
        if(current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i <= n-(k-current.size())+1; i++) {
            current.add(i);
            backtrack(i + 1, n, k);
            current.remove(current.size() - 1);
        }
    }


    public List<List<Integer>> combinationSum3(int k, int n) {
        combianationBacktrack(k, n, 1);
        return result;
    }
    int sum = 0;
    public void combianationBacktrack(int k, int n, int start) {

        if (current.size() == k && sum == n) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (current.size()==k){
            return;
        }

        for (int i = start; i <=9-(k-current.size())+1; i++) {
            sum += i;
            current.add(i);
            if (sum > n) {
                sum -= i;
                current.remove(current.size() - 1);
                return;
            }
            combianationBacktrack(k, n, i+1);
            current.remove(current.size() - 1);
            sum -= i;
        }
    }


    public List<String> letterRes = new ArrayList<>();
    public StringBuilder currentStr = new StringBuilder();
    public String [] letterMapping = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    public List<String> letterCombinations(String digits) {
        letterBacktrack(digits, 0);
        return letterRes;
    }

    public void letterBacktrack(String digits, int start) {
    // 如果当前生成的字符串长度等于输入数字字符串长度
    // 说明已经完成一个字母组合的生成
        if(currentStr.length() == digits.length()) {
        // 将生成的字母组合添加到结果列表中
            letterRes.add(currentStr.toString());
        // 返回上一层递归
            return;
        }
            int digit = digits.charAt(start)-'0';
            String str = letterMapping[digit];
            for(int j = 0; j < str.length(); j++) {
                currentStr.append(str.charAt(j));
                letterBacktrack(digits, start+1);
                currentStr.deleteCharAt(currentStr.length()-1);
            }
    }

}
