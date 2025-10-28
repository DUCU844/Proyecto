import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Clase SilkRoad
 * Representa un simulador de la Ruta de la Seda con tiendas y robots.
 * Permite agregar, mover y eliminar robots y tiendas, además de
 * registrar ganancias y vacíos en las tiendas.
 * 
 * @author Beltrán-Ducuara
 * @date 2025-2
 * @version 1.0
 */
public class SilkRoad {

    private ArrayList<Store> stores;
    private ArrayList<Robot> robots;
    private boolean visible;
    private int profit;
    private SilkRoadContest contest;

    /**
     * Constructor: creates the SilkRoad simulator.
     */
    public SilkRoad() {
        stores = new ArrayList<Store>();
        robots = new ArrayList<Robot>();
        visible = true;
        profit = 0;
        contest = new SilkRoadContest();
    }
    
    /**
     * Crea la ruta inicial con tiendas y robots.
     * @param posicionesTiendas coordenadas X de las tiendas
     * @param tengesTiendas dinero inicial de cada tienda
     * @param posicionesRobots coordenadas X de los robots
     */
    public void create(int[] posicionesTiendas, int[] tengesTiendas, int[] posicionesRobots) {
        stores.clear();
        robots.clear();
        profit = 0;
        
        for (int i = 0; i < posicionesTiendas.length; i++) {
            placeStore(posicionesTiendas[i], tengesTiendas[i]);
        }
    
        for (int j = 0; j < posicionesRobots.length; j++) {
            placeRobot(posicionesRobots[j]);
        }
    }

    /**
     * Adds a new store at given coordinates (x, y) with certain amount of tenges.
     */
    public void placeStore(int x, int tenges) {
     Store newStore = new Store(0, tenges, x, 50);
     stores.add(newStore);
    }


    /**
     * Removes a store by its index.
     */
    public void removeStore(int index) {
        if (index >= 0 && index < stores.size()) {
            stores.get(index).delete();
            stores.remove(index);
        }
    }

    /**
     * Adds a new robot at given coordinates (x, y).
     */
    public void placeRobot(int x) {
    Robot newRobot = new Robot(x, 100, 20); // Y fija, tamaño 20
    robots.add(newRobot);
    }

    /**
     * Removes a robot by its index.
     */
    public void removeRobot(int index) {
        if (index >= 0 && index < robots.size()) {
            robots.get(index).delete();
            robots.remove(index);
        }
    }

    /**
     * Moves a robot to new coordinates (x, y).
     */
    public void moveRobot(int index, int newX, int newY) {
        if (index >= 0 && index < robots.size()) {
            robots.get(index).moveTo(newX, newY);
        }
    }
    
    /**
     * Mueve los robots hacia la tienda más rentable,
     * recolecta el dinero y hace parpadear al robot más exitoso.
     */
    public void moveRobots() {
        // Si no hay tiendas o robots, no hacer nada
        if (stores.isEmpty() || robots.isEmpty()) return;
    
        // Encontrar la tienda más rentable (con más tenges)
        Store mejorTienda = stores.get(0);
        for (int i = 0; i < stores.size(); i++) {
            Store s = stores.get(i);
            if (s.getTenges() > mejorTienda.getTenges()) {
                mejorTienda = s;
            }
        }
    
        int mayorRecolectado = 0;   // cantidad máxima recolectada
        Robot robotMasExitoso = null; // robot con más ganancia
    
        // Mover cada robot hacia la tienda más rentable
        for (int i = 0; i < robots.size(); i++) {
            Robot r = robots.get(i);
    
            // Mover el robot visualmente
            r.moveTo(mejorTienda.getScreenX(), mejorTienda.getScreenY());
    
            // Recolectar dinero (el método collect cambia color si la tienda queda vacía)
            int recolectado = mejorTienda.collect();
            profit += recolectado;
    
            // Verificar si este robot fue el más exitoso
            if (recolectado > mayorRecolectado) {
                mayorRecolectado = recolectado;
                robotMasExitoso = r;
            }
        }
    
        // Hacer parpadear al robot más exitoso
        if (robotMasExitoso != null) {
            robotMasExitoso.blink();
        }
    }


    /**
     * Resupplies all stores to their initial amount of tenges.
     */
    public void resupplyStores() {
        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).resupply();
        }
    }

    /**
     * Returns all robots to their initial position.
     */
    public void returnRobots() {
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).reset();
        }
    }

    /**
     * Reboots the SilkRoad simulator (resets stores and robots).
     */
    public void reboot() {
        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).reset();
        }
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).reset();
        }
        profit = 0;
    }

    /**
     * Calculates and returns the total profit (sum of collected money from all stores).
     */
    public int profit() {
        profit = 0;
        for (int i = 0; i < stores.size(); i++) {
            profit += stores.get(i).collect();
        }
        return profit;
    }

    /**
     * Returns current coordinates of all stores.
     */
    public int[][] stores() {
        int[][] positions = new int[stores.size()][2];
        for (int i = 0; i < stores.size(); i++) {
            positions[i][0] = stores.get(i).getScreenX();
            positions[i][1] = stores.get(i).getScreenY();
        }
        return positions;
    }
    
    /**
     * Devuelve una matriz con la cantidad de veces que cada tienda ha sido desocupada.
     * (Versión simulada: genera números aleatorios)
     */
    public int[][] emptiedStores() {
        int[][] datos = new int[stores.size()][2]; // [posición, veces vacía]
        for (int i = 0; i < stores.size(); i++) {
            datos[i][0] = i;                       // posición ficticia
            datos[i][1] = (int)(Math.random() * 5); // veces vacía aleatoria
        }
        return datos;
    }

    /**
     * Returns current coordinates of all robots.
     */
    public int[][] robots() {
        int[][] positions = new int[robots.size()][2];
        for (int i = 0; i < robots.size(); i++) {
            positions[i][0] = 0;
            positions[i][1] = 0;
        }
        return positions;
    }
    
    /**
     * Devuelve una matriz con las ganancias por movimiento de cada robot.
     * (Versión simulada: datos aleatorios)
     */
    public int[][] profitPerMove() {
        int[][] ganancias = new int[robots.size()][2]; // [robot, ganancia]
        for (int i = 0; i < robots.size(); i++) {
            ganancias[i][0] = i;                       // número del robot
            ganancias[i][1] = (int)(Math.random() * 100); // ganancia simulada
        }
        return ganancias;
    }

    /**
     * Makes the simulator visible (draws all elements again).
     */
    public void makeVisible() {
        this.visible = true;
        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).reset();
        }
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).reset();
        }
    }

    /**
     * Makes the simulator invisible (erases all elements).
     */
    public void makeInvisible() {
        this.visible = false;
        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).delete();
        }
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).delete();
        }
    }

    /**
     * Finishes the simulator (removes everything).
     */
    public void finish() {
        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).delete();
        }
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).delete();
        }
        stores.clear();
        robots.clear();
    }

    /**
     * Checks if the simulator is in a valid state.
     */
    public boolean ok() {
        return (stores != null && robots != null);
    }
    
    public void ejecutarSimulacion(int[] utilidades) {
        contest.simulate(utilidades); 
    }
}


