package com.ezgroceries.shoppinglist.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class CocktailDBResponse  {

    private List<DrinkResource> drinks;

    public List<DrinkResource> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkResource> drinks) {
        this.drinks = drinks;
    }



    public static class DrinkResource {
        private String idDrink;
        private String strDrink;
        private String strGlass;
        private String strInstructions;
        private String strDrinkThumb;
        private String strIngredient1;
        private String strIngredient2;
        private String strIngredient3;
        private String strIngredient4;
        private String strIngredient5;
        private String strIngredient6;
        private String strIngredient7;
        private String strIngredient8;
        private String strIngredient9;
        private String strIngredient10;
        private String strMeasure1;
        private String strMeasure2;
        private String strMeasure3;
        private String strMeasure4;
        private String strMeasure5;
        private String strMeasure6;
        private String strMeasure7;
        private String strMeasure8;
        private String strMeasure9;
        private String strMeasure10;


        public String getIdDrink() {
            return idDrink;
        }

        public void setIdDrink(String idDrink) {
            this.idDrink = idDrink;
        }

        public String getStrDrink() {
            return strDrink;
        }

        public void setStrDrink(String strDrink) {
            this.strDrink = strDrink;
        }

        public String getStrGlass() {
            return strGlass;
        }

        public void setStrGlass(String strGlass) {
            this.strGlass = strGlass;
        }

        public String getStrInstructions() {
            return strInstructions;
        }

        public void setStrInstructions(String strInstructions) {
            this.strInstructions = strInstructions;
        }

        public String getStrDrinkThumb() {
            return strDrinkThumb;
        }

        public void setStrDrinkThumb(String strDrinkThumb) {
            this.strDrinkThumb = strDrinkThumb;
        }

        public String getStrIngredient1() {
            return strIngredient1;
        }

        public void setStrIngredient1(String strIngredient1) {
            this.strIngredient1 = strIngredient1;
        }

        public String getStrIngredient2() {
            return strIngredient2;
        }

        public void setStrIngredient2(String strIngredient2) {
            this.strIngredient2 = strIngredient2;
        }

        public String getStrIngredient3() {
            return strIngredient3;
        }

        public void setStrIngredient3(String strIngredient3) {
            this.strIngredient3 = strIngredient3;
        }

        public String getStrIngredient4() {
            return strIngredient4;
        }

        public void setStrIngredient4(String strIngredient4) {
            this.strIngredient4 = strIngredient4;
        }

        public String getStrIngredient5() {
            return strIngredient5;
        }

        public void setStrIngredient5(String strIngredient5) {
            this.strIngredient5 = strIngredient5;
        }

        public String getStrIngredient6() {
            return strIngredient6;
        }

        public void setStrIngredient6(String strIngredient6) {
            this.strIngredient6 = strIngredient6;
        }

        public String getStrIngredient7() {
            return strIngredient7;
        }

        public void setStrIngredient7(String strIngredient7) {
            this.strIngredient7 = strIngredient7;
        }

        public String getStrIngredient8() {
            return strIngredient8;
        }

        public void setStrIngredient8(String strIngredient8) {
            this.strIngredient8 = strIngredient8;
        }

        public String getStrIngredient9() {
            return strIngredient9;
        }

        public void setStrIngredient9(String strIngredient9) {
            this.strIngredient9 = strIngredient9;
        }

        public String getStrIngredient10() {
            return strIngredient10;
        }

        public void setStrIngredient10(String strIngredient10) {
            this.strIngredient10 = strIngredient10;
        }

        public String getStrMeasure1() {
            return strMeasure1;
        }

        public void setStrMeasure1(String strMeasure1) {
            this.strMeasure1 = strMeasure1;
        }

        public String getStrMeasure2() {
            return strMeasure2;
        }

        public void setStrMeasure2(String strMeasure2) {
            this.strMeasure2 = strMeasure2;
        }

        public String getStrMeasure3() {
            return strMeasure3;
        }

        public void setStrMeasure3(String strMeasure3) {
            this.strMeasure3 = strMeasure3;
        }

        public String getStrMeasure4() {
            return strMeasure4;
        }

        public void setStrMeasure4(String strMeasure4) {
            this.strMeasure4 = strMeasure4;
        }

        public String getStrMeasure5() {
            return strMeasure5;
        }

        public void setStrMeasure5(String strMeasure5) {
            this.strMeasure5 = strMeasure5;
        }

        public String getStrMeasure6() {
            return strMeasure6;
        }

        public void setStrMeasure6(String strMeasure6) {
            this.strMeasure6 = strMeasure6;
        }

        public String getStrMeasure7() {
            return strMeasure7;
        }

        public void setStrMeasure7(String strMeasure7) {
            this.strMeasure7 = strMeasure7;
        }

        public String getStrMeasure8() {
            return strMeasure8;
        }

        public void setStrMeasure8(String strMeasure8) {
            this.strMeasure8 = strMeasure8;
        }

        public String getStrMeasure9() {
            return strMeasure9;
        }

        public void setStrMeasure9(String strMeasure9) {
            this.strMeasure9 = strMeasure9;
        }

        public String getStrMeasure10() {
            return strMeasure10;
        }

        public void setStrMeasure10(String strMeasure10) {
            this.strMeasure10 = strMeasure10;
        }

        @JsonIgnore
        public Set<String> getIngredients() {
            return Arrays.asList(strIngredient1, strIngredient2, strIngredient3,
                    strIngredient4, strIngredient5, strIngredient6,
                    strIngredient7, strIngredient8, strIngredient9,
                    strIngredient10).stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet());


        }

        @JsonIgnore
        public Set<String> getMeasures() {
            return Arrays.asList(strMeasure1,strMeasure2,strMeasure3,
                    strMeasure4,strIngredient5,strIngredient6,
                    strIngredient7,strIngredient8,strIngredient9,
                    strIngredient10).stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        }


        @JsonIgnore
        public CocktailDto cocktailDto() {
            return  CocktailDto.Builder.newInstance()
                    .cocktailId(getIdDrink())
                    .glass(getStrGlass())
                    .ingredients(getIngredients())
                    .name(getStrDrink())
                    .image(getStrDrinkThumb())
                    .instructions(getStrInstructions()).build();

        }

    }


}