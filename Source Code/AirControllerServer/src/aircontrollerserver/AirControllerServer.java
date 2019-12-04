/*
 * Server Application For Connecting and peform KeyEVENt and Mouse Event. 
 */
package aircontrollerserver;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Divyang
 */
public class AirControllerServer extends Thread {

    /**
     * @param args the command line arguments
     */
    private static Robot robot;

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException, AWTException {
        ServerSocket server = new ServerSocket(9999);
        robot = new Robot(); // for key Press event.

   InetAddress localhost = InetAddress.getLocalHost(); 
   String address = (localhost.getHostAddress()).trim();
        System.out.println(String.format("Host: %s\nPort: %s",address,server.getLocalPort())); // Show IPv4 Address.
        while (true) {
            Socket socket = server.accept(); 
            System.out.println("Connected with AirController Application."); // accept connection.
            InputStream inputStream = socket.getInputStream();
            handleSocketConnection(inputStream); // call threads. 
        }
    }

    private static void handleSocketConnection(final InputStream inputStream) throws IOException {
        Thread Mythread;
        Runnable myRunnable
                = new Runnable() {
            public void run() {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String text = "";
                try {
                    while ((text = br.readLine()) != null) {
                        JSONObject json = new JSONObject(text);// Creat JSON Object with given Input. 
                        process(json); // call the process thread.
                    }
                } catch (IOException e) {
                }
            }
        };
        Mythread = new Thread(myRunnable);
        Mythread.start();
    }

    private static void process(final JSONObject json) {

        Thread Mythread;
        Runnable myRunnable
                = new Runnable() {
            public void run() {
                String type = json.getString("type");
                boolean flag = false;
                switch (type) {
                    case "MOUSE_SCROLL": { //mouse scroll event 
                        JSONObject value = json.getJSONObject("value");
                        float y = value.getFloat("y");
                        robot.mouseWheel((int) y);
                    }
                    break;
                    case "MOUSE_LEFT_CLICK": { // peform left click on computer 
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                    break;
                    case "MOUSE_DOUBLE_CLICK": { // peform double click on computer
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                    break;
                    case "MOUSE_RIGHT_CLICK": { // peform right click on computer
                        robot.mousePress(InputEvent.BUTTON3_MASK);
                        robot.mouseRelease(InputEvent.BUTTON3_MASK);
                    }
                    break;
                    case "MOUSE_MOVE": { // peform mouse movement on computer 
                        JSONObject value = json.getJSONObject("value");

                        float dx = value.getFloat("x");
                        float dy = value.getFloat("y");

                        Point location = MouseInfo.getPointerInfo()
                                .getLocation(); // Get Mouse Curront location. 
                        double x = location.getX() - dx; // sub difference and assign in X and Y.
                        double y = location.getY() - dy;

                        robot.mouseMove((int) x, (int) y); //  assign new Mouse possition.
                    }
                    break;
                    case "KEYBOARD_INPUT": {
                        JSONObject value = json.getJSONObject("value");
     //                   System.out.println(value);
                        int keyCode = value.getInt("keyCode");
                        if (keyCode == 0) { // all mouse input and other input 
                            break;
                        }
                        if (keyCode == Constants.KEYCODE_DEL) { // Backspace key evvent.
                            robot.keyPress(KeyEvent.VK_BACK_SPACE);
                            robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                            break;
                        }
                        if (keyCode == Constants.KEYCODE_DPAD_LEFT) {  // for previous slide in presentation.
                            robot.keyPress(KeyEvent.VK_LEFT);
                            robot.keyRelease(KeyEvent.VK_LEFT);
                            break;
                        }
                        if (keyCode == Constants.KEYCODE_DPAD_RIGHT) { // for next slide in presentation.
                            robot.keyPress(KeyEvent.VK_RIGHT);
                            robot.keyRelease(KeyEvent.VK_RIGHT);
                            break;
                        }
                        if (keyCode == 900) { // For Blackscreen 
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.keyPress(KeyEvent.VK_B);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_B);
                            break;
                        }
                        if (keyCode == 901) { // For whiteScreen
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.keyPress(KeyEvent.VK_W);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_W);
                            break;
                        }
                        if (keyCode == 902) { //For Hide Drawing.
                            robot.keyPress(KeyEvent.VK_CONTROL);
                            robot.keyPress(KeyEvent.VK_M);
                            robot.keyRelease(KeyEvent.VK_CONTROL);
                            robot.keyRelease(KeyEvent.VK_M);
                            break;
                        }
                        if (keyCode == 903) { // For Clean Drawing 
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.keyPress(KeyEvent.VK_E);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_E);
                            break;
                        }
                        if (keyCode == 904) { // For Pen 
                            robot.keyPress(KeyEvent.VK_CONTROL);
                            robot.keyPress(KeyEvent.VK_P);
                            robot.keyRelease(KeyEvent.VK_CONTROL);
                            robot.keyRelease(KeyEvent.VK_P);
                            break;
                        }
                        if (keyCode == 905) { // For on  Leasor Pointer.
                            robot.keyPress(KeyEvent.VK_CONTROL);
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                             try {
                                sleep(100);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(AirControllerServer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            robot.keyRelease(KeyEvent.VK_CONTROL);
                            break;
                        }
                        if (keyCode == 909) { // For off Leasor Pointer 
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                            break;
                        }
                        if (keyCode == 910) {
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            break;
                        }
                        if (keyCode == 906) { // for Highlighter.
                            robot.keyPress(KeyEvent.VK_CONTROL);
                            robot.keyPress(KeyEvent.VK_I);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_I);
                            break;
                        }
                        if (keyCode == 907) {// Full screen
                            robot.keyPress(KeyEvent.VK_F5);
                            robot.keyRelease(KeyEvent.VK_F5);

                            break;
                        }
                        if (keyCode == 908) { // Exit 
                            robot.keyPress(KeyEvent.VK_ESCAPE);
                            robot.keyRelease(KeyEvent.VK_ESCAPE);
                            break;
                        }
                        boolean isShiftPressed = value.getBoolean("shift");
                        int event = KeyEvent.getExtendedKeyCodeForChar(keyCode);

                        if (isShiftPressed) {
                            robot.keyPress(KeyEvent.VK_SHIFT);
                        }
                        robot.keyPress(event);
                        robot.keyRelease(event);
                        if (isShiftPressed) {
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                        }
                    }
                    break;
                }
            }
        };
        Mythread = new Thread(myRunnable);
        Mythread.start();
    }
}
