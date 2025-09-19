package com.pibic.floodfill;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.http.ResponseEntity;

@Controller
public class FloodFillController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/fill")
    public ResponseEntity<byte[]> fillImage(
            @RequestParam("imageName") String imageName,
            @RequestParam("x") int x,
            @RequestParam("y") int y) throws IOException {

        // Carrega imagem da pasta static
        var resource = getClass().getResourceAsStream("/static/" + imageName);
        if (resource == null) {
            return ResponseEntity.badRequest().build();
        }
        BufferedImage image = ImageIO.read(resource);

        int targetColor = image.getRGB(x, y);
        int replacementColor = Color.RED.getRGB();

        floodFill(image, x, y, targetColor, replacementColor);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }

    private void floodFill(BufferedImage img, int x, int y, int targetColor, int replacementColor) {
        if (isSameColor(targetColor, replacementColor)) return;

        Pilha<int[]> stack = new Pilha<>();
        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] p = stack.pop();
            int px = p[0];
            int py = p[1];

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

    private boolean isSameColor(int c1, int c2) {
        Color color1 = new Color(c1);
        Color color2 = new Color(c2);
        int diff = Math.abs(color1.getRed() - color2.getRed()) +
                Math.abs(color1.getGreen() - color2.getGreen()) +
                Math.abs(color1.getBlue() - color2.getBlue());
        return diff < 50;
    }
}