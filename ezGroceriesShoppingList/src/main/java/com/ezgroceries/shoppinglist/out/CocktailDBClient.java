package com.ezgroceries.shoppinglist.out;


import com.ezgroceries.shoppinglist.model.dto.CocktailDBResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "cocktailDBClient", url = "${cocktail-db.url}" ,fallback = CocktailDBClientFallbacks.class)
public interface CocktailDBClient {

    @GetMapping(value = "${cocktail-db.url.get-method}")
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);



}                          