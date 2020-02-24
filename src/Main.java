import javax.swing.*;

public class Main extends JFrame {

    public Main(){
        setTitle("Змейка");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200+18,960+40);
        setLocationRelativeTo(null);
        setLocation(500,10);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
      new Main();
    }
}