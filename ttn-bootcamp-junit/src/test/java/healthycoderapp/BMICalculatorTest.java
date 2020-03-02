package healthycoderapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

     @BeforeAll
    static void beforeAll()
    {
        System.out.println("Before all unit test cases...");
    }

    @AfterAll
    static void afterAll()
    {
        System.out.println("After all unit test cases...");
    }

  @Test
    void should_ThrowsException_When_Height_isZero() {


        //given
        double weight = 50.0;
        double height = 0.0;

        //when
//        boolean recommended= BMICalculator.isDietRecommended(weight,height);

        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        //then
        assertThrows(ArithmeticException.class, executable);
    }


    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
        List<Coder> coders = new ArrayList<>();
        //given
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));

//when
        Coder coderWorstBmi = BMICalculator.findCoderWithWorstBMI(coders);
//then
        assertAll(

                () -> assertEquals(1.82, coderWorstBmi.getHeight()),
                () -> assertEquals(98.0, coderWorstBmi.getWeight())
        );


    }

    @Test
    void should_ReturnNull_When_CoderListIsEmpty() {

        //given
        List<Coder> coder = new ArrayList<>();

        //when
        Coder coder1 = BMICalculator.findCoderWithWorstBMI(coder);

        //then
        assertNull(coder1);

    }


    @Test
    void should_ReturnCorrectBMIScore_When_CoderListNotEmpty() {


        //given
        List<Coder> coders1 = new ArrayList<>();
        coders1.add(new Coder(1.80, 60.0));
        coders1.add(new Coder(1.82, 98.0));
        coders1.add(new Coder(1.82, 64.7));

        double[] expected = {18.52, 29.59,19.53};

       //when
        double[] bmiScores = BMICalculator.getBMIScores(coders1);

        //then
        assertArrayEquals(expected, bmiScores);

    }

  @Nested
class GetBMIScoreTests{

    @Test
    void should_ReturnCodderWithWorstBMi_when_CoderListHas1000Elememts()
    {
        //given
        List<Coder> coders2= new ArrayList<>();
        for(int i=0;i<1000;i++)
        {
            coders2.add(new Coder(1.0+i,10.0+i));
        }
        //when
        Executable executable=()->BMICalculator.findCoderWithWorstBMI(coders2);

        //then
        assertTimeout(Duration.ofMillis(6),executable);
    }

}

}