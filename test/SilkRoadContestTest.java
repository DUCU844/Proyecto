package test;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import silkRoad.SilkRoadContest;
import silkRoad.SilkRoadException;

/**
 * Pruebas unitarias para SilkRoadContest
 * 
 * @author Beltrán-Ducuara
 * @version 2025-2
 */
public class SilkRoadContestTest {
    private SilkRoadContest contest;
    
    @BeforeEach
    public void setUp() {
        contest = new SilkRoadContest();
    }
    
    @AfterEach
    public void tearDown() {
        contest = null;
    }
    
    /**
     * Test: Solución con array vacío
     */
    @Test
    public void shouldReturnZeroForEmptyArray() {
        int[] utilidades = {};
        assertEquals(0, contest.solve(utilidades));
    }
    
    /**
     * Test: Solución con array null
     */
    @Test
    public void shouldReturnZeroForNullArray() {
        assertEquals(0, contest.solve(null));
    }
    
    /**
     * Test: Solución con utilidades positivas
     */
    @Test
    public void shouldCalculateCorrectMaxProfit() {
        int[] utilidades = {100, 150, 200, 120};
        assertEquals(570, contest.solve(utilidades));
    }
    
    /**
     * Test: Solución con un solo elemento
     */
    @Test
    public void shouldHandleSingleElement() {
        int[] utilidades = {250};
        assertEquals(250, contest.solve(utilidades));
    }
    
    /**
     * Test: Solución con todas utilidades iguales
     */
    @Test
    public void shouldHandleEqualValues() {
        int[] utilidades = {100, 100, 100, 100};
        assertEquals(400, contest.solve(utilidades));
    }
    
    /**
     * Test: Simulate no lanza excepciones
     */
    @Test
    public void shouldSimulateWithoutErrors() {
        int[] utilidades = {100, 150, 200};
        try {
            contest.simulate(utilidades);
            assertTrue(true); // Si llega aquí, no hubo error
        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}
