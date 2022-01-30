package linkedlists;

//https://leetcode.com/problems/add-two-numbers/

//1000000000000000000000000000001

/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode(int
 * x) { val = x; } }
 */

class ListNode {

  int val;
  ListNode next;

  ListNode(int x) {
    val = x;
  }
}

public class AddTwoNumbers {

  static ListNode head = null;

  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

    String sum = String.valueOf(getInt(l1) + getInt(l2));

    for (int i = sum.length() - 1; i >= 0; i--) {
      String sub = sum.substring(i, i + 1);
      System.out.println("sub:" + sub);
      add(sub);
    }
    return head;
  }

  private static void add(String s) {
    ListNode newNode = new ListNode(Integer.valueOf(s));

    if (head == null) {
      head = newNode;
      return;
    }

    ListNode current = head;
    while (current.next != null) {
      current = current.next;
    }
    current.next = newNode;
  }

  private static int getInt(ListNode l) {
    ListNode next = l;
    String s = "";
    do {
      s = next.val + s;
      next = next.next;
    } while (next != null);

    return Integer.valueOf(s);

  }

  public static void main(String args[]) {
    ListNode l13 = new ListNode(3);
    ListNode l12 = new ListNode(4);
    l12.next = l13;
    ListNode l1 = new ListNode(2);
    l1.next = l12;
    ///
    ListNode l23 = new ListNode(4);
    ListNode l22 = new ListNode(6);
    l22.next = l23;
    ListNode l2 = new ListNode(5);
    l2.next = l22;

    ListNode sum = addTwoNumbers(l1, l2);
    System.out.println(sum);
  }
}