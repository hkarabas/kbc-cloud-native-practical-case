package com.ezgroceries.shoppinglist.model.entity;


import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "shopping_list")
public class ShoppingList {

    @Id
    UUID id;

    String name;

    @OneToMany(mappedBy = "shoppingList")
    Set<CocktailShoppingList>  cocktail_shopping_lists;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public ShoppingListDto shoppingListDto() {
        return ShoppingListDto.Builder.newInstance()
                .shoppingListId(id.toString())
                .name(name).cocktailList(cocktail_shopping_lists.stream()
                        .map(CocktailShoppingList::getCocktail).map(Cocktail::getCocktailDto).collect(Collectors.toSet()))
                .ingredients(cocktail_shopping_lists.stream().map(CocktailShoppingList::getCocktail).flatMap(str->str.getIngredients().stream()).collect(Collectors.toSet()))
                .build();
    }
}
