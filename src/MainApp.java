import model.HotelDAO;
import view.HotelView;
import controller.HotelController;

public class MainApp {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/hotel_management";
        String user = "root";
        String password = "mazeppa1881";

        HotelDAO hotelDAO = new HotelDAO(url, user, password);
        HotelView view = new HotelView();
        HotelController controller = new HotelController(view, hotelDAO);

        view.display(); // Show the GUI
    }
}
