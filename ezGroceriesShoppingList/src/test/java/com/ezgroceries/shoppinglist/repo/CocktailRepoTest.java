package com.ezgroceries.shoppinglist.repo;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.entity.Cocktail;
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
public class CocktailRepoTest {


    @Autowired
    private  CocktailRepo cocktailRepo;

    private UUID coctailID;
    private CocktailDto cocktaildto;
    private Cocktail cocktail;


    @BeforeEach
    private  void init() {
        coctailID = UUID.randomUUID();
        cocktaildto = CocktailDto.Builder.newInstance()
                .cocktailId("11102")
                .name("Black Russian")
                .glass("Cocktail Glass")
                .ingredients(Set.of("Lemon Juice",
                        "Creme de Cassis",
                        "Vodka")).build();

        cocktail = cocktaildto.getCocktailEntity();
        cocktail.setId(coctailID);



    }

    @Test
    void should_be_get_zero_record() {
      Optional<Cocktail> cocktail = cocktailRepo.findById(coctailID);
      assertEquals(cocktail.isEmpty(),true);
    }

    @Test
    void cocktail_save_and_findId() {
         cocktailRepo.save(cocktail);
         Optional<Cocktail> cocktailFound = cocktailRepo.findById(coctailID);
         assertEquals(cocktailFound.get().getId(),cocktail.getId());
    }

    @Test
    void cocktail_update() {
        cocktailRepo.save(cocktail);
        Optional<Cocktail> cocktailFound = cocktailRepo.findById(coctailID);
        assertEquals(cocktailFound.get().getName(),"Black Russian");

        cocktailFound.get().setName("Russian");
        cocktailRepo.save(cocktailFound.get());
        cocktailFound = cocktailRepo.findById(coctailID);
        assertEquals(cocktailFound.get().getName(),"Russian");
    }

    @Test
    void cocktail_delete() {
        cocktailRepo.save(cocktail);
        Optional<Cocktail> cocktailFound = cocktailRepo.findById(coctailID);
        assertEquals(cocktailFound.get().getName(),"Black Russian");

        cocktailRepo.delete(cocktailFound.get());

        cocktailFound = cocktailRepo.findById(coctailID);

        assertEquals(cocktailFound.isEmpty(),true);
    }



    

}
