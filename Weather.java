import javax.swing.*;

public class Weather {
    public static String PATH = "C:\\Users\\Marian\\IdeaProjects\\Weather\\src\\output\\";
    public static void run(final JFrame frame, final int width, final int height){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setTitle("Weather Comparator");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setSize(width,height);
                frame.setVisible(true);
            }
        });
    }
    public static void main(String args[]) {;
        run(new WeatherGUI(), 640,480);
        //SYPIE SIE PRZY POLSKICH ZNAKACH
        //LISTA PLIKOW SIE NIE ODSWIEZA
        //poprawic pogodnie , pobiera 1 znak za mało (nie ma "-")
        //meteoprog 1 znak wiecej na koncu

    }
}