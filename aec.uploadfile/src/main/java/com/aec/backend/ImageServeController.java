package com.aec.backend;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageServeController {
    private final String mainDir = "src/main/resources/static/images/main/";
    private final String thumbDir = "src/main/resources/static/images/thumb/";

    @GetMapping("/images/{filename}/main")
    public ResponseEntity<Resource> getMainImage(@PathVariable String filename) {
        return serveImage(mainDir, filename);
    }

    @GetMapping("/images/{filename}/thumb")
    public ResponseEntity<Resource> getThumbImage(@PathVariable String filename) {
        return serveImage(thumbDir, filename);
    }

    private ResponseEntity<Resource> serveImage(String dir, String filename) {
        try {
            Path filePath = Paths.get(dir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}