package uk.codersparks.learningspringboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Paths;

import static uk.codersparks.learningspringboot.service.ImageService.UPLOAD_ROOT;

/**
 * TODO: Add Javadoc
 */
@Configuration
public class CmdLineRunners {

    private static final Logger logger = LoggerFactory.getLogger(CmdLineRunners.class);


    @Bean
    public CommandLineRunner initialiseFiles() {
        return (args) -> {


            if( ! Files.exists(Paths.get(UPLOAD_ROOT))) {
                logger.info("******** Creating folder for image upload *******");
                Files.createDirectory(Paths.get(UPLOAD_ROOT));
            }
        };
    }
}
