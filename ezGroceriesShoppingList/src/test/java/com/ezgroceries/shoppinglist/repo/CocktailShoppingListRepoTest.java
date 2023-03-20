package com.ezgroceries.shoppinglist.repo;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.model.entity.Cocktail;
import com.ezgroceries.shoppinglist.model.entity.CocktailShoppingList;
import com.ezgroceries.shoppinglist.model.entity.ShoppingList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
public class CocktailShoppingListRepoTest {


    @Autowired
    private  CocktailRepo cocktailRepo;

    @Autowired
    private  ShoppingListRepo shoppingListRepo;

    @Autowired
    private CocktailShoppingListRepo cocktailShoppingListRepo;

    private UUID coctailID;
    private CocktailDto cocktaildto;
    private Cocktail cocktail;


    private UUID shoppingListId;
    private ShoppingListDto shoppingListDto;
    private ShoppingList shoppingList;

    private CocktailShoppingList cocktailShoppingList;


    @BeforeEach
    private  void init() {
        coctailID = UUID.randomUUID();
        shoppingListId = UUID.randomUUID();

        cocktaildto = CocktailDto.Builder.newInstance()
                .cocktailId("11102")
                .name("Black Russian")
                .glass("Cocktail Glass")
                .ingredients(Set.of("Lemon Juice",
                        "Creme de Cassis",
                        "Vodka")).build();

        cocktail = cocktaildto.getCocktailEntity();
        cocktail.setId(coctailID);

        shoppingListId = UUID.randomUUID();
        shoppingListDto = ShoppingListDto.Builder.newInstance()
                .name("Stephanie's birthday")
                .ingredients(Set.of("Lemon Juice",
                        "Creme de Cassis",
                        "Vodka")).build();
        shoppingList = shoppingListDto.getShoppingListEntity();
        shoppingList.setId(shoppingListId);
        cocktailShoppingList = new CocktailShoppingList();
        cocktailShoppingList.setCocktailId(coctailID);
        cocktailShoppingList.setShoppingListId(shoppingListId);

    }


    @Test
    void should_be_get_zero_record() {
        Optional<CocktailShoppingList> shoppingListOptional = cocktailShoppingListRepo.findById(UUID.randomUUID());
        assertEquals(shoppingListOptional.isEmpty(),true);
    }

    @Test
    void cocktailShoppingListRepo_save_and_findId() {
        CocktailShoppingList cocktailShoppingListSaved = cocktailShoppingListRepo.save(cocktailShoppingList);
        Optional<CocktailShoppingList> cocktailShoppingListOptional = cocktailShoppingListRepo.findById(cocktailShoppingListSaved.getId());
        assertEquals(cocktailShoppingListOptional.get().getId(),cocktailShoppingListSaved.getId());
    }

    @Test
    void cocktailShoppingListRepo_Save_and_checkMap() {
        CocktailShoppingList cocktailShoppingListSaved = cocktailShoppingListRepo.save(cocktailShoppingList);
        Optional<CocktailShoppingList> cocktailShoppingListOptional = cocktailShoppingListRepo.findById(cocktailShoppingListSaved.getId());

        assertEquals(cocktailShoppingListOptional.get().getShoppingListId(),shoppingList.getId());
        assertEquals(cocktailShoppingListOptional.get().getCocktailId(),cocktail.getId());
    }

    @Test
    void cocktailShoppingListRepo_Delete() {
        CocktailShoppingList cocktailShoppingListSaved =  cocktailShoppingListRepo.save(cocktailShoppingList);
        Optional<CocktailShoppingList> cocktailShoppingListOptional = cocktailShoppingListRepo.findById(cocktailShoppingListSaved.getId());
        assertEquals(cocktailShoppingListOptional.get().getId(), cocktailShoppingListSaved.getId());

        cocktailShoppingListRepo.delete(cocktailShoppingListOptional.get());
        Optional<CocktailShoppingList> cocktailShoppingListOptionalDeleted = cocktailShoppingListRepo.findById(cocktailShoppingListSaved.getId());
        assertEquals(cocktailShoppingListOptionalDeleted.isEmpty(),true);

    }

}
