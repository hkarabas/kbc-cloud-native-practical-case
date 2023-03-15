package com.ezgroceries.shoppinglist.repo;

import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.entity.CocktailEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailRepo extends JpaRepository<CocktailEntity,String> {

    @Override
    Optional<CocktailEntity> findById(String s);

    @Override
    List<CocktailEntity> findAll();

    List<CocktailEntity> findByNameContaining(String name);


}
