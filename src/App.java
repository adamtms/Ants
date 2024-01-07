public class App {
    public static void main(String[] args) throws Exception {
        World world = new World();
        world.initializeAnthills();
        world.initializeVertices(30);
        world.initializeLarvaes(10);
        world.initializeAnts(10);
        world.run();
    }
}
