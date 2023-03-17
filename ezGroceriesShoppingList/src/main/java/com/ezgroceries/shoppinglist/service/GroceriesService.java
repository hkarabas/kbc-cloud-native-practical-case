package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.exception.ResourceNotFoundException;
import com.ezgroceries.shoppinglist.exception.UniqueRecordException;
import com.ezgroceries.shoppinglist.model.dto.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.model.entity.Cocktail;
import com.ezgroceries.shoppinglist.model.entity.CocktailShoppingList;
import com.ezgroceries.shoppinglist.model.entity.ShoppingList;
import com.ezgroceries.shoppinglist.out.CocktailDBClient;
import com.ezgroceries.shoppinglist.repo.CocktailRepo;
import com.ezgroceries.shoppinglist.repo.CocktailShoppingListRepo;
import com.ezgroceries.shoppinglist.repo.ShoppingListRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class GroceriesService {

    private CocktailRepo cocktailRepo;
    private ShoppingListRepo shoppingListRepo;
    private CocktailShoppingListRepo cocktailShoppingListRepo;
    private CocktailDBClient cocktailDBClient;




    @Autowired
    public GroceriesService(CocktailRepo cocktailRepo,ShoppingListRepo shoppingListRepo,
                CocktailShoppingListRepo cocktailShoppingListRepo,CocktailDBClient cocktailDBClient) {
        this.cocktailRepo = cocktailRepo;
        this.shoppingListRepo = shoppingListRepo;
        this.cocktailShoppingListRepo = cocktailShoppingListRepo;
        this.cocktailDBClient = cocktailDBClient;
    }

    private List<CocktailDto> getCocktailDtoListFromRemote(String name) {
     return    this.cocktailDBClient.searchCocktails(name).getDrinks().stream().map(cocktailDb->
            CocktailDto.Builder.newInstance()
                    .cocktailId(cocktailDb.getIdDrink())
                    .name(cocktailDb.getStrDrink())
                    .glass(cocktailDb.getStrGlass())
                    .instructions(cocktailDb.getStrInstructions())
                    .ingredients(cocktailDb.getIngredients()).build()
            ).collect(Collectors.toList());
    }

    public List<CocktailDto> getCocktailDtoList(String name) {
       return this.cocktailRepo.findByNameContaining(name).stream()
               .map(Cocktail::getCocktailDto)
               .collect(Collectors.toList());
    }

    public CocktailDto saveAndUpdateCocktailEntity(CocktailDto cocktailDto){
        return cocktailRepo.save(cocktailDto.getCocktailEntity()).getCocktailDto();
    }


    public ShoppingListDto createShoppingListDto(String name) {
        checkUniqShoppingListName(name);
        ShoppingListDto shoppingListDto =  ShoppingListDto.Builder.newInstance()
                .name(name)
                .shoppingListId(UUID.randomUUID().toString())
                .build();
        shoppingListRepo.save(shoppingListDto.getShoppingListEntity());
        return shoppingListDto;
    }

    public List<CocktailDto> getCocktailListDbAndRemote(String name) {

        List<CocktailDto> cocktailDtoList = cocktailRepo.findByNameContaining(name).stream().map(Cocktail::getCocktailDto).collect(Collectors.toList());
        List<CocktailDto> cocktailDtoListRemote = cocktailDBClient.searchCocktails(name)
                .getDrinks().stream().map(DrinkResource::cocktailDto).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(cocktailDtoList) && CollectionUtils.isEmpty(cocktailDtoListRemote)) {
            return Collections.emptyList();
        }

        if (CollectionUtils.isEmpty(cocktailDtoList) && !CollectionUtils.isEmpty(cocktailDtoListRemote)) {
            cocktailDtoListRemote.forEach(cocktailDto -> {
               Cocktail cocktail = cocktailDto.getCocktailEntity();
               cocktail.setId(UUID.randomUUID());
               cocktail = cocktailRepo.save(cocktail);
               cocktailDtoList.add(cocktail.getCocktailDto());
            });
            return cocktailDtoList;
        }

        if (!CollectionUtils.isEmpty(cocktailDtoList) && !CollectionUtils.isEmpty(cocktailDtoListRemote)) {
            Predicate<CocktailDto> notIn2 = s -> cocktailDtoList.stream().noneMatch(mc -> s.getCocktailId().equals(mc.getCocktailId()));
            List<CocktailDto> filterList = cocktailDtoListRemote.stream().filter(notIn2).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(filterList)) {
                filterList.forEach(cocktailDto -> {
                    Cocktail cocktail = cocktailDto.getCocktailEntity();
                    cocktail.setId(UUID.randomUUID());
                    cocktail = cocktailRepo.save(cocktail);
                    cocktailDtoList.add(cocktail.getCocktailDto());
                });
            }
        }
        return cocktailDtoList;
    }

    public ShoppingListDto addCocktailToShoppingList(String shoppingListId,Set<String> cocktailIds) {
            shoppingListRepo.findById(UUID.fromString(shoppingListId)).orElseThrow(() ->
            {
                throw new ResourceNotFoundException(String.format("Shopping not found %s",shoppingListId));
            });

            for (String cocktailId:cocktailIds) {
            cocktailRepo.findById(UUID.fromString(cocktailId)).orElseThrow( ()->{
                throw new ResourceNotFoundException(String.format("Cocktail not found %s",cocktailId));
            });
            CocktailShoppingList cocktailShoppingList = new CocktailShoppingList();
            cocktailShoppingList.setShoppingListId(UUID.fromString(shoppingListId));
            cocktailShoppingList.setCocktailId(UUID.fromString(cocktailId));
            cocktailShoppingListRepo.save (cocktailShoppingList);
        }
        return  shoppingListRepo.findById(UUID.fromString(shoppingListId)).orElseThrow(()-> {
            throw new ResourceNotFoundException(String.format("Shopping not found %s",shoppingListId));
        }).shoppingListDto();

    }


    private void checkUniqShoppingListName(String name) {
         shoppingListRepo.findByName(name).ifPresent(s-> {throw new UniqueRecordException(String.format("there is same name shopping list %s ",name));});
    }

    public ShoppingListDto getShoppingListDto(String shoppingListId) {
          return  shoppingListRepo.findById(UUID.fromString(shoppingListId))
                  .orElseThrow(()-> {throw new ResourceNotFoundException(String.format("Shopping not found %s",shoppingListId));}).shoppingListDto();
    }

    public List<ShoppingListDto> shoppingListDtoList(){
        return shoppingListRepo.findAll().stream().map(ShoppingList::shoppingListDto).collect(Collectors.toList());
    }

}
