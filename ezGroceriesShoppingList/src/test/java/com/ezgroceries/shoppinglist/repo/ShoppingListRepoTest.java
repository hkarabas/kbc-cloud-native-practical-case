package com.ezgroceries.shoppinglist.repo;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
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
public class ShoppingListRepoTest {

    @Autowired
    private  ShoppingListRepo shoppingListRepo;


    private UUID shoppingListId;
    private ShoppingListDto shoppingListDto;
    private ShoppingList shoppingList;

    @BeforeEach
    private void init() {
       shoppingListId = UUID.randomUUID();
       shoppingListDto = ShoppingListDto.Builder.newInstance()
                        .name("Stephanie's birthday")
                        .ingredients(Set.of("Lemon Juice",
                        "Creme de Cassis",
                        "Vodka")).build();
       shoppingList = shoppingListDto.getShoppingListEntity();
       shoppingList.setId(shoppingListId);
    }

    @Test
    void should_be_get_zero_record() {
        Optional<ShoppingList> shoppingList = shoppingListRepo.findById(shoppingListId);
        assertEquals(shoppingList.isEmpty(),true);
    }

    @Test
    void shoppingList_save_and_findId() {
        shoppingListRepo.save(shoppingList);
        Optional<ShoppingList> shoppingListOptional = shoppingListRepo.findById(shoppingListId);
        assertEquals(shoppingListOptional.get().getId(),shoppingList.getId());
    }

    @Test
    void shoppingList_update() {
        shoppingListRepo.save(shoppingList);
        Optional<ShoppingList> shoppingListOptional = shoppingListRepo.findById(shoppingListId);
        assertEquals(shoppingListOptional.get().getName(),"Stephanie's birthday");

        shoppingListOptional.get().setName("Denny's birthday");
        shoppingListRepo.save(shoppingListOptional.get());
        shoppingListOptional = shoppingListRepo.findById(shoppingListId);
        assertEquals(shoppingListOptional.get().getName(),"Denny's birthday");
    }

    @Test
    void shoppingList_delete() {
        shoppingListRepo.save(shoppingList);
        Optional<ShoppingList> shoppingListOptional = shoppingListRepo.findById(shoppingListId);
        assertEquals(shoppingListOptional.get().getName(),"Stephanie's birthday");

        shoppingListRepo.delete(shoppingListOptional.get());

        shoppingListOptional = shoppingListRepo.findById(shoppingListId);

        assertEquals(shoppingListOptional.isEmpty(),true);
    }





}
