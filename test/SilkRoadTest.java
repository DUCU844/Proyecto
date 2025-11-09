package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import silkRoad.SilkRoad;
import silkRoad.SilkRoadException;

/**
 * Pruebas unitarias para la clase SilkRoad
 * 
 * @author Beltrán-Ducuara
 * @version 2025-2
 */
public class SilkRoadTest {
    private SilkRoad silkRoad;
    
    @BeforeEach
    public void setUp() {
        silkRoad = new SilkRoad(1000); // Inicializa con longitud
    }
    
    @AfterEach
    public void tearDown() {
        if (silkRoad != null) {
            silkRoad.finish();
        }
        silkRoad = null;
    }
    
    /**
     * Test: Agregar tiendas y robots válidos
     */
    @Test
    public void shouldAddStoresAndRobotsSuccessfully() {
        try {
            silkRoad.placeStore(100, 100);
            silkRoad.placeStore(200, 150);
            silkRoad.placeStore(300, 200);
            
            silkRoad.placeRobot(50);
            silkRoad.placeRobot(60);
            
            assertTrue(silkRoad.ok());
            assertEquals(3, silkRoad.stores().length);
            assertEquals(2, silkRoad.robots().length);
        } catch (SilkRoadException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    /**
     * Test: Agregar tienda válida
     */
    @Test
    public void shouldAddStoreSuccessfully() {
        try {
            silkRoad.placeStore(100, 100);
            silkRoad.placeStore(200, 150);
            silkRoad.placeStore(300, 180);
            
            assertEquals(3, silkRoad.stores().length);
        } catch (SilkRoadException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    /**
     * Test: Agregar tienda con tenges negativos
     */
    @Test
    public void shouldThrowExceptionWhenAddingStoreWithNegativeTenges() {
        try {
            silkRoad.placeStore(100, -50);
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.INVALID_TENGES_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Eliminar tienda con índice válido
     */
    @Test
    public void shouldRemoveStoreSuccessfully() {
        try {
            silkRoad.placeStore(100, 100);
            silkRoad.placeStore(200, 150);
            silkRoad.placeStore(300, 200);
            
            assertEquals(3, silkRoad.stores().length);
            
            silkRoad.removeStore(1);
            
            assertEquals(2, silkRoad.stores().length);
        } catch (SilkRoadException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    /**
     * Test: Eliminar tienda con índice inválido
     */
    @Test
    public void shouldThrowExceptionWithInvalidStoreIndex() {
        try {
            silkRoad.placeStore(100, 100);
            silkRoad.placeStore(200, 150);
            
            silkRoad.removeStore(5); // Índice fuera de rango
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.INVALID_INDEX_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Eliminar robot con índice válido
     */
    @Test
    public void shouldRemoveRobotSuccessfully() {
        try {
            silkRoad.placeRobot(50);
            silkRoad.placeRobot(100);
            silkRoad.placeRobot(150);
            
            assertEquals(3, silkRoad.robots().length);
            
            silkRoad.removeRobot(1);
            
            assertEquals(2, silkRoad.robots().length);
        } catch (SilkRoadException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    /**
     * Test: Eliminar robot con índice inválido
     */
    @Test
    public void shouldThrowExceptionWithInvalidRobotIndex() {
        try {
            silkRoad.placeRobot(50);
            silkRoad.placeRobot(100);
            
            silkRoad.removeRobot(5); // Índice fuera de rango
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.INVALID_INDEX_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Mover robots sin tiendas
     */
    @Test
    public void shouldThrowExceptionMovingRobotsWithoutStores() {
        try {
            silkRoad.placeStore(100, 100);
            silkRoad.placeRobot(50);
            
            silkRoad.removeStore(0);
            silkRoad.moveRobots();
            
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.NO_STORES_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Mover robots sin robots en la ruta
     */
    @Test
    public void shouldThrowExceptionMovingWithoutRobots() {
        try {
            silkRoad.placeStore(100, 100);
            silkRoad.placeStore(200, 150);
            
            silkRoad.moveRobots(); // No hay robots
            
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.NO_ROBOTS_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Reboot restaura el estado inicial
     */
    @Test
    public void shouldRebootSuccessfully() {
        try {
            silkRoad.placeStore(100, 100);
            silkRoad.placeStore(200, 150);
            silkRoad.placeRobot(50);
            
            silkRoad.moveRobots();
            
            int profitBeforeReboot = silkRoad.profit();
            silkRoad.reboot();
            int profitAfterReboot = silkRoad.profit();
            
            assertEquals(0, profitAfterReboot);
            assertTrue(profitBeforeReboot >= 0);
        } catch (SilkRoadException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    /**
     * Test: Método ok() funciona correctamente
     */
    @Test
    public void shouldReturnTrueWhenInitializedCorrectly() {
        assertTrue(silkRoad.ok());
    }
    
    /**
     * Test: Constructor con array de días
     */
    @Test
    public void shouldCreateWithDaysArray() {
        int[][] days = {
            {0, 100},
            {1, 150},
            {2, 200}
        };
        
        SilkRoad road = new SilkRoad(days);
        assertTrue(road.ok());
        assertEquals(3, road.stores().length);
        road.finish();
    }
    
    /**
     * Test: Agregar diferentes tipos de tiendas
     */
    @Test
    public void shouldAddDifferentStoreTypes() {
        silkRoad.placeStore("normal", 100, 100);
        silkRoad.placeStore("discount", 200, 150);
        silkRoad.placeStore("fighter", 300, 200);
        silkRoad.placeStore("autonomous", 400, 180);
        
        assertEquals(4, silkRoad.stores().length);
    }
    
    /**
     * Test: Agregar diferentes tipos de robots
     */
    @Test
    public void shouldAddDifferentRobotTypes() {
        silkRoad.placeRobot("normal", 50);
        silkRoad.placeRobot("neverback", 100);
        silkRoad.placeRobot("tender", 150);
        
        assertEquals(3, silkRoad.robots().length);
    }
}
