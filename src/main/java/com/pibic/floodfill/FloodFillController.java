package com.pibic.floodfill;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
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
            @RequestParam("y") int y,
            @RequestParam(value = "method", defaultValue = "dfs") String method
    ) throws IOException {

        var resource = getClass().getResourceAsStream("/static/" + imageName);
        if (resource == null) {
            return ResponseEntity.badRequest().build();
        }

        BufferedImage image = ImageIO.read(resource);
        FloodFillService floodFill = new FloodFillService(Color.RED);

        if ("bfs".equalsIgnoreCase(method)) {
            floodFill.fillBFS(image, x, y);
        } else {
            floodFill.fillDFS(image, x, y);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }
}
