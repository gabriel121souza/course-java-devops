package br.com.spacegtech;

import br.com.spacegtech.math.SimpleMath;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Test Math Operations in SimpleMath Class")
class SimpleMathTest {
    private static final Double FIRST_NUMBER = 6.2D;
    private static final Double SECOND_NUMBER = 2D;
    private static final Double THIRD_NUMBER = 81D;

    @BeforeAll
    static void setup(){
        System.out.println("Running @BefofeAll method!");
    }

    @AfterAll
    static void cleanup(){
        System.out.println("Running @AfterAll method!");
    }

    @Test
    @DisplayName("Test 6.2 + 2 = 8.2")
    void testSum_when_SixDotTwoIsAddedByTwo_ShoudReturnEightDotTwo(){
        SimpleMath math = new SimpleMath();
        Double result = math.sum(FIRST_NUMBER, SECOND_NUMBER);
        double expected = 8.2D;
        assertEquals(expected, result, () -> "6.2+2 did not produce 8.2");
    }
    @Test
    @DisplayName("Test 6.2 - 2 = 4.2")
    void testSubtraction(){
        SimpleMath math = new SimpleMath();
        Double result = math.subtraction(FIRST_NUMBER, SECOND_NUMBER);
        double expected = 4.2D;
        assertEquals(expected, result, () -> "6.2-2 did not produce 4.2");
    }
    @Test
    @DisplayName("Test 6.2 * 2 = 12.4")
    void testMultiplication(){
        SimpleMath math = new SimpleMath();
        Double result = math.multiplication(FIRST_NUMBER, SECOND_NUMBER);
        double expected = 12.4D;
        assertEquals(expected, result, () -> "6.2*2 did not produce 12.4");
    }
    @Test
    @DisplayName("Test 6.2 / 2 = 3.1")
    void testDivision(){
        SimpleMath math = new SimpleMath();
        Double result = math.division(FIRST_NUMBER, SECOND_NUMBER);
        double expected = 3.1D;
        assertEquals(expected, result, () -> "6.2/2 did not produce 3.1");
    }
    @Disabled("TODO: We need still work on it!")
    @Test
    @DisplayName("Test Square Root of 81 = 9")
    void testSquareRoot(){
        SimpleMath math = new SimpleMath();
        Double result = math.squareRoot(THIRD_NUMBER);
        double expected = 9D;
        assertEquals(expected, result, () -> " 81^ did not produce 3.1");
    }

    @Test
    @DisplayName("Test Division By Zero")
    void testDivision_when_FirstNumberIsDividedByZero_ShouldThrowArithmeticException(){
        double firstNumber = 6.2D;
        double secondNumber = 0D;
        SimpleMath math = new SimpleMath();
        var expectedMessage = "Impossible to divide by zero!";

       ArithmeticException actual = assertThrows(
               ArithmeticException.class, () -> {
            math.division(firstNumber, secondNumber);
        });
       assertEquals(expectedMessage, actual.getMessage(), () -> "Unexpected exception message!");
    }

}
