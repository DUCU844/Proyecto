package silkRoad;

import shapes.Canvas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import shapes.Rectangle;

/**
 * Clase SilkRoad
 * It represents a Silk Road simulator with shops and robots.
 * It allows you to add, move, and delete robots and stores, as well as
 * record gains and gaps in the stores.
 * 
 * @author Beltrán-Ducuara
 * @version 2025-2
 */
public class SilkRoad {

    private ArrayList<Store> stores;
    private ArrayList<Robot> robots;
    private boolean visible;
    private int profit;
    private SilkRoadContest contest;
    
    private String[] storeColors = {"red", "blue", "green", "magenta", "yellow", "orange", "pink", "cyan"};
    private String[] robotColors = {"blue", "cyan", "magenta", "orange"};
    private int storeColorIndex = 0;
    private int robotColorIndex = 0;
    
    private SpiralPath spiralPath;
    
    private Rectangle progressBackground;
    private Rectangle progressBar;
    private int maxPossibleProfit;
    
    /**
    public void testPath() {
        System.out.println("\n===== TEST DIRECTO DEL CAMINO =====\n");
        
        int[][] points = {
            {200, 200},
            {400, 200}
        };
        
        spiralPath.changeColor("red");
        spiralPath.drawPath(points);
        spiralPath.makeVisible();
        
        System.out.println("\n===== FIN DEL TEST =====\n");
    }
    */

    /**
     * Constructor: creates the SilkRoad simulator.
     */
    public SilkRoad() {
        stores = new ArrayList<Store>();
        robots = new ArrayList<Robot>();
        visible = true;
        profit = 0;
        contest = new SilkRoadContest();
        
        spiralPath = new SpiralPath();
        spiralPath.changeColor("black");
        
        progressBackground = new Rectangle();
        progressBackground.changeColor("gray");
        progressBackground.changeSize(25, 700);
        progressBackground.moveHorizontal(50 - 70);
        progressBackground.moveVertical(550 - 15);
        
        progressBar = new Rectangle();
        progressBar.changeColor("green");
        progressBar.changeSize(25, 0);
        progressBar.moveHorizontal(50 - 70);
        progressBar.moveVertical(550 - 15);
        
        Canvas.getCanvas();
    }
    
    /**
     * Create the initial route with stores and robots.
     * @param store positions X coordinates of the stores
     * @param tengesTiendas initial money from each store
     * @param posicionesRobots X coordinates of the robots
     */
    public void create(int[] posicionesTiendas, int[] tengesTiendas, int[] posicionesRobots) throws SilkRoadException {
        if (posicionesTiendas == null || tengesTiendas == null || posicionesRobots == null) {
            throw new SilkRoadException(SilkRoadException.INVALID_ARRAYS_EXCEPTION);
        }
        
        if (posicionesTiendas.length != tengesTiendas.length) {
            throw new SilkRoadException(SilkRoadException.ARRAY_SIZE_MISMATCH_EXCEPTION);
        }
        
        if (posicionesTiendas.length == 0) {
            throw new SilkRoadException(SilkRoadException.NO_STORES_EXCEPTION);
        }
        
        for (int tenges : tengesTiendas) {
            if (tenges < 0) {
                throw new SilkRoadException(SilkRoadException.INVALID_TENGES_EXCEPTION);
            }
        }
        stores.clear();
        robots.clear();
        profit = 0;
        storeColorIndex = 0;
        robotColorIndex = 0;
        
        maxPossibleProfit = 0;
        for (int i = 0; i < tengesTiendas.length; i++) {
            maxPossibleProfit += tengesTiendas[i];
        }
        
        // Crear tiendas en posiciones calculadas
        int centerX = 400;
        int centerY = 300;
        int spacing = 80;
        
        int[][] storePositions = new int[posicionesTiendas.length][2];
        
        for (int i = 0; i < posicionesTiendas.length; i++) {
            // Calcular posición en espiral simple 
            double angle = (2 * Math.PI * i) / posicionesTiendas.length;
            int radius = 150;
            int x = centerX + (int)(radius * Math.cos(angle));
            int y = centerY + (int)(radius * Math.sin(angle));
            
            storePositions[i][0] = x;
            storePositions[i][1] = y;
            
            // Obtener color único
            String color = storeColors[storeColorIndex % storeColors.length];
            storeColorIndex++;
            
            Store newStore = new Store(i, tengesTiendas[i], x, y, color);
            stores.add(newStore);
        }
        
        spiralPath.changeColor("black");
        spiralPath.drawPath(storePositions);
        spiralPath.makeVisible();
        
        // Crear robots en línea superior
        for (int j = 0; j < posicionesRobots.length; j++) {
            int xPos = 150 + (j * 100);
            int yPos = 50;
            
            String color = robotColors[robotColorIndex % robotColors.length];
            robotColorIndex++;
            
            Robot newRobot = new Robot(xPos, yPos, 25, color);
            robots.add(newRobot);
        }
        
        updateProgressBar();
        progressBackground.makeVisible();
        progressBar.makeVisible();
        
    }

