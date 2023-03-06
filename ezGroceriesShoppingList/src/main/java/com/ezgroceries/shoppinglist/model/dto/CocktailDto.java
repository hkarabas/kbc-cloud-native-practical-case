package com.ezgroceries.shoppinglist.model.dto;


import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class CocktailDto {

    @ApiModelProperty(notes = "Cocktail Id",name="id",required=true,value="23b3d85a-3928-41c0-a533-6538a71e17c4")
    String cocktailId;

    @ApiModelProperty(notes = "Cocktail Name",name="name",required=true,value="Margerita")
    String name;

    @ApiModelProperty(notes = "Cocktail Glass",name="glass",required=false,value="Cocktail glass")
    String glass;

    @ApiModelProperty(notes = "Image",name="image",required=false,value="https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg")
    String image;

    @ApiModelProperty(notes = "Cocktail making instructions",name="glass",required=false,value="Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..")
    String instructions;

    @ApiModelProperty(notes = "Cocktail include ingredients",name="ingredients")
    Set<String> ingredients;

    public CocktailDto(Builder builder) {
        this.cocktailId = builder.cocktailId;
        this.name = builder.name;
        this.glass = builder.glass;
        this.image = builder.image;
        this.ingredients = builder.ingredients;
        this.instructions = builder.instructions;
    }


    public String getCocktailId() {
        return cocktailId;
    }

    public String getName() {
        return name;
    }

    public String getGlass() {
        return glass;
    }

    public String getImage() {
        return image;
    }

    public String getInstructions() {
        return instructions;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "CocktailDto{" +
                "cocktailId='" + cocktailId + '\'' +
                ", name='" + name + '\'' +
                ", glass='" + glass + '\'' +
                ", image='" + image + '\'' +
                ", instructions='" + instructions + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    public static class Builder {
       private  String cocktailId;
       private  String name;
       private  String glass;
       private  String image;
       private  String instructions;
       private  Set<String> ingredients = Set.of();

       private Builder(){}

        public static Builder newInstance()
        {
            return new Builder();
        }
       
       public Builder coctailId(String cocktailId) {
           this.cocktailId = cocktailId;
           return this;
       }

       public Builder name(String name) {
           this.name = name;
           return this;
       }

       public Builder glass(String glass) {
           this.glass = glass;
           return this;
       }

       public  Builder image(String image) {
           this.image = image;
           return this;
       }

       public  Builder instructions(String instructions) {
           this.instructions = instructions;
           return this;
       }

       public Builder ingredients(Set<String> ingredients) {
           this.ingredients = ingredients;
           return  this;
       }


       public CocktailDto build() {
           CocktailDto cocktailDto = new CocktailDto(this);
           validate(cocktailDto);
           return cocktailDto;
       }

       private void validate(CocktailDto cocktailDto) {
           if (cocktailDto.cocktailId == null || cocktailDto.cocktailId.isEmpty()) {
               throw  new IllegalArgumentException("cocktailId is must be not null");
           }
           if (cocktailDto.name == null || cocktailDto.name.isEmpty()) {
               throw  new IllegalArgumentException("cocktail name is must be not null");
           }
           if (CollectionUtils.isEmpty(ingredients)) {
               throw  new IllegalArgumentException("ingredients inside must have at least one element ");

           }

       }



    }
    
}
