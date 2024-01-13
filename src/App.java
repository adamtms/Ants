/**
 * The main class that initializes the world and runs the simulation.
 */
public class App {
    /**
     * The main method is the entry point of the program.
     * It initializes the world, vertices, larvaes, ants, and runs the simulation.
     *
     * @param args the command line arguments
     * @throws Exception if an error occurs during the simulation
     */
    public static void main(String[] args) throws Exception {
        World world = new World();
        world.initializeVertices(30);
        world.initializeLarvaes(60);
        world.initializeAnts(10);
        world.run();
    }
}
