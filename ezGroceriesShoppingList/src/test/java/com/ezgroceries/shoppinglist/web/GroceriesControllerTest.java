package com.ezgroceries.shoppinglist.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ezgroceries.shoppinglist.controller.GroceriesController;
import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.repo.GroceriesRepoManuel;
import com.ezgroceries.shoppinglist.service.GroceriesService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroceriesController.class)
public class GroceriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GroceriesService groceriesService;

    @MockBean
    GroceriesRepoManuel groceriesRepoManuel;

    private  List<CocktailDto> cocktailDtoList = new ArrayList<>() ;
    private  List<ShoppingListDto> shoppingListDtoList = new ArrayList<>();

    @BeforeEach
    private  void init() {
        cocktailDtoList.add(
                CocktailDto.Builder.newInstance().coctailId("23b3d85a-3928-41c0-a533-6538a71e17c4")
                        .name("Margerita")
                        .glass("Cocktail glass")
                        .instructions("Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..")
                        .image("https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg")
                        .ingredients(Set.of("Tequila",
                                "Triple sec",
                                "Lime juice",
                                "Salt")).build()
        );
        cocktailDtoList.add(
                CocktailDto.Builder.newInstance().coctailId("d615ec78-fe93-467b-8d26-5d26d8eab073")
                        .name("Blue Margerita"  )
                        .glass("Cocktail glass")
                        .instructions("Rub rim of  glass with lime juice. Dip rim in coarse salt..")
                        .image("https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg")
                        .ingredients(Set.of("Tequila",
                                "Blue Curacao",
                                "Lime juice",
                                "Salt")).build()
        );
        shoppingListDtoList.add(ShoppingListDto.Builder.newInstance()
                .name("Stephanie's birthday")
                .shoppingListId("4ba92a46-1d1b-4e52-8e38-13cd56c7224c").build());
        shoppingListDtoList.add(ShoppingListDto.Builder.newInstance()
                .name("My Birthday")
                .shoppingListId("6c7d09c2-8a25-4d54-a979-25ae779d2465").build());
    }

    @Test
    public void getCocktailList() throws Exception {

        when(groceriesService.getCocktailList()).thenReturn(cocktailDtoList);

        mockMvc.perform(get("/cocktails").param("search","Russian"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].cocktailId",is("23b3d85a-3928-41c0-a533-6538a71e17c4")))
                .andExpect(jsonPath("$[0].name" ,is("Margerita")))
                .andExpect(jsonPath("$[1].cocktailId").value("d615ec78-fe93-467b-8d26-5d26d8eab073"))
                .andExpect(jsonPath("$[1].name").value("Blue Margerita"));

        verify(groceriesService).getCocktailList();
    }

    @Test
    public  void createShoppingList() throws Exception {
        when(groceriesService.createShoppingListDto(any(String.class))).thenReturn(shoppingListDtoList.get(0));
        mockMvc.perform(post("/shopping-lists")
                .param("name","Stephanie's birthday"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location","http://localhost/shopping-lists/4ba92a46-1d1b-4e52-8e38-13cd56c7224c"));
        verify(groceriesService).createShoppingListDto("Stephanie's birthday");
    }


    @Test
    public void createShoppingListEmptyName() throws Exception {
        mockMvc.perform(post("/shopping-lists")
                        .param("name", Strings.EMPTY))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("shoppingLis name is must be not null"));
    }

    @Test
    public void getShoppingListDtoById() throws Exception {
        when(groceriesService.getShoppingListDto(any(String.class))).thenReturn(shoppingListDtoList.get(0));
        mockMvc.perform(get("/shopping-lists/{shoppingListId}","4ba92a46-1d1b-4e52-8e38-13cd56c7224c"))
                .andExpect(status().isOk())
                .andExpect(jsonPath( "$.name").value("Stephanie's birthday"));
       verify(groceriesService).getShoppingListDto(any(String.class));
    }

    @Test
    public void getShoppingListDtoByIdNotFound() throws Exception {
        when(groceriesRepoManuel.getByShoppingById(any(String.class))).thenReturn(Optional.empty());
        mockMvc.perform(get("/shopping-lists/{shoppingListId}","13cd56c7224c"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath( "$.message").value(String.format("Shopping not found %s","13cd56c7224c")));
        verify(groceriesService).getShoppingListDto(any(String.class));
    }


}
