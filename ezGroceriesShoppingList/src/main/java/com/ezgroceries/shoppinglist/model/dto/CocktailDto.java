package com.ezgroceries.shoppinglist.model.dto;


import com.ezgroceries.shoppinglist.model.entity.Cocktail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;
import org.springframework.util.CollectionUtils;

public class CocktailDto {

    @Schema(description = "Id",name="id",required=true)
    String id;

    @Schema(description = "Cocktail Id",name="Cocktail id",required=true,defaultValue="23b3d85a-3928-41c0-a533-6538a71e17c4")
    String cocktailId;

    @Schema(description = "Cocktail Name",name="name",required=true,defaultValue="Margerita")
    String name;

    @Schema(description = "Cocktail Glass",name="glass",defaultValue="Cocktail glass")
    String glass;

    @Schema(description = "Image",name="image",defaultValue="https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg")
    String image;

    @Schema(description = "Cocktail making instructions",name="glass",defaultValue="Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..")
    String instructions;

    @Schema(description = "Cocktail include ingredients",name="ingredients")
    Set<String> ingredients;

    public CocktailDto(Builder builder) {
        this.id = builder.id;
        this.cocktailId = builder.cocktailId;
        this.name = builder.name;
        this.glass = builder.glass;
        this.image = builder.image;
        this.ingredients = builder.ingredients;
        this.instructions = builder.instructions;
    }

    public String getId() {
        return id;
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
                "id='" + id + '\'' +
                ", cocktailId='" + cocktailId + '\'' +
                ", name='" + name + '\'' +
                ", glass='" + glass + '\'' +
                ", image='" + image + '\'' +
                ", instructions='" + instructions + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    public static class Builder {
        private  String id;
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


        public Builder id(String id) {
           this.id = id;
           return this;
        }
       public Builder cocktailId(String cocktailId) {
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

    @JsonIgnore
    public Cocktail getCocktailEntity(){
        Cocktail cocktail = new Cocktail();
        cocktail.setId(id != null ? UUID.fromString(id) : UUID.randomUUID());
        cocktail.setIdDrink(cocktailId);
        cocktail.setName(name);
        cocktail.setGlass(glass);
        cocktail.setInstructions(instructions);
        cocktail.setIngredients(ingredients);
        return cocktail;
    }
    
}
