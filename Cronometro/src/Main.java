public class Main {
    public static void main(String[] args) {
        var window = new Window();
        var thread = new Thread(new Clock(window));
        thread.start();
    }
}