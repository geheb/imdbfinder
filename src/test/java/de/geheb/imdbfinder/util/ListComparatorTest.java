package de.geheb.imdbfinder.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class ListComparatorTest {

  @ParameterizedTest
  @MethodSource("createTestData")
  void canEquals(boolean expectedResult, List<String> first, List<String> second) {
    assertEquals(expectedResult, ListComparator.equals(first, second));
  }

  private static Stream<Arguments> createTestData() {
    var list = Collections.singletonList("1");
    return Stream.of(
            Arguments.of(true, null, null),
            Arguments.of(true, new ArrayList<>(), new ArrayList<>()),
            Arguments.of(true, list, list),
            Arguments.of(true, Collections.singletonList("1"), Collections.singletonList("1")),
            Arguments.of(true, Arrays.asList("1", "2"), Arrays.asList("2", "1")),
            Arguments.of(true, Arrays.asList("ä", "€"), Arrays.asList("€", "ä")),
            Arguments.of(false, null, new ArrayList<>()),
            Arguments.of(false, new ArrayList<>(), null),
            Arguments.of(false, Collections.singletonList("1"), new ArrayList<>()),
            Arguments.of(false, Collections.singletonList("1"), null),
            Arguments.of(false, null, Collections.singletonList("1")),
            Arguments.of(false, new ArrayList<>(), Collections.singletonList("1")),
            Arguments.of(false, Arrays.asList("1", "2"), Arrays.asList("2", "3")));
  }
}