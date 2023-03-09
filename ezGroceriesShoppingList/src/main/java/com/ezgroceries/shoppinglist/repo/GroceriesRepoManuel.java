package com.ezgroceries.shoppinglist.repo;


import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class GroceriesRepoManuel implements IGroceriesRepo {

    private List<ShoppingListDto> shoppingListDtos;

    public GroceriesRepoManuel() {
        this.shoppingListDtos = new ArrayList<>();
    }


    @Override
    public List<CocktailDto> getCockTailsStatics() {

        List<CocktailDto> cocktailDtoList = new ArrayList<>();

        cocktailDtoList.add(
                CocktailDto.Builder.newInstance().coctailId("23b3d85a-3928-41c0-a533-6538a71e17c4")
                        .name("Margerita")
                        .glass("Cocktail glass")
                        .instructions("Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..")
                        .image("https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg")
                        .ingredients(Set.of("Tequila",
                                "Triple sec",
                                "Lime juice",
                                "Salt")).build()
        );
        cocktailDtoList.add(
                CocktailDto.Builder.newInstance().coctailId("d615ec78-fe93-467b-8d26-5d26d8eab073")
                        .name("Blue Margerita"  )
                        .glass("Cocktail glass")
                        .instructions("Rub rim of  glass with lime juice. Dip rim in coarse salt..")
                        .image("https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg")
                        .ingredients(Set.of("Tequila",
                                "Blue Curacao",
                                "Lime juice",
                                "Salt")).build()
        );

        return cocktailDtoList;

    }


    @Override
    public Optional<CocktailDto> getCocktailByCocktailId(String cocktailId) {
         return getCockTailsStatics().stream().filter(cocktailDto -> cocktailDto.getCocktailId().equals(cocktailId)).findFirst();
    }

    @Override
    public Optional<CocktailDto> getByCocktailByName(String name) {
        return getCockTailsStatics().stream().filter(cocktailDto -> cocktailDto.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public void saveShoppingList(ShoppingListDto shoppingListDto) {
        shoppingListDtos.add(shoppingListDto);
    }

    @Override
    public List<ShoppingListDto> getShoppingList() {
        return shoppingListDtos;
    }

    @Override
    public Optional<ShoppingListDto> getByShoppingByName(String name) {
        return shoppingListDtos.stream().filter(shoppingListDto -> shoppingListDto.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public Optional<ShoppingListDto> getByShoppingById(String shoppingListId) {
        Optional<ShoppingListDto> shoppingListDtoOptional = shoppingListDtos.stream().filter(shoppingListDto -> shoppingListDto.getShoppingListId().equals(shoppingListId)).findFirst();
        System.out.println(shoppingListDtoOptional);
        return  shoppingListDtoOptional;
    }


}
