package com.example.blog_test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;


@SpringBootTest
public class test {
//    @ParameterizedTest
//    @MethodSource("stringIntAndListProvider")
//    void testWithMultiArgMethodSource(String str, int num, List<String> list) {
//        assertEquals(5, str.length());
//        assertTrue(num >=1 && num <=2);
//        assertEquals(2, list.size());
//    }
//
//    static Stream<Arguments> stringIntAndListProvider() {
//        return Stream.of(
//                arguments("apple", 1, Arrays.asList("a", "b")),
//                arguments("lemon", 2, Arrays.asList("x", "y"))
//        );
//    }
//@ParameterizedTest
//    @CsvSource({
//            "apple,     ''    ",
//            "banana,        ",
//            "'lemon, lime', 0xF1"
//    })
//    void testWithCsvSource(String fruit, String rank) {
////        assertNotNull(fruit);
////        assertNotEquals(0, rank);
//        System.out.println("friut:"+fruit +" rank:"+rank);
//    }
@ParameterizedTest
@CsvFileSource( files = "src/test/resources/TestSource/UserAcount.csv", numLinesToSkip = 1)
void testWithCsvFileSourceFromClasspath(String name,String password) {
    System.out.println("name:"+name+" password:"+password);
}

}
