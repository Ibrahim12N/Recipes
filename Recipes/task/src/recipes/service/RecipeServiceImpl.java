package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.entity.Recipe;
import recipes.entity.User;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService{

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long save(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
         recipeRepository.save(recipe);
         return recipe.getId();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            return recipeOptional.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "recipe not found");
        }
    }

    @Override
    public ResponseEntity<Object> deleteRecipeById(Long id, User user) {
        Optional<Recipe> recipe = Optional.ofNullable(getRecipeById(id));
        Optional<User> userOptional = Optional.ofNullable(recipe.get().getUser());
        if(recipe.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if(userOptional.isEmpty()){
            recipeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else if (recipe.get().getUser().equals(user)) {
            recipeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @Override
    public ResponseEntity<Object> updateRecipeById(Long id, Recipe newRecipe, User user) {
        Optional<Recipe> recipe = Optional.ofNullable(getRecipeById(id));
        Optional<User> userOptional = Optional.ofNullable(recipe.get().getUser());
        if(recipe.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else if (userOptional.get().getId().equals(user.getId())) {
            newRecipe.setId(id);
            save(newRecipe);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @Override
    public List<Recipe> findRecipesByCategory(String category) {
        return  recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    @Override
    public List<Recipe> findRecipesByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
