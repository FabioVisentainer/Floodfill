package com.pibic.floodfill;

import java.awt.Color;
import java.awt.image.BufferedImage;

// Classe responsável por realizar o Flood Fill em uma imagem
public class FloodFillService {

    // Cor que será usada para preencher a área
    private final int replacementColor;

    // Construtor recebe a cor de preenchimento
    public FloodFillService(Color replacement) {
        // Armazena como inteiro RGB
        this.replacementColor = replacement.getRGB();
    }

    // Metodo de preenchimento usando Pilha (DFS - Depth First Search)
    // Vai "mergulhando" em um caminho até não ter mais vizinhos, depois volta.
    // Resultado: o preenchimento segue um caminho contínuo, como se “raspasse” profundamente.
    // Início -> pixel (x,y)
    // Adiciona vizinhos à pilha
    // Pop último vizinho -> preenche
    // Pop próximo vizinho -> preenche
    public void fillDFS(BufferedImage img, int x, int y) {
        int targetColor = img.getRGB(x, y); // Cor do pixel inicial
        if (isSameColor(targetColor, replacementColor)) return; // Se já estiver preenchida, retorna

        Pilha<int[]> stack = new Pilha<>(); // Cria pilha própria
        stack.push(new int[]{x, y});        // Pixel clicado (inicial)

        while (!stack.isEmpty()) {
            int[] p = stack.pop();          // pega o último pixel inserido
            int px = p[0], py = p[1];

            // Ignora pixels fora da imagem
            if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;

            int currentColor = img.getRGB(px, py); // Cor do pixel atual
            if (isSameColor(currentColor, targetColor)) {
                img.setRGB(px, py, replacementColor); // Preenche o pixel

                // Adiciona pixels vizinhos à pilha (DFS)
                stack.push(new int[]{px + 1, py});
                stack.push(new int[]{px - 1, py});
                stack.push(new int[]{px, py + 1});
                stack.push(new int[]{px, py - 1});
            }
        }
    }

    // Metodo de preenchimento usando Fila (BFS - Breadth First Search)
    // Preenche pixel clicado, depois todos os vizinhos imediatos, depois os vizinhos dos vizinhos, expandindo em camadas.
    // Resultado: o preenchimento se espalha uniformemente em volta do ponto inicial.
    // Início -> pixel (x,y)
    // Adiciona vizinhos à fila
    // Dequeue primeiro vizinho -> preenche
    // Adiciona vizinhos à fila
    // Dequeue próximo vizinho -> preenche
    public void fillBFS(BufferedImage img, int x, int y) {
        int targetColor = img.getRGB(x, y); // Cor do pixel inicial
        if (isSameColor(targetColor, replacementColor)) return; // Se já estiver preenchida, retorna

        Fila<int[]> queue = new Fila<>(); // Cria fila própria
        queue.enqueue(new int[]{x, y});   // Pixel clicado (inicial)

        while (!queue.isEmpty()) {
            int[] p = queue.dequeue();    // pega o primeiro pixel inserido
            int px = p[0], py = p[1];

            // Ignora pixels fora da imagem
            if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;

            int currentColor = img.getRGB(px, py); // Cor do pixel atual
            if (isSameColor(currentColor, targetColor)) {
                img.setRGB(px, py, replacementColor); // Preenche o pixel

                // Adiciona pixels vizinhos à fila (BFS)
                queue.enqueue(new int[]{px + 1, py});
                queue.enqueue(new int[]{px - 1, py});
                queue.enqueue(new int[]{px, py + 1});
                queue.enqueue(new int[]{px, py - 1});
            }
        }
    }

    // Metodo auxiliar para comparar cores com tolerância
    private boolean isSameColor(int c1, int c2) {
        Color color1 = new Color(c1);
        Color color2 = new Color(c2);

        // Diferença absoluta entre os componentes RGB
        int diff = Math.abs(color1.getRed() - color2.getRed()) +
                Math.abs(color1.getGreen() - color2.getGreen()) +
                Math.abs(color1.getBlue() - color2.getBlue());

        // Retorna true se as cores forem "semelhantes"
        return diff < 50;
    }
}
