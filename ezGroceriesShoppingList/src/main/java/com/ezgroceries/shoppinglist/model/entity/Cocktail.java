package com.ezgroceries.shoppinglist.model.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cocktail")
public class Cocktail {

    @Id
    String id;

    @Column(name="id_drink")
    String idDrink;

    String name;
    
    String ingredients;

    @OneToMany(mappedBy = "cocktail")
    Set<Cocktail_Shopping_List> cocktail_shopping_lists;

    public void setId(String id) {
        this.id = id;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public Set<Cocktail_Shopping_List> getCocktail_shopping_lists() {
        return cocktail_shopping_lists;
    }
}
