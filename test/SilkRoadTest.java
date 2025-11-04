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
        silkRoad = new SilkRoad();
    }
    
    @AfterEach
    public void tearDown() {
        if (silkRoad != null) {
            silkRoad.finish();
        }
        silkRoad = null;
    }
    
    /**
     * Test: Crear una ruta válida
     */
    @Test
    public void shouldCreateValidRoad() throws SilkRoadException{
        try {
            int[] posStores = {0, 1, 2};
            int[] tenges = {100, 150, 200};
            int[] posRobots = {0, 1};
            
            silkRoad.create(posStores, tenges, posRobots);
            
            assertTrue(silkRoad.ok());
            assertEquals(3, silkRoad.stores().length);
            assertEquals(2, silkRoad.robots().length);
        } catch (SilkRoadException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    /**
     * Test: Crear ruta con arrays null
     */
    @Test
    public void shouldThrowExceptionWithNullArrays() {
        try {
            silkRoad.create(null, null, null);
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.INVALID_ARRAYS_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Crear ruta con arrays de diferente tamaño
     */
    @Test
    public void shouldThrowExceptionWithMismatchedArrays() {
        try {
            int[] posStores = {0, 1, 2};
            int[] tenges = {100, 150}; // Tamaño diferente
            int[] posRobots = {0};
            
            silkRoad.create(posStores, tenges, posRobots);
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.ARRAY_SIZE_MISMATCH_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Crear ruta sin tiendas
     */
    @Test
    public void shouldThrowExceptionWithNoStores() {
        try {
            int[] posStores = {};
            int[] tenges = {};
            int[] posRobots = {0};
            
            silkRoad.create(posStores, tenges, posRobots);
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.NO_STORES_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Crear ruta con tenges negativos
     */
    @Test
    public void shouldThrowExceptionWithNegativeTenges() {
        try {
            int[] posStores = {0, 1};
            int[] tenges = {100, -50}; // Tenges negativo
            int[] posRobots = {0};
            
            silkRoad.create(posStores, tenges, posRobots);
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.INVALID_TENGES_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Agregar tienda válida
     */
    @Test
    public void shouldAddStoreSuccessfully() {
        try {
            int[] posStores = {0, 1};
            int[] tenges = {100, 150};
            int[] posRobots = {0};
            
            silkRoad.create(posStores, tenges, posRobots);
            silkRoad.placeStore(200, 180);
            
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
            int[] posStores = {0, 1, 2};
            int[] tenges = {100, 150, 200};
            int[] posRobots = {0};
            
            silkRoad.create(posStores, tenges, posRobots);
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
            int[] posStores = {0, 1};
            int[] tenges = {100, 150};
            int[] posRobots = {0};
            
            silkRoad.create(posStores, tenges, posRobots);
            silkRoad.removeStore(5); // Índice fuera de rango
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
            int[] posStores = {0};
            int[] tenges = {100};
            int[] posRobots = {0};
            
            silkRoad.create(posStores, tenges, posRobots);
            silkRoad.removeStore(0);
            silkRoad.moveRobots();
            fail("Debería lanzar SilkRoadException");
        } catch (SilkRoadException e) {
            assertEquals(SilkRoadException.NO_STORES_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Test: Reboot restaura el estado inicial
     */
    @Test
    public void shouldRebootSuccessfully() {
        try {
            int[] posStores = {0, 1};
            int[] tenges = {100, 150};
            int[] posRobots = {0};
            
            silkRoad.create(posStores, tenges, posRobots);
            silkRoad.moveRobots();
            
            int profitBeforeReboot = silkRoad.profit();
            silkRoad.reboot();
            int profitAfterReboot = silkRoad.profit();
            
            assertEquals(0, profitAfterReboot);
            assertTrue(profitBeforeReboot > profitAfterReboot);
        } catch (SilkRoadException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}
