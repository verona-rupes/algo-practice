package favorites;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * https://leetcode.com/problems/lru-cache/
 *
 * @author ven.ayya
 */
public class LRUCache extends HashMap<Integer, Integer> {

  private int MAX;

  private ArrayList<Integer> list = new ArrayList<Integer>();


  public LRUCache(int i) {
    this.MAX = i;
  }

  @Override
  public Integer put(Integer key, Integer value) {
    System.out.println("size put:" + list.size());
    //System.out.println( key+":"+value);
    if (!this.containsKey(key) && list.size() >= MAX) {
      int first = list.remove(0);
      super.remove(first);
    }

    super.put(key, value);
    list.remove((Integer) key);
    list.add((Integer) key);
    return -1;

  }


  @Override
  public Integer get(Object key) {
    System.out.println("size get:" + list.size());
    Integer val = super.get(key) == null ? -1 : super.get(key);
    if (val != -1) {
      list.remove((Object) key);
      list.add((Integer) key);

    }

    return val;
  }

  public static void main(String args[]) {
    LRUCache cache = new LRUCache(5);
    /**
     cache.put(1, 1);
     cache.put(2, 2);
     System.out.println(cache.get(1));// returns 1
     cache.put(3, 3); // evicts key 2
     System.out.println(cache.get(2)); // returns -1 (not found)
     cache.put(4, 4); // evicts key 1
     System.out.println(cache.get(1)); // returns -1 (not found)
     System.out.println(cache.get(3)); // returns 3
     System.out.println(cache.get(4)); // returns 4
     **/

    /**
     cache.put(1, 1);
     cache.put(2, 2);
     System.out.println(cache.get(1));
     cache.put(3, 3);
     System.out.println(cache.get(2));
     cache.put(4, 4);
     System.out.println(cache.get(1));
     System.out.println(cache.get(3));
     System.out.println(cache.get(4));
     **/

    /**
     cache.put(2, 1);
     cache.put(1, 1);
     cache.put(2, 3);
     cache.put(4, 1);
     System.out.println(cache.get(1));
     System.out.println(cache.get(2));
     **/

    /**
     System.out.println(cache.get(2)); //-1
     cache.put(2, 6);
     cache.put(3, 6);
     cache.put(4, 6);
     cache.put(5, 6);
     cache.put(2, 9); //3,4,5,2
     System.out.println(cache.get(1)); //-1
     cache.put(1, 5); //evicts 3, inserts 1
     cache.put(1, 2); //overwrites 1
     System.out.println(cache.get(9));
     System.out.println(cache.get(3));
     System.out.println(cache.get(4));
     System.out.println(cache.get(5));
     System.out.println(cache.get(1));
     System.out.println(cache.get(2));
     **/
  }

}
