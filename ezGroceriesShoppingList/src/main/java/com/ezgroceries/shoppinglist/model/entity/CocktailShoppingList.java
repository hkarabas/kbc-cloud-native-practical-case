package com.ezgroceries.shoppinglist.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cocktail_shopping_list")
public class CocktailShoppingList {

    @Column(name = "shopping_list_id",nullable = false)
    UUID shoppingListId;

    @Column(name = "cocktail_id",nullable = false)
    UUID cocktailId;

    @Id
    @GeneratedValue
    UUID id;

    
    @ManyToOne(optional = false)
    @JoinColumn(name = "cocktail_id",referencedColumnName = "id",updatable = false,insertable = false)
    Cocktail cocktail;

    @ManyToOne
    @JoinColumn(name="shopping_list_id",referencedColumnName = "id",updatable = false,insertable = false)
    ShoppingList shoppingList;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }


    public Cocktail getCocktail() {
        return cocktail;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }


    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public UUID getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }
}
