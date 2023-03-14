package com.ezgroceries.shoppinglist.repo;

import com.ezgroceries.shoppinglist.model.entity.CocktailShoppingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailShoppingListRepo extends JpaRepository<CocktailShoppingListEntity,String> {

}
