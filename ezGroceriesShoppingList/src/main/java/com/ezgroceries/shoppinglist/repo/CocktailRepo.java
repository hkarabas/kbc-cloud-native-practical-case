package com.ezgroceries.shoppinglist.repo;

import com.ezgroceries.shoppinglist.model.entity.Cocktail;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailRepo extends JpaRepository<Cocktail,String> {


    Optional<Cocktail> findById(UUID s);

    Optional<Cocktail> findByIdDrink(String s);

    @Override
    List<Cocktail> findAll();

    List<Cocktail> findByNameContaining(String name);


}
