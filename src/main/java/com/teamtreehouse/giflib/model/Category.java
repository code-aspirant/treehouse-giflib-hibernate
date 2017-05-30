package com.teamtreehouse.giflib.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id // All other non-transient fields are assumed to be columns
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 12)
    private String name;

    /**
     * Must be not null
     * Pattern of # plus 0-9, a-f, and or A-F for 6 places
     */
    @NotNull
    @Pattern(regexp = "#[0-9a-fA-F]{6}")
    private String colorCode;

    // One category object can be related to many Gif objects
    // mappedBy: The category field of the Gif entity that will be used to map the category of each gif
    // and by default use the Category's id field and therefore creating a category_id field in the gif table
    @OneToMany(mappedBy = "category")
    private List<Gif> gifs = new ArrayList<>();

    public Category(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public List<Gif> getGifs() {
        return gifs;
    }

}
