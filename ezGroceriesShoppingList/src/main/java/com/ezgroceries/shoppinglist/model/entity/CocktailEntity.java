package com.ezgroceries.shoppinglist.model.entity;

import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.utils.StringSetConverter;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cocktail")
public class CocktailEntity {

    @Id
    String id;

    @Column(name="id_drink")
    String idDrink;

    String name;

    String glass;

    String instructions;

    @Convert(converter = StringSetConverter.class)
    Set<String> ingredients;

    @OneToMany(mappedBy = "cocktailEntity")
    Set<CocktailShoppingListEntity> cocktail_shopping_lists;

    public void setId(String id) {
        this.id = id;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
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


    public Set<CocktailShoppingListEntity> getCocktail_shopping_lists() {
        return cocktail_shopping_lists;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public CocktailDto getCocktailDto() {
       return   CocktailDto.Builder.newInstance()
                 .id(getId())
                 .cocktailId(getIdDrink())
                 .name(getName())
                 .glass(getGlass())
                 .instructions(getInstructions())
                 .ingredients(getIngredients()).build();
    }
}
