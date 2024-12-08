import view.MenuView;
import controller.MenuController;
import model.BookingDAO;
import model.RoomDAO;

public class MainApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MenuView view = new MenuView();
            BookingDAO bookingDAO = new BookingDAO();
            RoomDAO roomDAO = new RoomDAO();
            new MenuController(view, bookingDAO, roomDAO);
            view.display();
        });
    }
}