package uk.codersparks.learningspringboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.codersparks.learningspringboot.model.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TODO: Add Javadoc
 */
@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    public static String UPLOAD_ROOT = "/tmp/upload-dir";

    private final ResourceLoader resourceLoader;

    public ImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Flux<Image> findAllImages() {
        try {
            return Flux.fromIterable(
                    Files.newDirectoryStream(Paths.get(UPLOAD_ROOT)))
                    .map(path -> new Image(Integer.toString(path.hashCode()), path.getFileName().toString()));
        } catch (IOException e) {
            return Flux.empty();
        }
    }

    public Mono<Resource> findOneImage(String filename) {
        return Mono.fromSupplier(() -> resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename));
    }

    public Mono<Void> createImage(Flux<FilePart> files) {

        logger.info("Filename uploaded: ", files);
        return files.flatMap(filePart -> {
            File file = Paths.get(UPLOAD_ROOT, filePart.filename()).toFile();
            try {
                file.createNewFile();
                return filePart.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).then();
    }

    public Mono<Void> deleteImage(String filename) {
        return Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
