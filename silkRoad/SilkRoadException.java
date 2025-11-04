package silkRoad;


/**
 * Write a description of class SilkRoadException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SilkRoadException extends Exception{
    public static final String INVALID_POSITION_EXCEPTION = "La posición especificada no es válida";
    public static final String INVALID_TENGES_EXCEPTION = "La cantidad de tenges debe ser positiva";
    public static final String INVALID_INDEX_EXCEPTION = "El índice está fuera de rango";
    public static final String NO_STORES_EXCEPTION = "No hay tiendas en la ruta";
    public static final String NO_ROBOTS_EXCEPTION = "No hay robots en la ruta";
    public static final String EMPTY_STORE_EXCEPTION = "La tienda está vacía";
    public static final String INVALID_ARRAYS_EXCEPTION = "Los arreglos de entrada no son válidos";
    public static final String ARRAY_SIZE_MISMATCH_EXCEPTION = "Los arreglos de tiendas deben tener el mismo tamaño";
    public SilkRoadException(String message){
        super(message);
    }

}