    /**
     * Adds a new store at given coordinates (x, y) with certain amount of tenges.
     */
    public void placeStore(int x, int tenges) throws SilkRoadException {
         if (tenges < 0) {
            throw new SilkRoadException(SilkRoadException.INVALID_TENGES_EXCEPTION);
        }
        
        String color = storeColors[storeColorIndex % storeColors.length];
        storeColorIndex++;
        
        Store newStore = new Store(stores.size(), tenges, x, 50, color);
        stores.add(newStore);
    }

    //New methood
    /**
     * Create a store of the specified type at the given location.
     * Valid types: "autonomous", "discount", "fighter"
     * @param type type of store
     * @param location position in x
     * @param tenges initial amount
     */
    public void placeStore(String type, int location, int tenges) throws SilkRoadException {
        if (tenges < 0) {
            throw new SilkRoadException(SilkRoadException.INVALID_TENGES_EXCEPTION);
        }
        String color = storeColors[storeColorIndex % storeColors.length];
        storeColorIndex++;
    
        // Coordenadas básicas (puedes ajustar según tu sistema visual)
        int x = location;
        int y = 100 + (stores.size() * 60);
    
        Store newStore;
    
        switch (type.toLowerCase()) {
        case "discount":
            newStore = new Discount(stores.size(), tenges, x, y, color);
            break;
        case "fighter":
            newStore = new Fighter(stores.size(), tenges, x, y, color);
            break;
        case "autonomous":
            newStore = new Autonomous(stores.size(), tenges, x, y, color);
            break;
        default:
            // Si no coincide con ninguno, se crea una tienda normal
            newStore = new Store(stores.size(), tenges, x, y, color);
            System.out.println("Tipo no reconocido, creando Store normal por defecto.");
            break;
        }

        stores.add(newStore);
    }   

    /**
     * Removes a store by its index.
     * @index a current position
     */
    public void removeStore(int index) throws SilkRoadException {
        if (index < 0 || index >= stores.size()) {
            throw new SilkRoadException(SilkRoadException.INVALID_INDEX_EXCEPTION);
        }
        if (index >= 0 && index < stores.size()) {
            stores.get(index).delete();
            stores.remove(index);
        }
    }

    /**
     * Adds a new robot at given coordinates (x, y).
     * @param x X position
     */
    public void placeRobot(int x) {
        String color = robotColors[robotColorIndex % robotColors.length];
        robotColorIndex++;
        
        Robot newRobot = new Robot(x, 100, 25, color);
        robots.add(newRobot);
    }

    //New methood
    /**
     * Crea un robot del tipo especificado y lo coloca en la ubicación dada.
     * 
     * @param type Tipo de robot: "normal", "neverback" o "tender".
     * @param location Posición horizontal donde se ubicará el robot.
     */
    public void placeRobot(String type, int location) {
        String color = robotColors[robotColorIndex % robotColors.length];
        robotColorIndex++;
    
        int x = location;
        int y = 100; // Altura estándar para los robots
        int size = 25;
    
        Robot newRobot;
    
        // Seleccionar tipo de robot
        switch (type.toLowerCase()) {
            case "neverback":
                newRobot = new NeverBack(x, y, size, color);
                break;
    
            case "tender":
                newRobot = new Tender(x, y, size, color);
                break;
    
            default: // "normal" u otro texto crea un robot estándar
                newRobot = new Robot(x, y, size, color);
                System.out.println("Tipo no reconocido, creando Robot normal por defecto.");
                break;
        }
    
        robots.add(newRobot);
    }

    
    /**
     * Removes a robot by its index.
     * @index a current position
     */
    public void removeRobot(int index) throws SilkRoadException {
        if (index < 0 || index >= robots.size()) {
            throw new SilkRoadException(SilkRoadException.INVALID_INDEX_EXCEPTION);
        }
        
        if (index >= 0 && index < robots.size()) {
            robots.get(index).delete();
            robots.remove(index);
        }
    }

    /**
     * Moves a robot to new coordinates (x, y).
     */
    public void moveRobot(int index, int newX, int newY) throws SilkRoadException {
        if (index < 0 || index >= robots.size()) {
            throw new SilkRoadException(SilkRoadException.INVALID_INDEX_EXCEPTION);
        }
        
        if (index >= 0 && index < robots.size()) {
            robots.get(index).moveTo(newX, newY);
        }
    }
    
