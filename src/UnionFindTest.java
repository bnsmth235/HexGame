import org.junit.Assert;

public class UnionFindTest {

    public void testSetup(UnionFind uf){
        for(int i = 0; i < 10; i++){
            //should be 0-9 in order
            Assert.assertEquals(i, uf.noCompressionFind(i));
        }
    }

    public void testUnion0(UnionFind uf){
        //1 is now connected to 0
        Assert.assertEquals(-2, uf.getVal(0));
        Assert.assertEquals(0, uf.getVal(1));
    }

    public void testUnion1(UnionFind uf){
        //3 should now be connected to 2
        Assert.assertEquals(-2, uf.getVal(2));
        Assert.assertEquals(2, uf.getVal(3));
    }

    public void testUnion2(UnionFind uf){
        //2 is root with height of 3, 0 and 3 are connected to 2, and 1 is connected to 0
        Assert.assertEquals(0, uf.getVal(1));
        Assert.assertEquals(2, uf.getVal(0));
        Assert.assertEquals(2, uf.getVal(3));
        Assert.assertEquals(-3, uf.getVal(2));
    }

    public void testUnion3(UnionFind uf){
        //4 will be a root to new subgroup with a height of 2, with 5,6,7,8 connected to it.
        Assert.assertEquals(4, uf.getVal(5));
        Assert.assertEquals(-2, uf.getVal(4));
        Assert.assertEquals(4, uf.getVal(6));
        Assert.assertEquals(4, uf.getVal(7));
        Assert.assertEquals(4, uf.getVal(8));
    }

    public void testUnion4(UnionFind uf){
        //2 will continue to be the root, 4, will be connected to 2, other nodes will follow
        Assert.assertEquals(-3, uf.getVal(2));
        Assert.assertEquals(2, uf.getVal(4));
    }

    @org.junit.Test
    public void main(){
        UnionFind uf = new UnionFind(10);

        //test if set up correctly
        testSetup(uf);
        //union 0 and 1
        uf.union(0,1);
        testUnion0(uf);
        System.out.println("After 0 and 1 union");
        uf.printAll();

        uf.union(2,3);
        testUnion1(uf);
        System.out.println("\n-------------------\nAfter 2 and 3 union");
        uf.printAll();

        uf.union(3, 1);
        testUnion2(uf);
        System.out.println("\n-------------------\nAfter 3 and 1 union");
        uf.printAll();

        uf.union(4,5);
        uf.union(4,6);
        uf.union(4,7);
        uf.union(4,8);
        testUnion3(uf);
        System.out.println("\n-------------------\nAfter 4 and 5,6,7,8 union");
        uf.printAll();

        //try to union something already unioned
        uf.union(4,8);
        testUnion3(uf);
        System.out.println("\n-------------------\nAfter 4 and 8 union (should be same)");
        uf.printAll();

        //right now we have 3 subgroups. Joining the two larger
        uf.union(3,4);
        //even though we're joining lower groups, it will join the roots.
        testUnion4(uf);
        System.out.println("\n-------------------\nAfter 3 and 4 union");
        uf.printAll();

        //although this is only with an array of length 10, size doesn't matter, all of the properties will work with any int array.








    }
}
