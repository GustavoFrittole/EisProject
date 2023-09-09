package it.unipd.dei.eis.analyze;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test di equals e compareTo della classe WeightedToken")
class WeightedTokenTest {
    static WeightedToken weightedToken;
    @BeforeAll
    static void setUp() {
        weightedToken = new WeightedToken("word", 2);
    }

    @ParameterizedTest
    @MethodSource("weightedBiggerTokensList")
    void compareToBiggerTest(WeightedToken otherWT) {
        Assertions.assertTrue(weightedToken.compareTo(otherWT) < 0);
    }
    @ParameterizedTest
    @MethodSource("weightedSmallerTokensList")
    void compareToSmallerTest(WeightedToken otherWT) {
        Assertions.assertFalse(weightedToken.compareTo(otherWT) < 0);
    }

    @ParameterizedTest
    @MethodSource("weightedTokensList")
    void notEqualsTest(WeightedToken otherWT) {
        assertNotEquals(weightedToken, otherWT);
    }

    @Test
    void equalsTest() {
        assertEquals(weightedToken, new WeightedToken("word", 2));
    }

    private static List<WeightedToken> weightedBiggerTokensList (){
        return Arrays.asList(new WeightedToken("word", 3), new WeightedToken("worf", 2));
    }

    private static List<WeightedToken> weightedSmallerTokensList (){
        return Arrays.asList(new WeightedToken("word", 1), new WeightedToken("worb", 2));
    }

    private static List<WeightedToken> weightedTokensList (){
        return Arrays.asList(new WeightedToken("word", 1), new WeightedToken("worb", 2),
                new WeightedToken("word", 3), new WeightedToken("worf", 2));
    }
}