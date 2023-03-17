package com.ezgroceries.shoppinglist.model.dto;

import com.ezgroceries.shoppinglist.model.entity.ShoppingList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;

public class ShoppingListDto {

    @Schema(description = "Shopping List Id",name="id",required=true,defaultValue = "")
    String shoppingListId;

    @Schema(description = "Shopping List name",name="name",required=true,defaultValue="")
    String name;

    @Schema(description = "Cocktails List",name="Cocktails")
    Set<CocktailDto> cocktailList;

    @Schema(description = "Cocktail include ingredients",name="ingredients")
    Set<String>  ingredients;


    public ShoppingListDto(Builder builder) {
        this.shoppingListId = builder.shoppingListId;
        this.name = builder.name;
        this.ingredients = builder.ingredients;
        this.cocktailList = builder.cocktailList;
    }


    public String getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }



    public Set<String> getIngredients() {
        return ingredients;
    }

    public static class Builder {
        String shoppingListId;
        String name;
        Set<CocktailDto> cocktailList;
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

        public Builder ingredients( Set<String>  ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder cocktailList(Set<CocktailDto> cocktailList) {
            this.cocktailList = cocktailList;
            return this;
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

    @JsonIgnore
    public ShoppingList getShoppingListEntity() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(UUID.fromString(shoppingListId));
        shoppingList.setName(getName());
        return shoppingList;
    }

}
