package com.ezgroceries.shoppinglist.out;

import com.ezgroceries.shoppinglist.model.dto.CocktailDBResponse;
import com.ezgroceries.shoppinglist.model.entity.Cocktail;
import com.ezgroceries.shoppinglist.repo.CocktailRepo;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CocktailDBClientFallbacks implements CocktailDBClient {


    private final Logger logger = LoggerFactory.getLogger(CocktailDBClientFallbacks.class);

    private final CocktailRepo cocktailRepo;

    public CocktailDBClientFallbacks(CocktailRepo cocktailRepo) {
        this.cocktailRepo = cocktailRepo;
    }


    @Override
    public CocktailDBResponse searchCocktails(String search) {
            logger.info("failback fail client");
            logger.info("call for fail ");

            List<Cocktail> cocktailEntities = cocktailRepo.findByNameContaining(search);

            CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
            cocktailDBResponse.setDrinks(cocktailEntities.stream().map(cocktail -> {
                CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource();
                drinkResource.setIdDrink(cocktail.getIdDrink());
                drinkResource.setStrDrink(cocktail.getName());
                //...omitted, all attributes need to be mapped
                return drinkResource;
            }).collect(Collectors.toList()));

            return cocktailDBResponse;

    }
}