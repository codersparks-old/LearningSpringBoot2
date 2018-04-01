package uk.codersparks.learningspringboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.codersparks.learningspringboot.model.Image;

/**
 * TODO: Add Javadoc
 */

@RestController
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);



    @GetMapping("/api/images")
    Flux<Image> images() {

        return Flux.just(
                new Image("1", "learning-spring-boot-cover.jpg"),
                new Image("2", "learning-spring-boot-2nd-edition-cover.jpg"),
                new Image("3", "bazinga.png")
        );
    }

    @PostMapping("/api/images")
    Mono<Void> create(@RequestBody Flux<Image> images) {

        return images
                .map(image -> {
                    logger.info("We will save " + image.getName() + " to a Reactive database soon!");
                    return image;
                })
                .then();
    }
}
