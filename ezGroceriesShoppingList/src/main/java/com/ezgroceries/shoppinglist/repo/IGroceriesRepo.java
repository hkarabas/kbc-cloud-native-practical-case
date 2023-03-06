package com.ezgroceries.shoppinglist.repo;

import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import java.util.List;
import java.util.Optional;

public interface IGroceriesRepo {

    public List<CocktailDto> getCockTailsStatics();

    public Optional<CocktailDto> getCocktailByCocktailId(String cocktailId);

    public Optional<CocktailDto> getByCocktailByName(String name);

    public void saveShoppingList(ShoppingListDto shoppingListDto);

    public List<ShoppingListDto> getShoppingList();

    Optional<ShoppingListDto> getByShoppingByName(String name);

    Optional<ShoppingListDto> getByShoppingById(String shoppingListId);
}