    /**
     * Moves a robot to new coordinates (x, y).
     * @param index current position
     * @param newX a new position in x.
     * @param newy a new position in y.
     */
    public void moveRobots() throws SilkRoadException{
        if (stores.isEmpty()) {
            throw new SilkRoadException(SilkRoadException.NO_STORES_EXCEPTION);
        }
        if (robots.isEmpty()) {
            throw new SilkRoadException(SilkRoadException.NO_ROBOTS_EXCEPTION);
        }
        
        if (stores.isEmpty() || robots.isEmpty()) return;
    
        // Determinar la tienda más rentable
        Store mejorTienda = stores.get(0);
        for (Store s : stores) {
            if (s.getTenges() > mejorTienda.getTenges()) {
                mejorTienda = s;
            }
        }
    
        int mayorRecolectado = 0;
        Robot robotMasExitoso = null;
    
        // Simulación de dinero individual de cada robot 
        Map<Robot, Integer> dineroRobots = new HashMap<>();
    
        for (Robot r : robots) {
            // valor inicial del robot si no tiene aún
            dineroRobots.putIfAbsent(r, 0);
            int dineroRobot = dineroRobots.get(r);
            r.moveTo(mejorTienda.getScreenX(), mejorTienda.getScreenY());
            int recolectado = 0;
    
            
            if (mejorTienda instanceof Discount) {
                // Tienda discount entrega 75 %
                recolectado = (int)(mejorTienda.collect() * 0.75);
    
            } else if (mejorTienda instanceof Fighter) {
                //solosi el robot tiene más dinero que la tienda
                if (dineroRobot > mejorTienda.getTenges()) {
                    recolectado = mejorTienda.collect();
                } else {
                    System.out.println("Robot no tiene más dinero que la Fighter, no puede recolectar.");
                    recolectado = 0;
                }
    
            } else {
                // Tienda normal
                recolectado = mejorTienda.collect();
            }
    
            // Ajuste si el robot es Tender 
            if (r instanceof Tender) {
                recolectado /= 2;
            }

            dineroRobot += recolectado;
            dineroRobots.put(r, dineroRobot);
            profit += recolectado;
    
            // Identificar robot más exitoso
            if (recolectado > mayorRecolectado) {
                mayorRecolectado = recolectado;
                robotMasExitoso = r;
            }
        }
    
        updateProgressBar();
        
        // hacemos que el mejor parpadee
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
        
        updateProgressBar();
    }

    /**
     * Calculates and returns the total profit (sum of diary collected money from all stores).
     */
    public int profit() {
        int total = profit;
        profit = 0;  // Reinicia después de devolver las ganancias
        return total;
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
     * Returns an array with the number of times each store has been vacated.
     */
    public int[][] emptiedStores() {
        int[][] datos = new int[stores.size()][2]; // [posición, veces vacía]
        for (int i = 0; i < stores.size(); i++) {
            datos[i][0] = i;                       // posición ficticia
            datos[i][1] = stores.get(i).getEmptiedCount();
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
     *Returns an array with the profits per movement of each robot.
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
        Canvas.getCanvas().setVisible(true);
        
        spiralPath.makeVisible();
        
        progressBackground.makeVisible();
        progressBar.makeVisible();
        
        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).makeVisible();
        }
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).makeVisible();
        }
    }

    /**
     * Makes the simulator invisible (erases all elements).
     */
    public void makeInvisible() {
        this.visible = false;
        spiralPath.makeInvisible();
        
        progressBackground.makeInvisible();
        progressBar.makeInvisible();
        
        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).delete();
        }
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).delete();
        }
    }

    private void updateProgressBar() {
        if (maxPossibleProfit == 0) {
            maxPossibleProfit = 1; // Evitar división por cero
        }
        
        // Calcular ancho de la barra (máximo 700 píxeles)
        int barWidth = (int)((double)profit / maxPossibleProfit * 700);
        barWidth = Math.min(barWidth, 700); // No exceder el ancho máximo
        
        progressBar.changeSize(25, barWidth);
        
        // Cambiar color según el progreso
        double percentage = (double)profit / maxPossibleProfit;
        
        if (percentage >= 1.0) {
            progressBar.changeColor("green");  // 100% = verde
        } else if (percentage >= 0.75) {
            progressBar.changeColor("blue");    // 75-99% = azul
        } else if (percentage >= 0.50) {
            progressBar.changeColor("cyan");    // 50-74% = cyan
        } else {
            progressBar.changeColor("yellow");   // 0-49% = amarillo
        }
    }
    
    /**
     * Finishes the simulator (removes everything).
     */
    public void finish() {
        spiralPath.clear();
        
        progressBackground.makeInvisible();
        progressBar.makeInvisible();
        
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

