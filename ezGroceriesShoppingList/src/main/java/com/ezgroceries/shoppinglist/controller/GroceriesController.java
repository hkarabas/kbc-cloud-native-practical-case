package com.ezgroceries.shoppinglist.controller;


import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.service.GroceriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@Api(value = "Groceries Rest Controller", description = "REST API for Groceries")
public class GroceriesController {

    private final Logger logger = LoggerFactory.getLogger(GroceriesController.class);

    private GroceriesService groceriesService;

    @Autowired
    public GroceriesController(GroceriesService groceriesService) {
        this.groceriesService = groceriesService;
    }

    @ApiOperation(value = "Get Cocktails", response = Iterable.class, tags = "getCocktailList")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "Not Found!") })
    @GetMapping("/cocktails")
    public List<CocktailDto> getCocktailList(@RequestParam String search) {
        logger.info(String.format("call getCocktailList by request param %s",search));
       return this.groceriesService.getCocktailList();
    }

    @ApiOperation(value = "Create Shopping List", response = ResponseEntity.class, tags = "createShoppingList")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create|OK")})
    @PostMapping("/shopping-lists")
    public ResponseEntity<Void> createShoppingList( @RequestParam String name) {
      ShoppingListDto shoppingListDto = groceriesService.createShoppingListDto(name);
      return  entityWithLocation(shoppingListDto);
    }


    @ApiOperation(value = "Add Cocktail to Shopping List", response = ResponseEntity.class, tags = "addCocktailToShoppingList")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create|OK")})
    @PostMapping("/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToShoppingList(@PathVariable String shoppingListId,@RequestParam String cocktailId) {
       ShoppingListDto shoppingListDto = groceriesService.addCocktailtoShoppingList(shoppingListId,cocktailId);
       return entityWithLocation(shoppingListDto.getShoppingListId());
    }

    @ApiOperation(value = "Get Shopping List By ID", response = ShoppingListDto.class, tags = "getShoppingListDto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "Not Found!")})
    @GetMapping("/shopping-lists/{shoppingListId}")
    public ShoppingListDto getShoppingListDto(@PathVariable String shoppingListId) {
        return groceriesService.getShoppingListDto(shoppingListId);
    }

    @ApiOperation(value = "Get Shopping List", response = Iterable.class, tags = "getShoppingListDtoList")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "Not Found!")})
    @GetMapping("/shopping-lists")
    public List<ShoppingListDto> getShoppingListDtoList() {
        return groceriesService.shoppingListDtoList();
    }

    private ResponseEntity<Void> entityWithLocation(Object resourceId) {

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(resourceId)
                .toUri();
         return ResponseEntity.created(location).build();
    }


}
