package Everyday;

import com.sun.source.tree.Tree;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Solution {
    public int[] sumZero(int n) {
        int[] result = new int[n];
        int pre = 0;
        int last = n/2;//后半部分的开始，奇数就是中间
        if (n%2!=0){
            result[last++]=0;
        }
            for (int i=1;i<=n/2;i++){
                result[pre++]=i;
                result[last++]=-i;
            }

        return result;
    }
    public int[] getNoZeroIntegers(int n) {
        int base = 1;
        int a = 0;
        for (int rest=n;rest>0;base*=10){
            //每位如果大于1就拆分成1+k-1，否则就高位借1然后拆分成2*这位 + ...
            int lastNum = rest%10; //当前位
            rest/=10;//高一位
            if (lastNum>1){
                a+=base;
            }else if (rest > 0){
                rest--;
                a+=base*2;
            }
        }
        return new int[]{a,n-a};
    }
    // 2  3  4  1  2 4
    public int inventoryManagement(int[] stock) {
        if (stock.length==1){
            return  stock[0];
        }
        int left = 0;
        int right = stock.length-1;

        //left是大于等于right的
        while(left<right){
            int mid = left + (right-left)/2;
            if (stock[mid] >stock[right] ){
                //一定在右边
                left = mid+1;
            }else if (stock[mid] <stock[right] ){
                //最小的一定在左边
                right = mid;
            }else {
                right--;
            }
        }
        return stock[left];
    }
//    学到了可变字符串使用stringbuilder,String 是 不可变的。 可以对字符使用Chartacter的方法进行大小写转换，
//    还可以对StringBuilder进行 转换类型成为字符串或者字符数组。 toString方法返回的其实是地址，如果是完整的字符串需要使用new string
    public String sortVowels(String s) {
        StringBuilder vowstr = new StringBuilder();
        int[] index = new int[s.length()];
        char[] str = s.toCharArray();
        int k = 0;
        for (int i=0;i<str.length;i++) {
            char c = Character.toLowerCase(str[i]);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowstr.append(str[i]);
                index[k++] = i;
            }
        }
        char []vowch = vowstr.toString().toCharArray();
        Arrays.sort(vowch);
        for (int i=0;i<k;i++) {
            str[index[i]] = vowch[i];
        }
        return new String(str);
    }
    public String pathEncryption(String path) {

        char[] chars = path.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i]=='.'){
                chars[i]=' ';
            }
        }
        return new String(chars);
    }

    public boolean doesAliceWin(String s) {
        int count = 0;
        char[] chars = s.toCharArray();
        for (char ch:chars
             ) {
            char lowCh = Character.toLowerCase(ch);
            if (lowCh=='a'||lowCh=='e'||lowCh=='i'||lowCh=='o'||lowCh=='u'){
                count++;
            }
        }
        if (count>0){
            if (count%2==0){
                return true;
            }
            return true;
        }
        return false;
    }

    public String dynamicPassword(String password, int target) {
        StringBuilder str = new StringBuilder(password.length());
        int index = 1;
        for (char ch:password.toCharArray()
             ) {
            if (index<=target){
                str.append(ch);
            }else {
                str.insert(index-target-1,ch);
            }
            index++;
        }
        return new String(str.toString());
    }

    public String dynamicPassword02(String password, int target) {
    String str = password.substring(target)+password.substring(0,target);
    return str;
 }
    public int maxFreqSum(String s) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            int voNum = 0;
            int conNum = 0;
            int cur = 0;
            for (int i=0;i<chars.length-1;i++){
                cur++;
                if(chars[i]!=chars[i+1]){
                    //一样的刚结束
                    if (chars[i]=='a'||chars[i]=='e'||chars[i]=='i'||chars[i]=='o'||chars[i]=='u'){
                        if (voNum < cur){
                            voNum = cur;//元音最大
                        }
                    }else {
                        if (conNum < cur){
                            conNum = cur;//辅音最大
                        }
                    }
                    cur=0;//重置计算器
                }
            }
            //单独的一个
            cur++;
            if (chars[chars.length-1]=='a'||chars[chars.length-1]=='e'
                        ||chars[chars.length-1]=='i'||chars[chars.length-1]=='o'
                        ||chars[chars.length-1]=='u'){
                    voNum = Math.max(voNum, cur);
            }else {
                    conNum = Math.max(conNum, cur);
            }

            return  voNum + conNum;
    }

    public boolean validNumber(String s) {
//        空格 b 符号 s 数字 n  点 .  幂函数 e
        Map[] states = {
                new HashMap<>(Map.of(' ',0,'s',1,'n',2,'.',4)),//0
                new HashMap<>(Map.of('n',2,'.',4)),//1
                new HashMap<>(Map.of('.',3,'e',5,'n',2,' ',8)),//2
                new HashMap<>(Map.of('n',3,' ',8,'e',5)),//3
                new HashMap<>(Map.of('n',3)),//4
                new HashMap<>(Map.of('s',6,'n',7)),//5
                new HashMap<>(Map.of('n',7)),//6
                new HashMap<>(Map.of(' ',8,'n',7)),//7
                new HashMap<>(Map.of(' ',8)),//8
        };

        char[] chars = s.toCharArray();
        char t;
        int p =0;//初始状态
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch=='+'||ch=='-') t = 's';//符号
            else if ((ch >= '0') && (ch <= '9')) t = 'n';//数字
            else if (ch=='E'||ch=='e') t = 'e'; //幂函数
            else if(ch==' '||ch=='.') t=ch;//直接用的符号
            else{ return false;}//非法字符输入
            //开始转移状态
            if (states[p].containsKey(t)){
                p = (int) states[p].get(t);
            }else {
                return false;
            }
        }
        if (p==2||p==3||p==7||p==8){
            return true;//接受的状态
        }
        return false;
    }

    public int canBeTypedWords(String text, String brokenLetters) {
        String[] words = text.split(" +");
        char[] chars = brokenLetters.toCharArray();
        int count = words.length;
        for (String str: words) {
            for (int i = 0; i < chars.length; i++) {
                if (str.indexOf(chars[i])>=0){
                    count--;
                    break;
                }
            }
        }
        return count;
    }


    public List<Integer> replaceNonCoprimes(int[] nums) {
        if (nums.length==1){
            return Arrays.stream(nums)   // IntStream
                    .boxed()        // 变成 Stream<Integer>
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (int i=0;i< nums.length;i++){
            stack.push(Integer.valueOf(nums[i]));
            while (stack.size()>=2){
                Integer a = stack.pop();
                Integer b = stack.pop();
                int gcd = BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue() ;
                if (gcd > 1){
                    Integer lcm = (a/ gcd * b);
                    stack.push(lcm);
                } else {                 // 已互质，恢复顺序并结束本轮
                    stack.push(b);
                    stack.push(a);
                    break;
                }
            }
        }
        // 栈顶是数组尾部，反转后得到正确顺序
        List<Integer> ans = new ArrayList<>(stack);
        Collections.reverse(ans);
        return ans;
    }

    public int maxFrequencyElements(int[] nums) {
        int []frequency = new int[101];
        for (int num : nums) {
            frequency[num]++;
        }
        Arrays.sort(frequency);
        int maxFre = frequency[frequency.length-1];
        int count = 0;
        for (int i = frequency.length-1;i>=0;i--){
            if (frequency[i]!=maxFre){
                break;
            }
            count++;
        }
        return maxFre*count;
    }
    public int compareVersion(String version1, String version2) {
        String[] v1Strs = version1.split(Pattern.quote("."));
        String[] v2Strs = version2.split(Pattern.quote("."));
        int length = Math.max(v1Strs.length,v2Strs.length);
        int[] v1 = new int[length];
        int[] v2 = new int[length];
        for (int i=0;i<length;i++){
            if (i < v1Strs.length ){
                v1[i] = Integer.parseInt(v1Strs[i]);
            }
            if (i < v2Strs.length ){
                v2[i] = Integer.parseInt(v2Strs[i]);
            }
        }
        for (int i=0;i<length;i++){
            if (v1[i]!=v2[i]){
                return v1[i] > v2[i]? 1 : -1 ;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // 基础场景
        System.out.println(s.compareVersion("1.01", "1.001"));     // 0
        System.out.println(s.compareVersion("1.0", "1.0.0"));      // 0
        System.out.println(s.compareVersion("1.2", "1.10"));        // -1
        System.out.println(s.compareVersion("1.0.1", "1"));        // 1
        System.out.println(s.compareVersion("7.5.2.4", "7.5.3"));  // -1

        // 前导零 + 长度不同
        System.out.println(s.compareVersion("0001.0002", "1.2"));  // 0

        // 纯数字版本号
        System.out.println(s.compareVersion("1", "1.0.0.0"));      // 0
        System.out.println(s.compareVersion("2", "10"));           // -1
    }

}

class NumberContainers {
    Map<Integer,Integer>indexMap;
    Map<Integer,TreeSet<Integer>>valueMap;
    public NumberContainers() {
        indexMap = new HashMap<>();
        valueMap = new HashMap<>();
    }

    public void change(int index, int number) {
        Integer integer = indexMap.get(index);
        TreeSet<Integer> numberIndecSet = valueMap.get(number);
        if (integer==null){
            //如果这个下标没有数字，那就新加就行
            indexMap.put(index,number);
            if (numberIndecSet!=null && numberIndecSet.size()!=0){// 下标有数字，数字有下标
                Integer minIndex =  numberIndecSet.first();//插入数字的最小下标
                numberIndecSet.add(Math.min(index, minIndex));
                valueMap.put(number, numberIndecSet);//更新最小的下标。
            }else {
                numberIndecSet = new TreeSet<>();
                numberIndecSet.add(index);
                valueMap.put(number,numberIndecSet);//初始化下标
            }
        }else {//下标有数字 integer 50,25   25,20
            if (integer!=number) {
                //不相等
                TreeSet<Integer> integerIndexes = valueMap.get(integer);
                integerIndexes.remove(index);
                valueMap.put(integer,integerIndexes);//对被覆盖的数字的维护

                indexMap.put(index,number);//直接插入就行
                //对插入的数字的维护
                //如果发现有这个下标是有数字的，首先查这个数字的最小下标是不是这个数字，如果最小下标比这个大就更新valueMap，否则不操作
                if (numberIndecSet!=null && numberIndecSet.size()!=0){//但是这个数字有下标
                        numberIndecSet.add(index);
                        valueMap.put(number, numberIndecSet);
                }else {
                    numberIndecSet = numberIndecSet==null? new TreeSet<Integer> ():numberIndecSet;
                    numberIndecSet.add(index);
                    valueMap.put(number,numberIndecSet);//初始化下标
                }
            }
        }
    }

    public int find(int number) {
        TreeSet<Integer> treeSet = valueMap.get(number);
        if(treeSet==null||treeSet.isEmpty()){
            return -1;
        }
        return treeSet.first();
    }

}


class TaskManager {
    Map<Integer,Set<Integer>>userMap;
    Map<Integer,Map.Entry<Integer,Integer>>taskMap;
    TreeMap<Integer,TreeSet<Integer>>priMap;
    public TaskManager(List<List<Integer>> tasks) {
        userMap = new HashMap<>();
        taskMap = new HashMap<>();
        priMap = new TreeMap<>();
        for (List<Integer> taskList :tasks
             ) {//【1,1,2】
            int userId = taskList.get(0);
            int taskId = taskList.get(1);
            int pri = taskList.get(2);
            userMap.computeIfAbsent(userId,k->new HashSet<>()).add(taskId);
            taskMap.put(taskId, new AbstractMap.SimpleEntry<>(userId, pri));
            priMap.computeIfAbsent(pri, k -> new TreeSet<>()).add(taskId);
        }
    }

    public void add(int userId, int taskId, int priority) {
        userMap.computeIfAbsent(userId,k->new HashSet<>()).add(taskId);
        taskMap.put(taskId, new AbstractMap.SimpleEntry<>(userId, priority));
        priMap.computeIfAbsent(priority, k -> new TreeSet<>()).add(taskId);
    }

    public void edit(int taskId, int newPriority) {
        int oldValue = taskMap.get(taskId).getValue();
        taskMap.get(taskId).setValue(newPriority);
        Set<Integer> set = priMap.get(oldValue);
        if (set!=null){
            set.remove(taskId);
            if (set.isEmpty()){
                priMap.remove(oldValue);
            }
        }
        priMap.computeIfAbsent(newPriority, k -> new TreeSet<>()).add(taskId);
    }

    public void rmv(int taskId) {
        Integer userId = taskMap.get(taskId).getKey();
        Integer pri = taskMap.get(taskId).getValue();
        userMap.get(userId).remove(taskId);
        if (userMap.get(userId).isEmpty()){
            userMap.remove(userId);
        }
        priMap.get(pri).remove(taskId);
        if (priMap.get(pri).isEmpty()){
            priMap.remove(pri);
        }
        taskMap.remove(taskId);
    }

    public int execTop() {
       if (priMap.isEmpty()){
           return -1;
       }
        Integer lastPriKey = priMap.lastKey();
        int taskID = priMap.get(lastPriKey).last();
        //执行唯一的任务
        int userID = taskMap.get(taskID).getKey();
        rmv(taskID);
        return userID;
    }
}

class Spreadsheet {
    List<Map>sheet;
    public Spreadsheet(int rows) {

        sheet = new ArrayList<>(26);
        for (int i = 0; i < 26; i++) {    // 26 个 Map
            Map<Integer, Integer> map = new HashMap<>(rows);
            for (int k = 1; k <=rows; k++) {
                map.put(k, 0);            // 1→0, 2→0, … , x→0
            }
            sheet.add(map);
        }
    }

    public void setCell(String cell, int value) {
        char[] chars = cell.toCharArray();
        int index = chars[0]-'A';
        int key = Integer.parseInt(cell.substring(1));
        sheet.get(index).put(key,value);
    }

    public void resetCell(String cell) {
        char[] chars = cell.toCharArray();
        int index = chars[0]-'A';
        int key =  Integer.parseInt(cell.substring(1));
        sheet.get(index).put(key,0);
    }

    public int getValue(String formula) {
        String[] part = formula.substring(1).split("\\+", 2);
        char[] a = part[0].toCharArray();
        char[] b = part[1].toCharArray();
        int va,vb = 0;
        if ( a[0]>='A' && a[0]<='Z'){
            int index = a[0]-'A';
            int key =  Integer.parseInt(part[0].substring(1));
            va  = (int) sheet.get(index).get(key);
        }else {

            va =  Integer.parseInt(part[0]);
        }
        if (b[0]>='A' && b[0]<='Z'){
            int index = b[0]-'A';
            int key =  Integer.parseInt(part[1].substring(1));
            vb  = (int) sheet.get(index).get(key);
        }else {
            vb =  Integer.parseInt(part[1]);
        }
        return va+vb;
    }
}

class Router {
    LinkedHashSet<String>packets;// [destination,source,time]
    LinkedHashMap<Integer,TreeMap<Integer,Integer>>time;//<destination, <time,x> >
    int limit;
    public Router(int memoryLimit) {
        packets = new LinkedHashSet<>();
        time = new LinkedHashMap<>();
        limit = memoryLimit;

    }
    String encode(int a, int b, int c) {
        return a + "|" + b + "|" + c;   // 管道符只要不在数据里出现即可
    }

    int[] decode(String s) {
        String[] p = s.split("\\|");
        return new int[]{
                Integer.parseInt(p[0]),
                Integer.parseInt(p[1]),
                Integer.parseInt(p[2])
        };
    }
    public boolean addPacket(int source, int destination, int timestamp) {
        //不重复
        String str = encode(source,destination,timestamp);
        if (packets.contains(str)){
            return false;
        }
        if (packets.size()==limit){
            int[] first =  decode(packets.iterator().next());
            Integer times = time.get(first[1]).get(first[2]);
            if (times - 1 <=0){
                time.get(first[1]).remove(first[2]);
            }else {
                time.get(first[1]).put(first[2],times-1);
            }
            packets.remove(packets.iterator().next());
        }
        //新增set
        packets.add(encode(source,destination,timestamp));
        //时间次数维护表
        if (time.get(destination)!=null){
            if (time.get(destination).get(timestamp)!=null){
                //修改次数
                int times = time.get(destination).get(timestamp);
                time.get(destination).put(timestamp,times+1);
            }else {//新增次数
                time.get(destination).put(timestamp,1);
            }
        }else {
            //没有这个d的包
            TreeMap<Integer,Integer> inner = new TreeMap<>();
            inner.put(timestamp, 1);
            time.put(destination, inner);
        }
        return true;
    }

    public int[] forwardPacket() {
        int [] packet = new int[3];
        if (packets.isEmpty()){
            return new int[0];
        }
        int[] first =  decode(packets.iterator().next());
        packets.remove(packets.iterator().next());
        Integer times = time.get(first[1]).get(first[2]);
        if (times - 1 <=0){
            time.get(first[1]).remove(first[2]);
        }else {
            time.get(first[1]).put(first[2],times-1);
        }
        return first;
    }

    public int getCount(int destination, int startTime, int endTime) {
        int count =0;
        NavigableMap<Integer, Integer> integerMap = time.get(destination).subMap(startTime, true, endTime, true);
        for (Map.Entry<Integer, Integer> e : integerMap.entrySet()) {
            count += e.getValue();
        }
        return count;
    }
}

class MovieRentingSystem {
    // Search: movie-> (price,shop)
    // Rent/Drop : (shop,movie) --> price, 借用从没借用的列表，还从借出去的列表，拿到price去维护第三个表方便report和search
    // (price,shop,moive)
    Map<Integer, TreeSet<int[]>> getPriceandShopByMovie;//未借出去的列表
    Map<Long,Integer> shopMoiveToPrice;//借书或者被借书时候寻找价格，这个不需要被改变，只需要初始化然后查询就可以了
    TreeSet<int[]> rentedPriceShopMovie;//借出去的列表

    public MovieRentingSystem(int n, int[][] entries) {
        rentedPriceShopMovie = new TreeSet<>((a,b)->{
            if (a[0]!= b[0]){
                return a[0]-b[0];
            } else if (a[1]!=b[1]) {
                return a[1]-b[1];
            }else {
                return a[2]-b[2];
            }
        });//已借出的表初始为0，但是定义好排序规则
        shopMoiveToPrice = new HashMap<>();
        getPriceandShopByMovie = new HashMap<>();
        for (int i=0;i<entries.length;i++){
            int [] item = entries[i];//[shopi, moviei, pricei]
            int price = item[2];
            int shop = item[0];
            int movie = item[1];
            long key = (long)shop << 32 | movie;
            shopMoiveToPrice.put(key,price);
            getPriceandShopByMovie.computeIfAbsent(movie,k->{
                return new TreeSet<int[]>((a, b)->{
                    if (a[0]!=b[0]){
                        return a[0]-b[0];
                    }else {
                        return a[1]-b[1];
                    }
                });
            }).add(new int[]{price,shop});
        }
    }

    public List<Integer> search(int movie) {
        TreeSet<int[]> treeSet = getPriceandShopByMovie.get(movie);
        List<Integer> res = new ArrayList<>();
        int count = 0;
        if (treeSet != null) {
            for (int[] priceShop : treeSet) {
                if (count >= 5) {//最多拿5个
                    break;
                }
                int shop = priceShop[1];
                res.add(shop);
                count++;
            }
        }
        //没有就返回空列表
        return res;
    }

    public void rent(int shop, int movie) {
        Integer price = shopMoiveToPrice.get((long) shop << 32 | movie);
        getPriceandShopByMovie.get(movie).remove(new int[]{price,shop});
        rentedPriceShopMovie.add(new int[]{price,shop,movie});//提目保证没有被借出去还，这个一定不存在与set
    }

    public void drop(int shop, int movie) {
        Integer price = shopMoiveToPrice.get((long) shop << 32 | movie);
        getPriceandShopByMovie.get(movie).add(new int[]{price,shop});
        rentedPriceShopMovie.remove(new int[]{price,shop,movie});
    }

    public List<List<Integer>> report() {
        int count = 0;
        List<List<Integer>>  res = new ArrayList<>();
       int size = rentedPriceShopMovie.size();
       if (size!=0){
           for (int[] item:rentedPriceShopMovie) {
               if (count >=5){
                   break;
               }
               res.add(List.of(item[1], item[2]));
               count++;
           }
       }
       return  res;

    }
}




