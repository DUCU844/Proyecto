import org.junit.*;
import static org.junit.Assert.*;

/**
 * Unit tests for the Store class.
 * 
 * Verifies:
 * - Initial money (tenges).
 * - collect(), resupply() and reset() behavior.
 * - Position and coordinates.
 */
public class StoreTest {
    private Store store;

    @Before
    public void setUp() {
        // Crea una tienda en (20, 20) con 50 tenges
        store = new Store(1, 50, 20, 20);
    }

    @After
    public void tearDown() {
        store = null;
    }

    @Test
    public void testInitialTenges() {
        assertEquals(50, store.getTenges());
    }

    @Test
    public void testCollect() {
        int collected = store.collect();
        assertEquals(50, collected);
        assertEquals(0, store.getTenges());
    }

    @Test
    public void testResupply() {
        store.collect();
        store.resupply();
        assertEquals(50, store.getTenges());
    }

    @Test
    public void testReset() {
        store.collect();
        store.reset();
        assertEquals(50, store.getTenges());
    }

    @Test
    public void testCoordinates() {
        assertEquals(20, store.getScreenX());
        assertEquals(20, store.getScreenY());
    }
}




