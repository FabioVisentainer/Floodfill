package com.pibic.floodfill;

// Importações do Spring para controller, requisições HTTP e tipos de mídia
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Importações para manipulação de imagens
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// Classe para construir respostas HTTP
import org.springframework.http.ResponseEntity;

@Controller
public class FloodFillController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Mapeia requisições POST para "/fill" para processar o Flood Fill
    @PostMapping("/fill")
    public ResponseEntity<byte[]> fillImage(
            @RequestParam("imageName") String imageName,
            @RequestParam("x") int x,
            @RequestParam("y") int y,
            @RequestParam(value = "method", defaultValue = "dfs") String method
    ) throws IOException {

        // Carrega a imagem da pasta resources/static
        var resource = getClass().getResourceAsStream("/static/" + imageName);
        if (resource == null) {
            // Retorna erro 400 caso a imagem não seja encontrada
            return ResponseEntity.badRequest().build();
        }

        // Lê a imagem carregada para um objeto BufferedImage
        BufferedImage image = ImageIO.read(resource);

        // Cria uma instância do serviço FloodFill com cor de preenchimento vermelha
        FloodFillService floodFill = new FloodFillService(Color.RED);

        // Escolhe qual metodo usar: BFS ou DFS
        if ("bfs".equalsIgnoreCase(method)) {
            // Preenche usando Fila (BFS)
            floodFill.fillBFS(image, x, y);
        } else {
            // Preenche usando Pilha (DFS)
            floodFill.fillDFS(image, x, y);
        }

        // Cria um fluxo de bytes para armazenar a imagem processada
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Escreve a imagem em PNG no fluxo de bytes
        ImageIO.write(image, "png", baos);

        // Retorna a imagem preenchida como resposta HTTP com tipo de mídia PNG
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }
}
