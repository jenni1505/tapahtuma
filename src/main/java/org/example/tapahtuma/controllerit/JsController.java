package org.example.tapahtuma.controllerit;



import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("/js")
public class JsController {

    @GetMapping("/main.js")
    public ResponseEntity<String> serveJavaScript() throws IOException {
        // Lue tiedosto resurssikansiosta
        // Lue tiedosto resurssikansiosta

        String jsContent;
        Resource resource = new ClassPathResource("static/js/main.js");
        try (InputStream inputStream = resource.getInputStream()) {
            jsContent = new String(inputStream.readAllBytes());
        }

        return ResponseEntity.ok()
                .header("Content-Type", "application/javascript")
                .body(jsContent);
    }
}

