package com.ezgroceries.shoppinglist.controller;


import com.ezgroceries.shoppinglist.model.dto.CocktailDBResponse;
import com.ezgroceries.shoppinglist.model.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.out.CocktailDBClient;
import com.ezgroceries.shoppinglist.service.GroceriesService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@OpenAPIDefinition(info = @Info(title = "Groceries Rest Controller", description = "REST API for Groceries"))
//@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
public class GroceriesController {

    private final Logger logger = LoggerFactory.getLogger(GroceriesController.class);

    private GroceriesService groceriesService;

    private CocktailDBClient cocktailDBClient;

    @Autowired
    public GroceriesController(GroceriesService groceriesService,CocktailDBClient cocktailDBClient) {

        this.groceriesService = groceriesService;
        this.cocktailDBClient = cocktailDBClient;
    }

    @Operation(summary = "Get Cocktails",tags = "getCocktailList")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Success|OK",
                          content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Iterable.class))}),
            @ApiResponse(responseCode = "404", description = "Cocktail list not found",
                    content = @Content) })
    @GetMapping("/cocktails")
    public List<CocktailDto> getCocktailList(@RequestParam String search) {
        logger.info(String.format("call getCocktailList by request param %s",search));
        return this.groceriesService.getCocktailListDbAndRemote(search);
    }
    @Operation(summary = "Get Cocktails from remote api",tags = "getCocktailListDB")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Success|OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Iterable.class))}),
            @ApiResponse(responseCode = "404", description = "Cocktail list not found",
                    content = @Content) })
    @GetMapping(value = "/cocktailsdb")
    public CocktailDBResponse getCocktailListDB(@RequestParam String search) {
        logger.info(String.format("call getCocktailList by request param %s",search));
        return this.cocktailDBClient.searchCocktails(search);
    }


    @Operation(summary = "Create Shopping List",  tags = "createShoppingList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create|OK")})
    @PostMapping("/shopping-lists")
    public ResponseEntity<Void> createShoppingList(@NotNull @RequestParam String name) {

        if (StringUtils.isEmpty(name)) {
            throw  new IllegalArgumentException("shoppingLis name is must be not null");
        }

        logger.info(String.format("call createShoppingList by request param %s",name));
        ShoppingListDto shoppingListDto = groceriesService.createShoppingListDto(name);
        return  entityWithLocation(shoppingListDto.getShoppingListId());

    }


    @Operation(summary = "Add Cocktail to Shopping List", tags = "addCocktailToShoppingList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create|OK")})
    @PostMapping("/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToShoppingList(@PathVariable String shoppingListId,@RequestParam String cocktailId) {

        logger.info(String.format("call add cocktail shopping by request param %s %s",shoppingListId,cocktailId));
        ShoppingListDto shoppingListDto = groceriesService.addCocktailToShoppingList(shoppingListId, Set.of(cocktailId));
        if (shoppingListDto != null){
            return entityWithLocation(shoppingListDto.getShoppingListId());
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @Operation(summary = "Get Shopping List By ID",  tags = "getShoppingListDto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success|OK" ,
                    content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ShoppingListDto.class))}),
            @ApiResponse(responseCode = "404", description = "Shopping Not Found!")})
    @GetMapping("/shopping-lists/{shoppingListId}")
    public ShoppingListDto getShoppingListDto(@PathVariable String shoppingListId) {
        logger.info(String.format("shoppingListDto shopping by request param %s ",shoppingListId));
       return  groceriesService.getShoppingListDto(shoppingListId);
    }

    @Operation(description = "Get Shopping List", tags = "getShoppingListDtoList")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Success|OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Iterable.class))}),
            @ApiResponse(responseCode = "404", description = "Shopping list not found",
                    content = @Content) })
    @GetMapping("/shopping-lists")
    public List<ShoppingListDto> getShoppingListDtoList() {
        logger.info("shoppingListDto shopping list full");
        return groceriesService.shoppingListDtoList();
    }

    private ResponseEntity<Void> entityWithLocation(String resourceId) {
      URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(resourceId)
                .toUri();
         return ResponseEntity.created(location).build();
    }


}
