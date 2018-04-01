package uk.codersparks.learningspringboot.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.codersparks.learningspringboot.service.ImageService;

import java.io.IOException;

/**
 * TODO: Add Javadoc
 */
@Controller
public class HomeController {

    private static final String BASE_BATH = "/images";
    private static final String FILENAME = "{filename:.+}";

    private final ImageService imageService;


    public HomeController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = BASE_BATH + "/" + FILENAME + "/raw", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<?>> oneRawImage(@PathVariable String filename) {
        return imageService.findOneImage(filename)
                .map(resource -> {
                    try {
                        return ResponseEntity.ok()
                                .contentLength(resource.contentLength())
                                .body(new InputStreamResource(resource.getInputStream()));
                    } catch (IOException e) {
                        return ResponseEntity.badRequest()
                                .body("Couldn't find " + filename + " => " + e.getLocalizedMessage());
                    }
                });
    }

    @PostMapping(value = BASE_BATH)
    public Mono<String> createFile(@RequestPart(name = "file") Flux<FilePart> files) {
        return imageService.createImage(files)
                .then(Mono.just("redirect:/"));
    }

    @DeleteMapping(BASE_BATH + "/" + FILENAME)
    public Mono<String> deleteFile(@PathVariable String filename) {
        return imageService.deleteImage(filename).then(Mono.just("redirect:/"));
    }

    @GetMapping("/")
    public Mono<String> index(Model model) {
        model.addAttribute("images", imageService.findAllImages());
        return Mono.just("index");
    }


}
