package recipes.service;

import org.springframework.http.ResponseEntity;
import recipes.entity.Recipe;
import recipes.entity.User;

import java.util.List;

public interface RecipeService {
    Long save (Recipe recipe);
    Recipe getRecipeById (Long id);
    ResponseEntity<Object> deleteRecipeById (Long id, User user);
    ResponseEntity<Object> updateRecipeById (Long id, Recipe recipe, User user);
    List<Recipe> findRecipesByCategory (String category);
    List<Recipe> findRecipesByName (String name);


}
