package silkRoad;

/**
 * The SilkRoadException class defines custom exceptions 
 * for handling specific error cases in the Silk Road simulator.
 * 
 * Each type of error has its own message, defined as a constant.
 * These exceptions are used to ensure valid data and safe operations
 * when managing robots, stores, and arrays in the simulation.
 * 
 * Example usage:
 * throw new SilkRoadException(SilkRoadException.INVALID_POSITION_EXCEPTION);
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class SilkRoadException extends Exception {

    // Common error messages used throughout the simulator
    public static final String INVALID_POSITION_EXCEPTION = "La posición especificada no es válida";
    public static final String INVALID_TENGES_EXCEPTION = "La cantidad de tenges debe ser positiva";
    public static final String INVALID_INDEX_EXCEPTION = "El índice está fuera de rango";
    public static final String NO_STORES_EXCEPTION = "No hay tiendas en la ruta";
    public static final String NO_ROBOTS_EXCEPTION = "No hay robots en la ruta";
    public static final String EMPTY_STORE_EXCEPTION = "La tienda está vacía";
    public static final String INVALID_ARRAYS_EXCEPTION = "Los arreglos de entrada no son válidos";
    public static final String ARRAY_SIZE_MISMATCH_EXCEPTION = "Los arreglos de tiendas deben tener el mismo tamaño";

    /**
     * Creates a new SilkRoadException with a specific message.
     * 
     * @param message the error message describing the cause of the exception
     */
    public SilkRoadException(String message) {
        super(message);
    }
}
