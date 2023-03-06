package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.repo.GroceriesRepoManuel;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroceriesService {

    private GroceriesRepoManuel groceriesRepoManuel;

    @Autowired
    public GroceriesService(GroceriesRepoManuel groceriesRepoManuel) {
        this.groceriesRepoManuel = groceriesRepoManuel;
    }

    public List<CocktailDto> getCocktailList() {
        return groceriesRepoManuel.getCockTailsStatics();
    }


    public ShoppingListDto createShoppingListDto(String name) {
        checkUniqShoppingListName(name);
        ShoppingListDto shoppingListDto =  ShoppingListDto.Builder.newInstance()
                .name(name)
                .shoppingListId(UUID.randomUUID().toString())
                .build();
        groceriesRepoManuel.saveShoppingList(shoppingListDto);
        return shoppingListDto;
    }

    public ShoppingListDto addCocktailtoShoppingList(String shoppingListId,String cocktailId) {
            ShoppingListDto shoppingListDto = groceriesRepoManuel.getByShoppingById(shoppingListId).orElseThrow(() -> {throw new IllegalArgumentException(String.format("Shopping not found %s",shoppingListId));});
            Set<String> ingredients = groceriesRepoManuel.getCocktailByCocktailId(cocktailId).orElseThrow( ()->{
                    throw new IllegalArgumentException("Cocktail not found");
                }).getIngredients();
            return  shoppingListDto.setCocktail(cocktailId,ingredients);
    }


    private void checkUniqShoppingListName(String name) {
         groceriesRepoManuel.getByShoppingByName(name).ifPresent(s-> {throw new IllegalArgumentException(String.format("there is same name shoopping list %s ",name));});
    }

    public ShoppingListDto getShoppingListDto(String shoppingListId) {
          return groceriesRepoManuel.getByShoppingById(shoppingListId).orElse(null);
    }

    public List<ShoppingListDto> shoppingListDtoList(){
        return groceriesRepoManuel.getShoppingList();
    }

}
