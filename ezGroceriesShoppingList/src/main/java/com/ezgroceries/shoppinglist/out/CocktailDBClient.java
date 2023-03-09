package com.ezgroceries.shoppinglist.out;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "cocktailDBClient", url = "#{cocktaildb.url}")
public interface CocktailDBClient {

}
