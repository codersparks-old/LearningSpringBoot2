package uk.codersparks.learningspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * TODO: Add Javadoc
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id private String id;
    private String name;

}
