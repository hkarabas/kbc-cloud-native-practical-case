package com.ezgroceries.shoppinglist.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class ShoppingListDto {

    @Schema(description = "Shopping List Id",name="id",required=true,defaultValue = "")
    String shoppingListId;

    @Schema(description = "Shopping List name",name="name",required=true,defaultValue="")
    String name;

    @Schema(description = "Cocktail Id",name="id")
    String cocktailId;

    @Schema(description = "Cocktail include ingredients",name="ingredients")
    Set<String>  ingredients;


    public ShoppingListDto(Builder builder) {
        this.shoppingListId = builder.shoppingListId;
        this.cocktailId = builder.cocktailId;
        this.name = builder.name;
        this.ingredients = builder.ingredients;
    }

    public ShoppingListDto setCocktail(String cocktailId,Set<String>  ingredients) {
        this.cocktailId = cocktailId;
        this.ingredients = ingredients;
        return this;
    }


    public String getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public String getCocktailId() {
        return cocktailId;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public static class Builder {
        String shoppingListId;
        String name;
        String cocktailId;
        Set<String>  ingredients;

        private Builder(){}

        public static Builder  newInstance() {
            return new Builder();
        }
        public Builder shoppingListId(String shoppingListId){
            this.shoppingListId = shoppingListId;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return  this;
        }

        public ShoppingListDto build(){
            ShoppingListDto shoppingListDto = new ShoppingListDto(this);
            validate(shoppingListDto);
             return  shoppingListDto;
        }

        private void validate(ShoppingListDto shoppingListDto) {
            if (shoppingListDto.name == null || shoppingListDto.name.isEmpty()) {
                throw  new IllegalArgumentException("shoppingLis name is must be not null");
            }
        }

    }

}
