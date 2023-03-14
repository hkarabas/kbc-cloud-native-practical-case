package com.ezgroceries.shoppinglist.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cocktail_shopping_list")
public class CocktailShoppingListEntity {

    @Id
    String id;

    @Column(name="cocktail_id")
    String cocktailId;

    @Column(name="shopping_list_id")
    String shoppingListId;

    @ManyToOne
    @JoinColumn(name = "cocktail_id")
    CocktailEntity cocktailEntity;

    @ManyToOne
    @JoinColumn(name="shopping_list_id")
    ShoppingListEntity shoppingListEntity;

    public void setId(String id) {
        this.id = id;
    }

    public void setCocktailId(String cocktailId) {
        this.cocktailId = cocktailId;
    }

    public void setShoppingListId(String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }


    public String getId() {
        return id;
    }

    public String getCocktailId() {
        return cocktailId;
    }

    public String getShoppingListId() {
        return shoppingListId;
    }

    public CocktailEntity getCocktail() {
        return cocktailEntity;
    }

    public ShoppingListEntity getShoppingList() {
        return shoppingListEntity;
    }
}
