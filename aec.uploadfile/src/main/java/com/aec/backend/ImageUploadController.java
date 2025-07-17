package com.aec.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {
    private final String mainDir = "src/main/resources/static/images/main/";
    private final String thumbDir = "src/main/resources/static/images/thumb/";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected");
        }
        try {
            // Tạo thư mục nếu chưa tồn tại
            File mainFolder = new File(mainDir);
            if (!mainFolder.exists())
                mainFolder.mkdirs();
            File thumbFolder = new File(thumbDir);
            if (!thumbFolder.exists())
                thumbFolder.mkdirs();

            // Lưu file gốc vào /main
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExt = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String storedFileName = System.currentTimeMillis() + fileExt;
            Path filePath = Paths.get(mainDir, storedFileName);
            Files.write(filePath, file.getBytes());

            // Tạo thumbnail và lưu vào /thumb
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage thumbnail = resizeImage(originalImage, 200, 200);
            Path thumbPath = Paths.get(thumbDir, storedFileName);
            ImageIO.write(thumbnail, fileExt.replace(".", ""), thumbPath.toFile());

            // Tạo URL trả về
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            String imageUrl = baseUrl + "/images/" + storedFileName + "/main";
            String thumbUrl = baseUrl + "/images/" + storedFileName + "/thumb";

            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);
            response.put("thumbnailUrl", thumbUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }
}