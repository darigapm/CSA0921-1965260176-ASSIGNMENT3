class StationRunnable implements Runnable {
    private final String stationName;
    private final int scripts;

    public StationRunnable(String name, int scripts) {
        this.stationName = name;
        this.scripts = scripts;
    }

    @Override
    public void run() {
        for (int i = 1; i <= scripts; i++) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(stationName + " finished scanning " + scripts + " scripts.");
    }
}

public class AnswerScriptScanner {
    public static void main(String[] args) throws InterruptedException {
        Thread s1 = new Thread(new StationRunnable("Station-1", 5));
        Thread s2 = new Thread(new StationRunnable("Station-2", 5));
        Thread s3 = new Thread(new StationRunnable("Station-3", 5));
        Thread s4 = new Thread(new StationRunnable("Station-4", 5));

        s1.start(); s2.start(); s3.start(); s4.start();
        System.out.println("Station-1 alive after start(): " + s1.isAlive());

        s1.join(); s2.join(); s3.join(); s4.join();
        System.out.println("All stations complete. Total scripts scanned: 20");
    }
}
