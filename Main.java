import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MouseCoordinates {
  private MouseCoordinates() {}

  public static int mouseX = 0;
  public static int mouseY = 0;
}

class Entity {
  float posX, posY;
  float velX, velY;
  float speed = 4;

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
    g2d.fillRect((int)posX, (int)posY, (int)32, (int)32);
  }
}

class Player extends Entity {
  @Override
  public void render(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(new Color(0, 255, 0));
    g2d.fillRect((int)posX, (int)posY, (int)32, (int)32);
  }
  @Override
  public void update() {
    int mouseX = MouseCoordinates.mouseX;
    int mouseY = MouseCoordinates.mouseY;
    double angle = Math.atan2((mouseY - posY), (mouseX - posX));
    velX = (int) (Math.cos(angle) * (double) speed);
    velY = (int) (Math.sin(angle) * (double) speed);
    float xDistance = mouseX - posX;
    float yDistance = mouseY - posY;
    float totalDistance = (float)Math.sqrt(xDistance*xDistance + yDistance*yDistance);
    if (totalDistance < 10) {
      velX = 0;
      velY = 0;
    }
    super.update();
  }
}

@SuppressWarnings("serial")
public class Main extends JPanel implements MouseMotionListener {
  public ArrayList<Entity> entityList = new ArrayList<Entity>();

  @Override
  public void mouseDragged(MouseEvent ev) {
  }

  @Override
  public void mouseMoved(MouseEvent ev) {
    MouseCoordinates.mouseX = ev.getX();
    MouseCoordinates.mouseY = ev.getY();
  }

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

    main.addMouseMotionListener(main);

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
