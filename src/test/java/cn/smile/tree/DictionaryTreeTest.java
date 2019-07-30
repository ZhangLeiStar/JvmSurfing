package cn.smile.tree;

import org.junit.Test;

/**
 * Created by zhanglei51 on 2018/10/26.
 */
public class DictionaryTreeTest {

    @Test
    public void find(){
        DictionaryTree dt = new DictionaryTree();

        dt.add("interest");
        dt.add("interesting");
        dt.add("interested");
        dt.add("inside");
        dt.add("insert");
        dt.add("apple");
        dt.add("inter");
        dt.add("interesting");

        dt.print();

        boolean isFind = dt.find("inside");
        System.out.println("find inside : " + isFind);
    }

    @Test
    public void count(){
        DictionaryTree dt = new DictionaryTree();

        dt.add("interest");
        dt.add("interesting");
        dt.add("interested");
        dt.add("inside");
        dt.add("insert");
        dt.add("apple");
        dt.add("inter");
        dt.add("interesting");

        dt.print();

        int count = dt.count("inter");
        System.out.println("count prefix inter : " + count);
    }
}
