package com.ezgroceries.shoppinglist.model.entity;


import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "shopping_list")
public class ShoppingList {

    @Id
    String id;

    String name;

    @OneToMany(mappedBy = "shopping_list")
    Set<Cocktail_Shopping_List>  cocktail_shopping_lists;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Cocktail_Shopping_List> getCocktail_shopping_lists() {
        return cocktail_shopping_lists;
    }
}
