import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BouncingBallApp {
    private JFrame frame;
    private BouncingBallPanel ballPanel;

    public BouncingBallApp() {
        frame = new JFrame("Рух кола з ефектом пружного стиснення");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ballPanel = new BouncingBallPanel();
        frame.add(ballPanel);

        Timer timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballPanel.moveBall();
            }
        });
        timer.start();

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BouncingBallApp();
            }
        });
    }
}

class BouncingBallPanel extends JPanel {
    private int ballSize = 30;
    private int ballX = 50;
    private int ballY = 50;
    private int speedX = 5;
    private int speedY = 5;

    public void moveBall() {
        ballX += speedX;
        ballY += speedY;

        if (ballX < 0 || ballX + ballSize > getWidth()) {
            speedX = -speedX; // Зміна напрямку при торканні горизонтальних границь
        }

        if (ballY < 0 || ballY + ballSize > getHeight()) {
            speedY = -speedY; // Зміна напрямку при торканні вертикальних границь
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillOval(ballX, ballY, ballSize, ballSize);
    }
}
