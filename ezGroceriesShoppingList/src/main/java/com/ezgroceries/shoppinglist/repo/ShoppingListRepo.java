package com.ezgroceries.shoppinglist.repo;

import com.ezgroceries.shoppinglist.model.entity.ShoppingListEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingList extends JpaRepository<ShoppingListEntity,String> {

    @Override
    List<ShoppingListEntity> findAll();

    Optional<ShoppingListEntity> findByName();



}
