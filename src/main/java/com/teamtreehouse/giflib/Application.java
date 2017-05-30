package com.teamtreehouse.giflib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/*Using a Custom Validator on the Uploaded GIF

Adding validation to the GIF can be a bit tricky, with one reason being
 that the MultipartFile is not part of the entity,
  but should definitely be validated. Currently our Gif entity uses a byte[]
  field for the GIF's binary image data. We're capturing
  a MultipartFile object in the controller method with
   a @RequestParam parameter. A nice alternative would be
   to include the MultipartFile object as a field in the entity,
    but we don't want this object to be persisted to the database,
    so we can mark it as @Transient (which means it'll exist in our
     application, but won't be saved to or read from the database).

The advantage of including it as a field is that we
 can then validate the entire Gif entity (with the file),
  and not have to worry about that logic in the controller
   methods for uploading a new GIF and updating an existing GIF.
    In order to do this, though, we'll need to write a custom validator.*/


@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
