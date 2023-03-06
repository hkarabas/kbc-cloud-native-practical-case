package com.ezgroceries.shoppinglist.model.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.Set;

public class ShoppingListDto {

    @ApiModelProperty(notes = "Shopping List Id",name="id",required=true,value="")
    String shoppingListId;

    @ApiModelProperty(notes = "Shopping List name",name="name",required=true,value="")
    String name;

    @ApiModelProperty(notes = "Cocktail Id",name="id",required=false,value="")
    String cocktailId;

    @ApiModelProperty(notes = "Cocktail include ingredients",name="ingredients")
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
