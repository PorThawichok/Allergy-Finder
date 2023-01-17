package com.fluke.allergyfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fluke.allergyfinder.Product.ProductAddActivity;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;




/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProductAddTest {

    ProductAddActivity checkTest ;


    @BeforeEach
    void setUp() {
        checkTest = new ProductAddActivity();
    }

    @Test
    public void checkBarcode() {

        assertEquals(   false, checkTest.checkBarcode("8851959132012"));
        assertEquals(true, checkTest.checkBarcode(""));
    }
    @Test
    public void checkCalories() {

        assertEquals(   false, checkTest.checkCalories("140"));
        assertEquals(true, checkTest.checkCalories(""));
    }
    @Test
    public void checkFat() {

        assertEquals(   false, checkTest.checkFat("0"));
        assertEquals(true, checkTest.checkFat(""));
    }
    @Test
    public void checkSugar() {

        assertEquals(   false, checkTest.checkSugar("34"));
        assertEquals(true, checkTest.checkSugar(""));
    }
    @Test
    public void checkProtein() {

        assertEquals(   false, checkTest.checkProtein("0"));
        assertEquals(true, checkTest.checkProtein(""));
    }
    @Test
    public void checkCarbohydrate() {

        assertEquals(   false, checkTest.checkCarbohydrate("34"));
        assertEquals(true, checkTest.checkCarbohydrate(""));
    }
    @Test
    public void checkSodium() {

        assertEquals(   false, checkTest.checkSodium("20"));
        assertEquals(true, checkTest.checkSodium(""));
    }

}