package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "recipe")
public class Recipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "category")
    private String category;

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @NotEmpty
    @Size(min = 1)
    @Column(name = "ingredients")
    private String [] ingredients;

    @NotNull
    @NotEmpty
    @Size(min = 1)
    @Column(name = "directions")
    private  String [] directions;

    @Column(name = "date")
    private LocalDateTime date ;

    public Recipe() {
    }

    public Recipe(Long id, String name, String category, String description, @NotEmpty @Size(min = 1) String[] ingredients, @NotEmpty @Size(min = 1) String[] directions, LocalDateTime date, User user) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.date = date;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getDirections() {
        return directions;
    }

    public void setDirections(String[] directions) {
        this.directions = directions;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", directions=" + Arrays.toString(directions) +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}
