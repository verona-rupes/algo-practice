package core;

import java.util.Spliterator;
import java.util.stream.Stream;

public class SplitIterator {
  public static void main (String args[]){
    Stream<String> stringStream=Stream.of("ee","bb","ff","tt");
    Spliterator<String> firstSplit=stringStream.spliterator().trySplit();
  }
}
