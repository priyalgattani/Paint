import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class draw extends JPanel {
    private int width;
    private int height;
    private Color currentColor = Color.BLACK;
    private int currentBrushSize = 5;
    private List<Point> points = new ArrayList<>();
    private List<Integer> brushSizes = new ArrayList<>();
    private List<Color> colors = new ArrayList<>();

    public draw() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);

        JButton colorButton1 = new JButton("Black");
        colorButton1.addActionListener(e -> currentColor = Color.BLACK);
        JButton colorButton2 = new JButton("Red");
        colorButton2.addActionListener(e -> currentColor = Color.RED);
        JButton colorButton3 = new JButton("Blue");
        colorButton3.addActionListener(e -> currentColor = Color.BLUE);
        JButton brushSizeButton1 = new JButton("Small");
        brushSizeButton1.addActionListener(e -> currentBrushSize = 5);
        JButton brushSizeButton2 = new JButton("Medium");
        brushSizeButton2.addActionListener(e -> currentBrushSize = 10);
        JButton brushSizeButton3 = new JButton("Large");
        brushSizeButton3.addActionListener(e -> currentBrushSize = 20);
        JButton eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(e -> currentColor = getBackground());

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(brushSizeButton1);
        optionsPanel.add(brushSizeButton2);
        optionsPanel.add(brushSizeButton3);
        optionsPanel.add(eraserButton);
        optionsPanel.add(colorButton1);
        optionsPanel.add(colorButton2);
        optionsPanel.add(colorButton3);

        JButton colorChooserButton = new JButton("Choose Color");
        colorChooserButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(optionsPanel, "Choose Color", currentColor);
            if (newColor != null) {
                currentColor = newColor;
            }
        });
        optionsPanel.add(colorChooserButton);

        add(optionsPanel);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                points.add(e.getPoint());
                brushSizes.add(currentBrushSize);
                colors.add(currentColor);
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                points.add(e.getPoint());
                brushSizes.add(currentBrushSize);
                colors.add(currentColor);
                repaint();
            }
        });
    }

    public void setWH(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
    }

    public class PaintFrame extends JFrame {
        public PaintFrame(draw drawPanel) {
            setTitle("Paint");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            add(drawPanel);
            pack();
            setLocationRelativeTo(null);
        }
    }

    public void openPaint() {
        new PaintFrame(this).setVisible(true);
    }

    public void clear() {
        points.clear();
        brushSizes.clear();
        repaint();
    }

    public void undo() {
        if (!points.isEmpty()) {
            points.remove(points.size() - 1);
            brushSizes.remove(brushSizes.size() - 1);
            repaint();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < points.size(); i++) {
            g.setColor(colors.get(i));
            g.fillOval(points.get(i).x - brushSizes.get(i) / 2,
                    points.get(i).y - brushSizes.get(i) / 2,
                    brushSizes.get(i), brushSizes.get(i));
        }
    }

    public static void main(String[] args) {
        draw paint = new draw();
        paint.setWH(1200, 800);
        paint.openPaint();
    }

}
