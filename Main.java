import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;

class Entity {
  int posX, posY;
  int velX, velY;

  public void update() {
    posX += velX;
    posY += velY;
  }

  public void render(Graphics g) {}
}

class Enemy extends Entity {
  @Override
  public void render(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(new Color(255, 0, 0));
    g2d.fillRect(posX, posY, 32, 32);
  }
}

class Player extends Entity {
  @Override
  public void render(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(new Color(0, 255, 0));
    g2d.fillRect(posX, posY, 32, 32);
  }
}

@SuppressWarnings("serial")
public class Main extends JPanel {
  public ArrayList<Entity> entityList = new ArrayList<Entity>();

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    // Loops through all entities
    for (Entity e : entityList) {
      e.render(g);
    }
  }

  public void update() {
    // Loops through all entities
    for (Entity e : entityList) {
      e.update();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    JFrame frame = new JFrame("Sample Frame");
    Main main = new Main();
    frame.add(main);
    frame.setSize(800, 600);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Player p = new Player();
    p.posX = 10;
    p.posY = 10;
    Enemy e = new Enemy();
    e.posX = 100;
    e.posY = 10;
    main.entityList.add(p);
    main.entityList.add(e);

    while (true) {
      main.update();
      main.repaint();
      Thread.sleep(10);
    }
  }
}
