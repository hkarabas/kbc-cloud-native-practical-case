package com.ezgroceries.shoppinglist.out;


import com.ezgroceries.shoppinglist.model.dto.CocktailDBResponse;
import com.ezgroceries.shoppinglist.model.entity.Cocktail;
import com.ezgroceries.shoppinglist.repo.CocktailRepo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "cocktailDBClient", url = "${cocktail-db.url}")
public interface CocktailDBClient {

    @GetMapping(value = "${cocktail-db.url.get-method}")
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);


    @Component
    class CocktailDBClientFallback implements CocktailDBClient {

        private final CocktailRepo cocktailRepo;

        public CocktailDBClientFallback(CocktailRepo cocktailRepo) {
            this.cocktailRepo = cocktailRepo;
        }

        @Override
        public CocktailDBResponse searchCocktails(String search) {
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

}
