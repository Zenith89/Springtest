package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IndexControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    Model model;
    IndexController indexController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);

    }

    @Test
    public void getIndexPage() {

        //given
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipeSet.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipeSet);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        String viewName = indexController.getIndexPage(model);
        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
//        verify(model,times(1)). addAttribute(eq("recipes"), anySet());
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setIncontroller = argumentCaptor.getValue();
        assertEquals(2, setIncontroller.size());

    }
}