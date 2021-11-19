package notificationupdater;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Notification {

    public static SystemTray tray = SystemTray.getSystemTray();

    public static TrayIcon createIcon() {
        //Obtain only one instance of the SystemTray object
        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        return new TrayIcon(image, "Click Tray");
    }

    public static void display(String title, String text) {
        TrayIcon icon = createIcon();
        addToTray(icon);
        icon.displayMessage(title, text, TrayIcon.MessageType.INFO);
    }

    public static void displayWithOnClick(String title, String text, Runnable runnable) {
        TrayIcon icon = createIcon();
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    System.out.println("Yeeet");
                }
            }
        });
        addToTray(icon);
        icon.displayMessage(title, text, TrayIcon.MessageType.INFO);
    }

    public static TrayIcon addToTray(TrayIcon icon) {
        try {
            tray.add(icon);
        } catch(AWTException e ) {
            e.printStackTrace();
        }
        return icon;
    }
}