package Hot100;

class Trie {
    private static class  Node {//二十六叉树
        Node[] children = new Node[26];
        boolean isEnd;
    }
    private final Node root = new Node();
    public Trie() {

    }

    public void insert(String word) {
        char[] chars = word.toCharArray();
        Node cur  = root;
        for (char c:chars){
            int index = c - 'a';
            if(cur.children[index] == null){
                cur.children[index] = new Node();
                cur = cur.children[index];
            }else {
                cur = cur.children[index];
            }
        }
        cur.isEnd = true;
    }

    public int find(String word) {
        char[] chars = word.toCharArray();
        Node cur  = root;
        for (char c:chars){
            int index = c - 'a';
            if(cur.children[index] == null){
                return -1;
            }else {
                cur = cur.children[index];
            }
        }
        return cur.isEnd ? 2:1;
    }
    public boolean search(String word) {
        return find(word)==2;
    }

    public boolean startsWith(String prefix) {
        return find(prefix)>0?true:false;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */