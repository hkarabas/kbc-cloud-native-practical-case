package com.ezgroceries.shoppinglist.repo;

import com.ezgroceries.shoppinglist.model.entity.ShoppingList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepo extends JpaRepository<ShoppingList,String> {

    @Override
    List<ShoppingList> findAll();

    Optional<ShoppingList> findByName(String name);

    Optional<ShoppingList> findById(UUID s);
}
