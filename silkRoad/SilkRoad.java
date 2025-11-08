package silkRoad;

import shapes.Canvas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import shapes.Rectangle;

/**
 * Class SilkRoad
 * Represents a simulator of the Silk Road with stores and robots.
 * It allows creating, managing, and visualizing the behavior of stores and robots,
 * including profits and movements in a square spiral pattern.
 * 
 * The simulator provides functionalities to:
 * - Add or remove stores and robots.
 * - Move robots along a spiral route.
 * - Collect profits and display progress.
 * - Simulate contests and calculate daily maximum profit.
 * 
 * @author 
 *  Beltrán-Ducuara
 * @version 1.0
 * @date 2025-2
 */
public class SilkRoad {
    
    // ----- Attributes -----
    
    private int length;  // Silk road length (defines scale and store spacing)
    private ArrayList<Store> stores;  // List of stores on the silk road
    private ArrayList<Robot> robots;  // List of robots operating on the road
    private boolean visible;          // Visibility flag for GUI mode
    private int profit;               // Accumulated profit value
    private SilkRoadContest contest;  // Contest instance to simulate problem
    
    // Color arrays to assign different colors automatically
    private String[] storeColors = {"red", "blue", "green", "magenta", "yellow", "orange", "pink", "cyan"};
    private String[] robotColors = {"blue", "cyan", "magenta", "orange"};
    private int storeColorIndex = 0;
    private int robotColorIndex = 0;
    
    private SpiralPath spiralPath;  // Draws and manages the spiral path
    private ArrayList<int[]> spiralPositions; // Stores the coordinates of each spiral position
    
    // Progress bar representation
    private Rectangle progressBackground; // Background of the progress bar
    private Rectangle progressBar;        // Filled part of the bar indicating gain
    private int maxPossibleProfit;        // Used to calculate proportional bar filling
    
    /**
     * Constructor: creates the SilkRoad simulator with a given length.
     * Initializes all graphical and logical components.
     * 
     * @param length the length of the silk road
     */
    public SilkRoad(int length) {
        this.length = length;
        stores = new ArrayList<Store>();
        robots = new ArrayList<Robot>();
        visible = true;
        profit = 0;
        contest = new SilkRoadContest();
        spiralPositions = new ArrayList<int[]>();
        
        spiralPath = new SpiralPath();
        spiralPath.changeColor("black");
        
        // Configura el fondo de la barra de progreso
        progressBackground = new Rectangle();
        progressBackground.changeColor("gray");
        progressBackground.changeSize(25, 700);
        progressBackground.moveHorizontal(50 - 70);
        progressBackground.moveVertical(550 - 15);
        
        // Configura la barra verde que representa la ganancia
        progressBar = new Rectangle();
        progressBar.changeColor("green");
        progressBar.changeSize(25, 0);
        progressBar.moveHorizontal(50 - 70);
        progressBar.moveVertical(550 - 15);
        
        // Inicializa el lienzo para dibujar elementos
        Canvas.getCanvas();
    }
    
