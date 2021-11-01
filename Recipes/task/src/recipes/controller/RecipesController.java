package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import recipes.entity.Recipe;
import recipes.entity.User;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/recipe")
public class RecipesController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping("new")
    public Map<String, Long> addRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal User user) {
        recipe.setUser(user);
        recipeService.save(recipe);
        return Collections.singletonMap("id", recipe.getId());
    }

    @GetMapping("{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRecipe (@PathVariable Long id, @AuthenticationPrincipal User user) {
        ResponseEntity<Object> responseEntity = recipeService.deleteRecipeById(id,user);
        return responseEntity;
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateRecipe (@PathVariable Long id, @Valid @RequestBody Recipe recipe, @AuthenticationPrincipal User user) {
            ResponseEntity<Object> responseEntity= recipeService.updateRecipeById(id,recipe, user);
            return responseEntity;
    }

    @GetMapping(path = "search", params = "category")
    public List<Recipe> getRecipesByCategory (@RequestParam String category){
        return recipeService.findRecipesByCategory(category);
    }

    @GetMapping(path = "search", params = "name")
    public List<Recipe> getRecipesByName (@RequestParam String name){
        return recipeService.findRecipesByName(name);
    }

}
