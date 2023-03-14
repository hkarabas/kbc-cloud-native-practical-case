package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.exception.ResourceNotFoundException;
import com.ezgroceries.shoppinglist.exception.UniqueRecordException;
import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.out.CocktailDBClient;
import com.ezgroceries.shoppinglist.repo.CocktailRepo;
import com.ezgroceries.shoppinglist.repo.CocktailShoppingListRepo;
import com.ezgroceries.shoppinglist.repo.GroceriesRepoManuel;
import com.ezgroceries.shoppinglist.repo.ShoppingListRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroceriesService {

    private CocktailRepo cocktailRepo;
    private ShoppingListRepo shoppingListRepo;
    private CocktailShoppingListRepo cocktailShoppingListRepo;
    private CocktailDBClient cocktailDBClient;




    @Autowired
    public GroceriesService(CocktailRepo cocktailRepo,ShoppingListRepo shoppingListRepo,
                CocktailShoppingListRepo cocktailShoppingListRepo,CocktailDBClient cocktailDBClient) {
        this.cocktailRepo = cocktailRepo;
        this.shoppingListRepo = shoppingListRepo;
        this.cocktailShoppingListRepo = cocktailShoppingListRepo;
        this.cocktailDBClient = cocktailDBClient;
    }

    private List<CocktailDto> getCocktailDtoListFromRemote(String name) {
     return    this.cocktailDBClient.searchCocktails(name).getDrinks().stream().map(cocktailDb->
            CocktailDto.Builder.newInstance().coctailId(cocktailDb.getIdDrink())
                   .name(cocktailDb.getStrDrink())
                   .glass(cocktailDb.getStrGlass())
                   .instructions(cocktailDb.getStrInstructions())
                   .ingredients(cocktailDb.getIngredients()).build()
            ).collect(Collectors.toList());
    }

    private List<CocktailDto> getCocktailDtoList(String name) {
        return this.shoppingListRepo.findByName()
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

    public ShoppingListDto addCocktailToShoppingList(String shoppingListId,String cocktailId) {
            ShoppingListDto shoppingListDto = groceriesRepoManuel.getByShoppingById(shoppingListId).orElseThrow(() -> {throw new ResourceNotFoundException(String.format("Shopping not found %s",shoppingListId));});
            Set<String> ingredients = groceriesRepoManuel.getCocktailByCocktailId(cocktailId).orElseThrow( ()->{
                    throw new ResourceNotFoundException(String.format("Cocktail not found %s",cocktailId));
                }).getIngredients();
            return  shoppingListDto.setCocktail(cocktailId,ingredients);
    }


    private void checkUniqShoppingListName(String name) {
         groceriesRepoManuel.getByShoppingByName(name).ifPresent(s-> {throw new UniqueRecordException(String.format("there is same name shoopping list %s ",name));});
    }

    public ShoppingListDto getShoppingListDto(String shoppingListId) {
          return  groceriesRepoManuel.getByShoppingById(shoppingListId)
                  .orElseThrow(()-> {throw new ResourceNotFoundException(String.format("Shopping not found %s",shoppingListId));});
    }

    public List<ShoppingListDto> shoppingListDtoList(){
        return groceriesRepoManuel.getShoppingList();
    }

}