    /**
     * Constructor: creates the SilkRoad simulator using an array of days.
     * Massive constructor for Contest. Only creates normal stores.
     * 
     * @param days array where each row is [location, tenges]
     */
    public SilkRoad(int[][] days) {
        this(1000); // Calls main constructor with default road length
        
        if (days == null || days.length == 0) {
            return;
        }
        
        // Crea tiendas normales según los datos del arreglo
        for (int i = 0; i < days.length; i++) {
            if (days[i] != null && days[i].length >= 2) {
                int location = days[i][0];
                int tenges = days[i][1];
                
                try {
                    placeStore(location, tenges);
                } catch (SilkRoadException e) {
                    System.err.println("Error creating store from days: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Default constructor: creates a SilkRoad instance with default configuration.
     * Useful for quick simulations or testing without specific parameters.
     */
    public SilkRoad() {
        stores = new ArrayList<Store>();
        robots = new ArrayList<Robot>();
        visible = true;
        profit = 0;
        contest = new SilkRoadContest();
        spiralPositions = new ArrayList<int[]>();
        
        spiralPath = new SpiralPath();
        spiralPath.changeColor("black");
        
        // Inicializa la barra de progreso (fondo y parte verde)
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
     * Generates square spiral coordinates.
     * The spiral expands from a central point and alternates
     * direction in the order: right → down → left → up.
     * 
     * @param centerX  x coordinate of the spiral center
     * @param centerY  y coordinate of the spiral center
     * @param numPoints number of positions to generate
     * @param spacing distance between each spiral step
     * @return list of coordinate pairs representing the spiral path
     */
    private ArrayList<int[]> generateSquareSpiralPositions(int centerX, int centerY, int numPoints, int spacing) {
        ArrayList<int[]> positions = new ArrayList<int[]>();
        
        int x = centerX;
        int y = centerY;
        positions.add(new int[]{x, y}); // punto inicial
        
        int steps = 1;
        int direction = 0; // 0=derecha, 1=abajo, 2=izquierda, 3=arriba
        int pointsGenerated = 1;
        
        // Genera los puntos siguiendo la lógica de espiral cuadrada
        while (pointsGenerated < numPoints) {
            for (int i = 0; i < 2 && pointsGenerated < numPoints; i++) {
                for (int j = 0; j < steps && pointsGenerated < numPoints; j++) {
                    switch (direction) {
                        case 0: x += spacing; break; // Derecha
                        case 1: y += spacing; break; // Abajo
                        case 2: x -= spacing; break; // Izquierda
                        case 3: y -= spacing; break; // Arriba
                    }
                    positions.add(new int[]{x, y});
                    pointsGenerated++;
                }
                direction = (direction + 1) % 4; // cambia de dirección
            }
            steps++; // incrementa los pasos tras dos direcciones
        }
        
        return positions;
    }
    
    /**
     * Adds a new store at a specific x position with a given amount of tenges.
     * 
     * @param x horizontal location for the store
     * @param tenges initial money of the store
     * @throws SilkRoadException if tenges value is invalid (negative)
     */
    public void placeStore(int x, int tenges) throws SilkRoadException {
        if (tenges < 0) {
            throw new SilkRoadException(SilkRoadException.INVALID_TENGES_EXCEPTION);
        }
        
        // Asigna color secuencial a la tienda
        String color = storeColors[storeColorIndex % storeColors.length];
        storeColorIndex++;
        
        Store newStore = new Store(stores.size(), tenges, x, 50, color);
        stores.add(newStore);
    }

    /**
     * Adds a new store by type and location.
     * Creates an instance of Store or its subclasses based on the type.
     * 
     * @param type store type ("discount", "fighter", "autonomous", or others)
     * @param location x coordinate for the store
     * @param tenges initial amount of money
     */
    public void placeStore(String type, int location, int tenges) {
        String color = storeColors[storeColorIndex % storeColors.length];
        storeColorIndex++;
    
        int x = location;
        int y = 100 + (stores.size() * 60); // Distribuye tiendas verticalmente
    
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
                newStore = new Store(stores.size(), tenges, x, y, color);
                System.out.println("Tipo no reconocido, creando Store normal por defecto.");
                break;
        }

        stores.add(newStore);
    }   

    /**
     * Removes a store by its index.
     * 
     * @param index position in the list of stores
     * @throws SilkRoadException if index is invalid
     */
    public void removeStore(int index) throws SilkRoadException {
        if (index < 0 || index >= stores.size()) {
            throw new SilkRoadException(SilkRoadException.INVALID_INDEX_EXCEPTION);
        }
        stores.get(index).delete(); // Elimina la representación visual
        stores.remove(index);
    }

    /**
     * Adds a robot at a specific x position with automatic color and fixed size.
     * 
     * @param x horizontal location for the robot
     */
    public void placeRobot(int x) {
        String color = robotColors[robotColorIndex % robotColors.length];
        robotColorIndex++;
        
        Robot newRobot = new Robot(x, 100, 25, color);
        robots.add(newRobot);
    }

    /**
     * Adds a robot of a given type at a specific location.
     * 
     * @param type robot type ("neverback", "tender", or others)
     * @param location x coordinate of the robot
     */
    public void placeRobot(String type, int location) {
        String color = robotColors[robotColorIndex % robotColors.length];
        robotColorIndex++;
    
        int x = location;
        int y = 100;
        int size = 25;
    
        Robot newRobot;
    
        switch (type.toLowerCase()) {
            case "neverback":
                newRobot = new NeverBack(x, y, size, color);
                break;
            case "tender":
                newRobot = new Tender(x, y, size, color);
                break;
            default:
                newRobot = new Robot(x, y, size, color);
                System.out.println("Tipo no reconocido, creando Robot normal por defecto.");
                break;
        }
    
        robots.add(newRobot);
    }
    
    /**
     * Removes a robot by its index.
     * 
     * @param index position of the robot in the list
     * @throws SilkRoadException if index is invalid
     */
    public void removeRobot(int index) throws SilkRoadException {
        if (index < 0 || index >= robots.size()) {
            throw new SilkRoadException(SilkRoadException.INVALID_INDEX_EXCEPTION);
        }
        robots.get(index).delete(); // Elimina visualmente el robot
        robots.remove(index);
    }
    
    /**
     * Moves a specific robot to a new (x, y) position.
     * 
     * @param index index of the robot to move
     * @param newX new x coordinate
     * @param newY new y coordinate
     * @throws SilkRoadException if the index is invalid
     */
    public void moveRobot(int index, int newX, int newY) throws SilkRoadException {
        if (index < 0 || index >= robots.size()) {
            throw new SilkRoadException(SilkRoadException.INVALID_INDEX_EXCEPTION);
        }
        robots.get(index).moveTo(newX, newY);
    }
    
    /**
     * Moves all robots along the spiral path toward the most profitable store.
     * Depending on the type of store and robot, the collection logic may vary:
     * - Discount: collects 75% of the normal profit.
     * - Fighter: only robots with more money can collect.
     * - Tender: collects half of what it would normally take.
     * 
     * @throws SilkRoadException if there are no stores or robots available
     */
    public void moveRobots() throws SilkRoadException {
        if (stores.isEmpty()) {
            throw new SilkRoadException(SilkRoadException.NO_STORES_EXCEPTION);
        }
        if (robots.isEmpty()) {
            throw new SilkRoadException(SilkRoadException.NO_ROBOTS_EXCEPTION);
        }
    
        // Encuentra la tienda con más dinero disponible (más rentable)
        Store mejorTienda = stores.get(0);
        int mejorIndice = 0;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getTenges() > mejorTienda.getTenges()) {
                mejorTienda = stores.get(i);
                mejorIndice = i;
            }
        }
    
        int mayorRecolectado = 0;
        Robot robotMasExitoso = null;
        Map<Robot, Integer> dineroRobots = new HashMap<>();
    
        // Itera sobre cada robot y simula su movimiento y recolección
        for (Robot r : robots) {
            dineroRobots.putIfAbsent(r, 0);
            int dineroRobot = dineroRobots.get(r);
            
            // Mueve el robot siguiendo la espiral hasta la tienda más rentable
            if (mejorIndice < spiralPositions.size()) {
                for (int i = 0; i <= mejorIndice && i < spiralPositions.size(); i++) {
                    int[] pos = spiralPositions.get(i);
                    r.moveTo(pos[0], pos[1]);
                }
            }
            
            int recolectado = 0; // dinero obtenido por el robot en esta ronda
    
            // Aplica reglas especiales según el tipo de tienda
            if (mejorTienda instanceof Discount) {
                recolectado = (int)(mejorTienda.collect() * 0.75);
            } else if (mejorTienda instanceof Fighter) {
                // Solo recoge si tiene más dinero que la tienda
                if (dineroRobot > mejorTienda.getTenges()) {
                    recolectado = mejorTienda.collect();
                } else {
                    System.out.println("Robot no tiene más dinero que la Fighter.");
                    recolectado = 0;
                }
            } else {
                recolectado = mejorTienda.collect();
            }
    
            // Si es un robot Tender, solo obtiene la mitad
            if (r instanceof Tender) {
                recolectado /= 2;
            }

            // Actualiza el dinero total del robot y la ganancia global
            dineroRobot += recolectado;
            dineroRobots.put(r, dineroRobot);
            profit += recolectado;
    
            // Registra cuál robot fue el más exitoso
            if (recolectado > mayorRecolectado) {
                mayorRecolectado = recolectado;
                robotMasExitoso = r;
            }
        }
    
        // Actualiza la barra de progreso según la ganancia
        updateProgressBar();
        
        // Hace parpadear al robot que más recolectó
        if (robotMasExitoso != null) {
            robotMasExitoso.blink();
        }
    }

    /**
     * Resupplies all stores, restoring their tenges.
     * Used to simulate the start of a new day or restock event.
     */
    public void resupplyStores() {
        // Reabastece todas las tiendas a su valor inicial
        for (Store s : stores) {
            s.resupply();
        }
    }

    /**
     * Returns all robots to their initial positions.
     * Used after a day ends or to restart a simulation.
     */
    public void returnRobots() {
        if (!spiralPositions.isEmpty()) {
            int[] startPos = spiralPositions.get(0);
            for (int i = 0; i < robots.size(); i++) {
                // Reposiciona los robots en línea frente al punto de inicio
                int xPos = startPos[0] + (i * 30) - ((robots.size() - 1) * 15);
                int yPos = startPos[1] - 50;
                robots.get(i).moveTo(xPos, yPos);
            }
        }
    }

    /**
     * Reboots the SilkRoad simulation.
     * Resets all stores, returns robots, and resets the total profit.
     */
    public void reboot() {
        for (Store s : stores) {
            s.reset(); // Restaura el estado original de cada tienda
        }
        returnRobots(); // Devuelve los robots a su posición inicial
        profit = 0; // Reinicia la ganancia total
        updateProgressBar();
    }

    /**
     * Returns the total accumulated profit and resets it to zero.
     * 
     * @return total profit value before reset
     */
    public int profit() {
        int total = profit;
        profit = 0;
        return total;
    }

    /**
     * Returns a 2D array with the current screen positions of all stores.
     * Each row corresponds to a store: [x, y].
     * 
     * @return positions of all stores
     */
    public int[][] stores() {
        int[][] positions = new int[stores.size()][2];
        // Extrae las coordenadas gráficas de cada tienda
        for (int i = 0; i < stores.size(); i++) {
            positions[i][0] = stores.get(i).getScreenX();
            positions[i][1] = stores.get(i).getScreenY();
        }
        return positions;
    }
    
    /**
     * Returns a 2D array with the number of times each store has been emptied.
     * Each row corresponds to [storeIndex, emptiedCount].
     * 
     * @return data of emptied stores
     */
    public int[][] emptiedStores() {
        int[][] datos = new int[stores.size()][2];
        for (int i = 0; i < stores.size(); i++) {
            datos[i][0] = i; // índice de la tienda
            datos[i][1] = stores.get(i).getEmptiedCount(); // veces desocupada
        }
        return datos;
    }

    /**
     * Returns a 2D array with the current positions of all robots.
     * Each row corresponds to [x, y].
     * 
     * @return positions of all robots
     */
    public int[][] robots() {
        int[][] positions = new int[robots.size()][2];
        for (int i = 0; i < robots.size(); i++) {
            positions[i][0] = robots.get(i).getX();
            positions[i][1] = robots.get(i).getY();
        }
        return positions;
    }
    
    /**
     * Returns a 2D array simulating the profit earned by each robot in its last move.
     * Each row corresponds to [robotIndex, profitValue].
     * (In this implementation, profits are randomized for demonstration purposes.)
     * 
     * @return profit per move per robot
     */
    public int[][] profitPerMove() {
        int[][] ganancias = new int[robots.size()][2];
        for (int i = 0; i < robots.size(); i++) {
            ganancias[i][0] = i;
            ganancias[i][1] = (int)(Math.random() * 100); // valor simulado
        }
        return ganancias;
    }

    /**
     * Makes the entire SilkRoad simulation visible.
     * Displays all graphical components including path, stores, robots, and progress bar.
     */
    public void makeVisible() {
        this.visible = true;
        Canvas.getCanvas().setVisible(true);
        spiralPath.makeVisible();
        progressBackground.makeVisible();
        progressBar.makeVisible();
        
        // Muestra todas las tiendas
        for (Store s : stores) {
            s.makeVisible();
        }
        // Muestra todos los robots
        for (Robot r : robots) {
            r.makeVisible();
        }
    }

    /**
     * Makes the SilkRoad simulation invisible.
     * Hides all elements visually while keeping logical data in memory.
     */
    public void makeInvisible() {
        this.visible = false;
        spiralPath.makeInvisible();
        progressBackground.makeInvisible();
        progressBar.makeInvisible();
        
        // Elimina representación visual (no los objetos en memoria)
        for (Store s : stores) {
            s.delete();
        }
        for (Robot r : robots) {
            r.delete();
        }
    }
    /**
     * Updates the progress bar according to the current profit.
     * The width and color of the bar represent the percentage of maximum profit achieved.
     * 
     * Color changes dynamically based on percentage:
     * - Yellow: < 50%
     * - Cyan: between 50% and 75%
     * - Blue: between 75% and 100%
     * - Green: full profit achieved
     */
    private void updateProgressBar() {
        if (maxPossibleProfit == 0) {
            maxPossibleProfit = 1; // evita división por cero
        }
        
        // Calcula el ancho de la barra proporcional a la ganancia actual
        int barWidth = (int)((double)profit / maxPossibleProfit * 700);
        barWidth = Math.min(barWidth, 700); // límite máximo
        progressBar.changeSize(25, barWidth);
        
        double percentage = (double)profit / maxPossibleProfit;
        
        // Cambia el color según el progreso actual
        if (percentage >= 1.0) {
            progressBar.changeColor("green");
        } else if (percentage >= 0.75) {
            progressBar.changeColor("blue");
        } else if (percentage >= 0.50) {
            progressBar.changeColor("cyan");
        } else {
            progressBar.changeColor("yellow");
        }
    }
    
    /**
     * Finishes and cleans the SilkRoad simulation.
     * Deletes all visual elements and resets all logical structures.
     * This action represents the end of the simulator.
     */
    public void finish() {
        // Limpia la ruta y oculta la barra de progreso
        spiralPath.clear();
        progressBackground.makeInvisible();
        progressBar.makeInvisible();
        
        // Elimina todas las tiendas y robots de la pantalla
        for (Store s : stores) {
            s.delete();
        }
        for (Robot r : robots) {
            r.delete();
        }
        // Vacía las listas de datos
        stores.clear();
        robots.clear();
    }
    /**
     * Checks if the SilkRoad simulator is correctly initialized.
     * Ensures that the lists of stores and robots exist (not null).
     * 
     * @return true if initialized correctly, false otherwise
     */
    public boolean ok() {
        return (stores != null && robots != null);
    }
    
    /**
     * Executes the contest simulation using the given utility values.
     * Delegates the process to the SilkRoadContest class.
     * 
     * @param utilidades array of utility values used for the simulation
     */
    public void ejecutarSimulacion(int[] utilidades) {
        // Llama al método simulate() de la clase SilkRoadContest
        // para ejecutar la simulación basada en los datos de utilidades
        contest.simulate(utilidades);
    }
}


