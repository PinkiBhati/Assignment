package com.im;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class FirstTest {

    static First first;

    @BeforeAll
    static void creationFirstClassObject() {
        first = new First();
    }

    @AfterAll
    static void afterAll()
    {
        System.out.println("after all");
    }

    @Nested
class ToReplaceSubString{

        @Test
        void should_show_return_true_if_all_the_values_are_right()
        {
            //given
            String originalString = "pinki kumari";
            String substringToBeReplaced = "kumari";
            String newSubstring = "bhati";
            String expected ="pinki bhati";

            //when
            String string = (first.replaceSubString(originalString,substringToBeReplaced,newSubstring));

            //then
            assertEquals(expected,string);
        }
        @Test
        void should_return_originalString_when_given_null()
        {
            String originalString = "pinki";
            String substringToBeReplaced = null;
            String newSubstring = "bhati";
            String expected ="pinki";

            //when
            String string = (first.replaceSubString(originalString,substringToBeReplaced,newSubstring));

            //then
            assertEquals(expected,string);
        }
        @Test
        void should_return_originalString_when_given_null1()
        {
            //given
            String originalString = "pinki";
            String substringToBeReplaced = "abc";
            String newSubstring = null;
            String expected ="pinki";

            //when
            String string = (first.replaceSubString(originalString,substringToBeReplaced,newSubstring));

            //then
            assertEquals(expected,string);
        }
        @Test
        void should_return_true_if_originalStringisEmpty()
        {
            //given
            String originalString = "";
            String substringToBeReplaced = "abc";
            String newSubstring = "def";
            String expected ="";

            //when
            boolean recommended = originalString.isEmpty();

            //then
            assertTrue(recommended);
        }
        @Test
        void should_return_false_if_mainStringDoesNotContainSubstring()
        {
            //given
            String originalString = "pinki";
            String substringToBeReplaced = "def";
            String newSubstring = "yui";
            String expected ="pinki";

            //when
            boolean recommended = originalString.contains(substringToBeReplaced);

            //then
            assertFalse(recommended);
        }

    }


    @Nested
    class ToFilterElements{
        @Test
        void shouldReturnOddElementOnly_When_OddElementExist_AfterFilterEvenElement() {
            //given
            List<Integer> list = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                list.add(i);
            }
            List<Integer> expectedlist = new ArrayList<>();
            expectedlist.add(1);
            expectedlist.add(3);
            expectedlist.add(5);
            expectedlist.add(7);
            expectedlist.add(9);

            //when
            List calculatelist = first.filterEvenElements(list);

            //then
            assertEquals(expectedlist, calculatelist);

        }
    }

    @Nested
    class IsPalindrome{
        @Test
        void shouldReturnTrue_When_StringIsNotpalindrome() {
            //given
            String originalinput = "pinki";

            //when
            boolean palindromecheck = first.isPallindrome(originalinput);

            //then
            assertFalse(palindromecheck);

        }
        @Test
        void shouldReturnTrue_When_StringIspalindrome() {
            //given
            String originalinput = "naman";

            //when
            boolean palindromecheck = first.isPallindrome(originalinput);

            //then
            assertTrue(palindromecheck);
        }

    }

    @Nested
    class ToCalCulateAverage{
        @Test
        void shouldThrowMessageInvalidInput_When_ListIsEmpty() {
            //given
            List<BigDecimal> list = new ArrayList<>();

            //when
            try {
                first.calculateAverage(list);
            }

            //then
            catch (RuntimeException r) {
                System.out.println(r);

            }
        }
        @Test
        void shouldReturnAveragevalue_When_ListContainsElement() {

            //given
            List<BigDecimal> list = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                list.add(new BigDecimal(1212121));
                list.add(new BigDecimal(1212121));
                list.add(new BigDecimal(1212121));
                list.add(new BigDecimal(1212121));
            }
            BigDecimal expectedaverage = new BigDecimal(1212121);

            //when
            BigDecimal calculateaverage = first.calculateAverage(list);

            //then
            assertEquals(expectedaverage, calculateaverage);
        }
        @Test
        void shouldThrowMessageInvalidInput_When_ListIsNotExist() {
            //given
            List<BigDecimal> list = null;

            //when
            try {
                first.calculateAverage(list);
            }
            //then
            catch (RuntimeException r) {
                System.out.println(r);

            }
        }
    }

}
