package com.pibic.floodfill;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class FloodFillService {

    private final int replacementColor;

    public FloodFillService(Color replacement) {
        this.replacementColor = replacement.getRGB();
    }

    // Pilha
    public void fillDFS(BufferedImage img, int x, int y) {
        int targetColor = img.getRGB(x, y);
        if (isSameColor(targetColor, replacementColor)) return;

        Pilha<int[]> stack = new Pilha<>();
        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] p = stack.pop();
            int px = p[0], py = p[1];

            if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;

            int currentColor = img.getRGB(px, py);
            if (isSameColor(currentColor, targetColor)) {
                img.setRGB(px, py, replacementColor);
                stack.push(new int[]{px + 1, py});
                stack.push(new int[]{px - 1, py});
                stack.push(new int[]{px, py + 1});
                stack.push(new int[]{px, py - 1});
            }
        }
    }

    // Fila
    public void fillBFS(BufferedImage img, int x, int y) {
        int targetColor = img.getRGB(x, y);
        if (isSameColor(targetColor, replacementColor)) return;

        Fila<int[]> queue = new Fila<>();
        queue.enqueue(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] p = queue.dequeue();
            int px = p[0], py = p[1];

            if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;

            int currentColor = img.getRGB(px, py);
            if (isSameColor(currentColor, targetColor)) {
                img.setRGB(px, py, replacementColor);
                queue.enqueue(new int[]{px + 1, py});
                queue.enqueue(new int[]{px - 1, py});
                queue.enqueue(new int[]{px, py + 1});
                queue.enqueue(new int[]{px, py - 1});
            }
        }
    }

    private boolean isSameColor(int c1, int c2) {
        Color color1 = new Color(c1);
        Color color2 = new Color(c2);
        int diff = Math.abs(color1.getRed() - color2.getRed()) +
                Math.abs(color1.getGreen() - color2.getGreen()) +
                Math.abs(color1.getBlue() - color2.getBlue());
        return diff < 50;
    }
}
