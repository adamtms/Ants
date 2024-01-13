/**
 * The main class that initializes the world and runs the simulation.
 */
public class App {
    public static void main(String[] args) throws Exception {
        World world = new World();
        world.initializeVertices(30);
        world.initializeLarvaes(60);
        world.initializeAnts(10);
        world.run();
    }
}
