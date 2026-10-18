package com.chatop.chatop.storage;

import com.chatop.chatop.exception.ImageStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageStorageService {

    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;

    private static final Map<String, String> ALLOWED_FORMATS = Map.of(
            "jpeg", ".jpg",
            "png", ".png"
    );

    private final Path uploadDirectory;

    public ImageStorageService(
            @Value("${app.upload.dir:uploads/rentals}") String uploadDir
    ) {
        this.uploadDirectory = Path.of(uploadDir)
                .toAbsolutePath()
                .normalize();
    }

    public String store(MultipartFile file) {
        validateFile(file);

        String extension = detectExtension(file);
        String filename = generateFilename(extension);

        saveFile(file, filename);

        return "/uploads/rentals/" + filename;
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ImageStorageException("L'image est obligatoire");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ImageStorageException("L'image ne doit pas dépasser 5 Mo");
        }
    }

    private String detectExtension(MultipartFile file) {
        try (InputStream input = file.getInputStream()) {

            BufferedImage image = ImageIO.read(input);

            if (image == null) {
                throw new ImageStorageException("Le fichier n'est pas une image valide");
            }

        } catch (IOException e) {
            throw new ImageStorageException("Impossible de lire l'image", e);
        }

        try (var imageInput = ImageIO.createImageInputStream(file.getInputStream())) {
            if (imageInput == null) {
                throw new ImageStorageException("Impossible de lire le format de l'image");
            }

            var readers = ImageIO.getImageReaders(imageInput);

            if (!readers.hasNext()) {
                throw new ImageStorageException("Format d'image non reconnu");
            }

            var reader = readers.next();

            try {
                String format = reader.getFormatName().toLowerCase();

                String extension = ALLOWED_FORMATS.get(format);

                if (extension == null) {
                    throw new ImageStorageException("Format d'image non autorisé");
                }

                return extension;
            } finally {
                reader.dispose();
            }

        } catch (IOException e) {
            throw new ImageStorageException("Impossible de détecter le format de l'image", e);
        }
    }

    private String generateFilename(String extension) {
        return UUID.randomUUID() + extension;
    }

    private void saveFile(MultipartFile file, String filename) {
        try {
            Files.createDirectories(uploadDirectory);

            Path destination = uploadDirectory.resolve(filename);

            try (InputStream input = file.getInputStream()) {
                Files.copy(input, destination);
            }

        } catch (IOException e) {
            throw new ImageStorageException("Impossible d'enregistrer l'image", e);
        }
    }
}