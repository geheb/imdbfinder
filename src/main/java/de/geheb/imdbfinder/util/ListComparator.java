package de.geheb.imdbfinder.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListComparator {

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  public static <T extends Comparable<? super T>> boolean equals(List<T> first, List<T> second) {

    if (first == null) return null == second;
    if (second == null) return false;
    if (first.isEmpty() && second.isEmpty()) return true;
    if (first.size() != second.size()) return false;

    if (1 == first.size()) {
      return first.get(0).equals(second.get(0));
    }

    first = new ArrayList<>(first);
    second = new ArrayList<>(second);

    Collections.sort(first);
    Collections.sort(second);

    return first.equals(second);
  }
}