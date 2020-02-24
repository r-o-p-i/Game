import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 912;
    private final int DOT_SIZE = 48;
    private Image dot;
    private Image apple;
    private ArrayList<Integer> appleX = new ArrayList();
    private ArrayList<Integer> appleY = new ArrayList();
    private ArrayList<String> frukt = new ArrayList();
    private Integer[] x = new Integer[200];
    private Integer[] y = new Integer[200];
    private int dots;
    private Timer timer = new Timer(350, this);
    private boolean left = false;
    //private boolean timerOn = true;
    private boolean right = true, timerOn = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private ImageIcon iia, iid, Field, banana;
    private int score;
    private static int total;
    private int key;
    private int countCreateApple;
    private static int countLevel = 1;
    private boolean nexLevel = false;

    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        timer.stop();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    private void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = (48 * 3) - (i * DOT_SIZE);
            y[i] = 48;
        }
        if (timer.isCoalesce())
            timer.setDelay(350);
        timer.start();
        appleX.clear();
        appleY.clear();
        frukt.clear();
        createApple(1);
    }

    private void createApple(int a) {
        for (int i = 0; i < a; i++) {


            if (appleX.size() % 2 == 0) {
                frukt.add("banana.png");

            } else {
                frukt.add("aplle.png");
            }
            Integer newAppleX = new Random().nextInt(19) * DOT_SIZE;
            Integer newAppleY = new Random().nextInt(19) * DOT_SIZE;

            if (Arrays.asList(x).contains(newAppleX)) {
                newAppleX = new Random().nextInt(19) * DOT_SIZE;
                appleX.add(newAppleX);

            } else {
                appleX.add(newAppleX);
            }
            if (Arrays.asList(y).contains(newAppleY)) {
                newAppleX = new Random().nextInt(19) * DOT_SIZE;
                appleY.add(newAppleY);

            } else {
                appleY.add(newAppleX);
            }
            // System.out.println(new Random().nextInt(20) );
        }
        countCreateApple++;
    }

    public void loadImages() {
        //
        iia = createIcon("resource/apple.png");
        apple = iia.getImage();
        iid = createIcon("resource/dot-right.png");
        banana = createIcon("resource/banana.png");
        dot = iid.getImage();
        Field = createIcon("resource/AllField.png");


    }

    public void UpdateDot(String s) {
        //
        assert iid != null;

        iid = createIcon("resource/" + s);

        dot = iid.getImage();
    }

    protected static ImageIcon createIcon(String path) {
        URL imgURL = GameField.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("File not found " + path);
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            //System.out.println(timer.isRunning()+" "+timerOn+" "+key);

            if (!timerOn) {
                // System.out.println(timer.isRunning()+" "+timerOn+" "+key);
                timer.stop();
                String game = "    Пауза";
                int sizeFou = 30;
                Font font2 = new Font("Arial", Font.PLAIN, sizeFou);
                g.setFont(font2);
                score = dots - 3;
                int x = game.length();
                g.setColor(Color.CYAN);
                g.drawString(game, (SIZE / 2) - (x * sizeFou / 2), SIZE / 2 - 64);
                g.drawString(" Уровень " + countLevel, (SIZE / 2) - (x * sizeFou / 2), SIZE / 2 - 32);
                g.drawString(" Ваши очки " + score, (SIZE / 2) - (x * sizeFou / 2), SIZE / 2);


            } else {

                // timerOn = true;
                timer.start();

                g.drawImage(Field.getImage(), 0, 0, this);
                for (int i = 0; i < appleX.size(); i++) {

                    if (frukt.get(i).equals("banana.png")) {
                        g.drawImage(banana.getImage(), appleX.get(i), appleY.get(i), this);
                    } else {
                        g.drawImage(iia.getImage(), appleX.get(i), appleY.get(i), this);
                    }

                }

                // g.drawImage(iia.getImage(), appleX, appleY, this);

                for (int i = 0; i < dots; i++) {
                    String game = "Съедено фруктов ";
                    int sizeFount = 25;
                    Font font2 = new Font("Arial", Font.PLAIN, sizeFount);
                    g.setFont(font2);
                    score = dots - 3;
                    g.setColor(Color.GREEN);
                    g.drawString(" Уровень " + countLevel, 960, 25);
                    g.drawString(game + score, 960, 50);
                    g.drawString(" Ваши очки " + total, 960, 75);
                    g.drawImage(dot, x[i], y[i], this);

                }
            }
            if (nexLevel && countCreateApple > 0) {
                nexLevel = false;
                timer.stop();
                String game = "    Уровень ";
                int sizeFou = 30;
                Font font2 = new Font("Arial", Font.CENTER_BASELINE, sizeFou);
                g.setFont(font2);
                int x = game.length();
                g.setColor(Color.RED);
                g.drawString(countLevel + "", (SIZE / 2) - (x * sizeFou / 2) + game.length(), SIZE / 2 - 64);
                g.drawString(game, (SIZE / 2) - (x * sizeFou / 2), SIZE / 2 - 64);

                g.drawString(" Нажмите  ENTER Чтобы продолжить!!! ", (SIZE / 2) - (x * sizeFou / 2), SIZE / 2 - 32);
                g.drawString(" Ваши очки " + total, (SIZE / 2) - (x * sizeFou / 2), SIZE / 2);


            }
        } else {
            String gameover = "GAME OVER!";
            String mesage = "Ваш рекорд!" + (total + score);
            int sizeFount = 25;
            Font font = new Font("Arial", Font.ITALIC, sizeFount);
            g.setFont(font);
            g.setColor(Color.CYAN);
            int x = gameover.length();
            score = 0;
            total = 0;
            countLevel = 1;
            g.drawString(gameover, (SIZE / 2) - (x * sizeFount / 2), SIZE / 2);
            g.drawString(mesage, 400, 300);

        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
            String DOT_left = "dot-left.png";
            UpdateDot(DOT_left);
        }
        if (right) {
            x[0] += DOT_SIZE;
            String DOT_right = "dot-right.png";
            UpdateDot(DOT_right);
        }
        if (up) {
            y[0] -= DOT_SIZE;
            String DOT_up = "dot-up.png";
            UpdateDot(DOT_up);
        }
        if (down) {
            y[0] += DOT_SIZE;
            String DOT_down = "dot-down.png";
            UpdateDot(DOT_down);
        }

    }

    public void checkApple() {
        int i = 0;
        for (int j = 0; j < appleX.size(); j++) {
            if (x[0].equals(appleX.get(j)) && y[0].equals(appleY.get(j))) {

                appleX.remove(j);
                appleY.remove(j);
                frukt.remove(j);
                dots++;
                if (dots >= 50) {
                    nexLevel = true;
                    total = total + dots - 3;
                    countLevel++;

                }

                if (timer.getDelay() > 100) {
                    i = timer.getDelay() ;
                    // System.out.println(i);
                    timer.setDelay(i);
                } else {
                    timer.setDelay(100);
                }
                // if(dots%3==0){
                if (appleX.size() == 0) {
                    createApple(countCreateApple);

                }
                // createApple();
                //  }
            }
        }


    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 35 && x[0].equals(x[i]) && y[0].equals(y[i])) {
                inGame = false;
                nexLevel = false;
                countCreateApple = 0;
            }
        }


        if (x[0] > SIZE - 48) {
            x[0] = 0;
            //inGame = false;

        }
        if (x[0] < 0) {
            x[0] = SIZE - DOT_SIZE;
            // inGame = false;
        }
        if (y[0] > SIZE - 48) {
            y[0] = 0;
            //inGame = false;

        }
        if (y[0] < 0) {
            y[0] = SIZE - DOT_SIZE;
            //  inGame = false;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
            repaint();
        }

    }

    class FieldKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                if (timerOn) {
                    timerOn = false;
                } else {
                    timer.start();
                    timerOn = true;
                }
            }
            if (key == KeyEvent.VK_ENTER) {
                inGame = right = true;
                up = down = left = false;
                timer.restart();
                initGame();
            }
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = right = false;
            }


        }

    }
}


