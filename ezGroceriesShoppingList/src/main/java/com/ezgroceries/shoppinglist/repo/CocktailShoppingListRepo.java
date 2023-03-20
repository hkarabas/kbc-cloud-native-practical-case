package com.ezgroceries.shoppinglist.repo;

import com.ezgroceries.shoppinglist.model.entity.CocktailShoppingList;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailShoppingListRepo extends JpaRepository<CocktailShoppingList, UUID> {

}
