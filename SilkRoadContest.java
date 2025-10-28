public class SilkRoadContest {

    // Constructor vacío
    public SilkRoadContest() {
    }

    /**
     * Calcula la máxima utilidad diaria.
     * Cada posición del arreglo representa la utilidad de un día.
     * No se usan valores negativos.
     * 
     * @param utilidades arreglo con las utilidades de cada día.
     * @return la suma máxima de utilidades consecutivas.
     */
    public int solve(int[] utilidades) {
        if (utilidades == null || utilidades.length == 0) {
            return 0;
        }

        int maximaUtilidad = 0;
        int utilidadActual = 0;

        // Recorremos todas las utilidades diarias
        for (int i = 0; i < utilidades.length; i++) {
            // Sumar la utilidad del día actual
            utilidadActual += utilidades[i];

            // Si la suma supera el mejor resultado, se guarda
            if (utilidadActual > maximaUtilidad) {
                maximaUtilidad = utilidadActual;
            }
        }

        return maximaUtilidad;
    }

    /**
     * Simula el cálculo de la utilidad.
     * Muestra los valores y el resultado final.
     */
    public void simulate(int[] utilidades) {
        System.out.println("Simulación de la Ruta de la Seda");
        System.out.println("Número de días: " + utilidades.length);

        for (int i = 0; i < utilidades.length; i++) {
            System.out.println("Día " + (i + 1) + ": " + utilidades[i]);
        }

        int resultado = solve(utilidades);

        System.out.println("Máxima utilidad total: " + resultado);
    }
}
