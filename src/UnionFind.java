public class UnionFind {
    private int [] set;

    public UnionFind(int size){
        set = new int[size];

        for(int i = 0; i < set.length; i++){
            set[i] = -1;
        }
    }

    //find with path compression
    public int find(int item){
        if(set[item] < 0){
            return item;
        }else {
            return set[item] = find(set[item]);
        }
    }

    private boolean isUnioned(int item1, int item2){
        if(getRoot(item1) == getRoot(item2)){
            return true;
        }
        return false;
    }

    private int getRoot(int item){
        if(set[item] > 0){
            return getRoot(set[item]);
        }
        return item;
    }

    //union by height
    public void union(int item1, int item2){
        if(!isUnioned(item1, item2)) {
            //if item1 is taller than item2
            int root1 = find(item1);
            int root2 = find(item2);
            if(root1 == root2){
                return;
            }
            if(set[root2] < set[root1]){
                set[root1] = root2;
            }else{
                if(set[root1] == set[root2]){
                    set[root1]--;
                }
                set[root2] = root1;
            }
        }
    }

    //for testing purposes
    public int noCompressionFind(int item){
        if(set[item] < 0){
            return item;
        }else{
            return noCompressionFind(set[item]);
        }
    }

    public void printAll(){
        for(int i = 0; i < set.length; i++){
            System.out.println(i + "\t\t" + noCompressionFind(i) + "\t" + set[i]);
        }
    }

    public int getVal(int item){
        return set[item];
    }

}
