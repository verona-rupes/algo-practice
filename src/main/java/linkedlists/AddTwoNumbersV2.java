package linkedlists;

//https://leetcode.com/problems/add-two-numbers/

//[3,2,9,7,3,5,7,2,8,1]
//[5,7,9,8,9,2,7,4,7]

public class AddTwoNumbersV2 {

  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

    int carry = 0;
    ListNode head = null;
    // String sumStr = "";
    while (l1 != null || l2 != null || carry > 0) {
      // System.out.println((l1head != null ? l1head.val : 0) + "+" + (l2head != null
      // ? l2head.val : 0) + "+" + carry);
      int sum = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + carry;
      if (sum >= 10) {
        sum = (sum - 10);
        carry = 1;
      } else {
        carry = 0;
      }
      // sumStr = sum + sumStr;
      if (l1 != null) {
        l1 = l1.next;
      }
      if (l2 != null) {
        l2 = l2.next;
      }

      if (head == null) {
        head = new ListNode(sum);
        continue;
      }

      ListNode current = head;
      while (current != null && current.next != null) {
        current = current.next;
      }

      ListNode newNode = new ListNode(sum);
      current.next = newNode;
    }
    // System.out.println(sumStr);
    return head;
  }

  public static void main(String args[]) {

    ListNode l13 = new ListNode(3);
    ListNode l12 = new ListNode(4);
    l13.next = l12;
    ListNode l1 = new ListNode(2);
    l12.next = l1;
    ///
    ListNode l23 = new ListNode(4);
    ListNode l22 = new ListNode(6);
    l23.next = l22;
    ListNode l2 = new ListNode(5);
    l22.next = l2;

    ListNode sum = addTwoNumbers(l13, l23);

  }

  // 3,2,9,7,3,5,7,2,8,1
  private static ListNode getL1Node() {
    ListNode l_3 = new ListNode(3);
    ListNode l_2 = new ListNode(2);
    l_3.next = l_2;
    ListNode l_9 = new ListNode(9);
    l_2.next = l_9;
    ListNode l_7 = new ListNode(7);
    l_9.next = l_7;
    ListNode l_33 = new ListNode(3);
    l_7.next = l_33;
    ListNode l_5 = new ListNode(5);
    l_33.next = l_5;
    ListNode l_77 = new ListNode(7);
    l_5.next = l_77;
    ListNode l_22 = new ListNode(2);
    l_77.next = l_22;
    ListNode l_8 = new ListNode(8);
    l_22.next = l_8;
    ListNode l_1 = new ListNode(1);
    l_8.next = l_1;
    return l_3;
  }

  // 5,7,9,8,9,2,7,4,7
  private static ListNode getL2Node() {
    ListNode l_5 = new ListNode(5);
    ListNode l_7 = new ListNode(7);
    l_5.next = l_7;
    ListNode l_9 = new ListNode(9);
    l_7.next = l_9;
    ListNode l_8 = new ListNode(8);
    l_9.next = l_8;
    ListNode l_99 = new ListNode(9);
    l_8.next = l_99;
    ListNode l_2 = new ListNode(2);
    l_99.next = l_2;
    ListNode l_77 = new ListNode(7);
    l_2.next = l_77;
    ListNode l_4 = new ListNode(4);
    l_77.next = l_4;
    ListNode l_777 = new ListNode(7);
    l_4.next = l_777;
    return l_5;
  }
}