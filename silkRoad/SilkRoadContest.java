package silkRoad;

/**
 * The SilkRoadContest class simulates and solves the profit calculation problem
 * from the Silk Road contest. 
 * It provides two main operations:
 * - solve(): calculates the maximum total profit.
 * - simulate(): shows a detailed simulation of the process.
 * 
 * This class is designed to process a list of daily utilities (profits)
 * and determine the best total value.
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class SilkRoadContest {

    // Empty constructor - initializes the contest object
    public SilkRoadContest() {
    }

    /**
     * Calculates the maximum daily profit.
     * Each position in the array represents the profit of one day.
     * Negative values are not considered.
     * 
     * @param utilidades an array containing daily profits
     * @return the maximum total profit obtained by summing consecutive days
     */
    public int solve(int[] utilidades) {
        if (utilidades == null || utilidades.length == 0) {
            return 0;
        }

        int maximaUtilidad = 0;
        int utilidadActual = 0;

        // Recorremos todas las utilidades diarias
        for (int i = 0; i < utilidades.length; i++) {
            // Add current day's profit
            utilidadActual += utilidades[i];

            // If the sum is greater than the best result, save it
            if (utilidadActual > maximaUtilidad) {
                maximaUtilidad = utilidadActual;
            }
        }

        return maximaUtilidad;
    }

    /**
     * Simulates the profit calculation process.
     * It prints the number of days, daily profits, and the final maximum result.
     * 
     * @param utilidades an array containing daily profits
     */
    public void simulate(int[] utilidades) {
        System.out.println("Silk Road Simulation");
        System.out.println("Number of days: " + utilidades.length);

        // Mostrar cada utilidad del arreglo
        for (int i = 0; i < utilidades.length; i++) {
            System.out.println("Day " + (i + 1) + ": " + utilidades[i]);
        }

        int resultado = solve(utilidades);

        System.out.println("Maximum total profit: " + resultado);
    }
}
