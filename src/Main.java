import controllers.DataController;
import views.MainView;


public class Main {
    public static void main(String[] args) throws Exception {
        DataController controller = new DataController();
        controller.loadFromCsv("data/airports.csv", "data/flights.csv");
        new MainView(controller);
    }
}