package silkRoad;

/**
 * Only robots with higher tenges can collect the tenges from this store.
 * 
 * @author Beltrán-Ducuara
 * @version 2025-2
 */
public class Fighter extends Store {

    public Fighter(int position, int tenges, int x, int y, String color) {
        super(position, tenges, x, y, color);
    }

    /**
     * Solo entrega su dinero si el robot tiene más que la tienda.
     * @param robotMoney dinero actual del robot
     * @return dinero recolectado (0 si no tiene derecho)
     */
    public int collect(int robotMoney) {
        if (robotMoney > getTenges()) {
            return super.collect(); // el robot puede vaciar la tienda
        } else {
            System.out.println("El robot no tiene suficiente dinero para vaciar esta FighterStore.");
            return 0;
        }
    }
}


