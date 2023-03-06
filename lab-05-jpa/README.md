# Lab 05 - Shopping List JPA
With our new persistence capability we can finally provide a real implementation for our Shopping List API.

## JPA Entities
Create the ``Cocktail`` and ``ShoppingList`` JPA entities to enable interaction with the database schema we created in
the previous lab.

A partial example for the ``CocktailEntity`` class:

```java
@Entity
@Table(name = "cocktailDto")
public class CocktailEntity {
    //declare fields + methods here
}
```

**IMPORTANT**: We're going to store the ``CocktailEntity`` ingredients as a delimited ``String`` in the ingredients
table column. To easily accomplish this we can use a custom JPA converter, you can add this one to your project:

```java
@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> set) {
        if (!CollectionUtils.isEmpty(set)) {
            return "," + String.join(",", set) + ",";
        } else {
            return null;
        }
    }

    @Override
    public Set<String> convertToEntityAttribute(String joined) {
        if (joined != null) {
            String values = joined.substring(1, joined.length() - 1); //Removes leading and trailing commas
            return new HashSet<>(Arrays.asList(values.split(",")));
        }
        return new HashSet<>();
    }
}
```

To instruct JPA to use this converter on your ``CocktailEntity``, add this annotation on the ``ingredients`` field:

```java
@Convert(converter = StringSetConverter.class)
private Set<String> ingredients;
```

## Spring Data JPA Repositories

Add 2 Spring Data JPA repositories, one for each entity. We already added this dependency in the persistence lab. Refer
to the [documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.definition) for
a refresher on how to define JPA repositories.

### Service tier

There are multiple ways to design the interaction of API calls with database entities. To keep things simple we're going
to introduce a traditional Service layer.

A typical API call will go through Controller -> Service -> Repository (classic 3 layer model).

Service definition example:

```java
@Service
public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public ShoppingListResource create(ShoppingListResource shoppingListResource) {
        //implement me
    }
}
```

## Cocktail API

A very important change to our application involves the on-the-fly persisting of all Cocktails returned from the search,
we don't want to store all the attributes, we just want to assign a UUID for every unique Cocktail and persist this to
our database.

### Search cocktails
Refactor our current implementation:

* map all the returned cocktails from the ``CocktailDBClient`` using their ``idDrink`` attributes to
  our ``CocktailEntity`` instances
* if we don't have it in our database, persist it

There are a myriad of possibilities to implement this, focus on just getting a working example. Some inspiration of a
possible implementation on a new ``CocktailService`` service layer component:

```java
public List<CocktailResource> mergeCocktails(List<CocktailDBResponse.DrinkResource>drinks){
        //Get all the idDrink attributes
        List<String> ids=drinks.stream().map(CocktailDBResponse.DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap=cocktailRepository.findByIdDrinkIn(ids).stream().collect(Collectors.toMap(CocktailEntity::getIdDrink,o->o,(o,o2)->o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap=drinks.stream().map(drinkResource->{
        CocktailEntity cocktailEntity=existingEntityMap.get(drinkResource.getIdDrink());
        if(cocktailEntity==null){
        CocktailEntity newCocktailEntity=new CocktailEntity();
        newCocktailEntity.setId(UUID.randomUUID());
        newCocktailEntity.setIdDrink(drinkResource.getIdDrink());
        newCocktailEntity.setName(drinkResource.getStrDrink());
        cocktailEntity=cocktailRepository.save(newCocktailEntity);
        }
        return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getIdDrink,o->o,(o,o2)->o));

        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks,allEntityMap);
        }

private List<CocktailResource> mergeAndTransform(List<CocktailDBResponse.DrinkResource>drinks,Map<String, CocktailEntity> allEntityMap){
        return drinks.stream().map(drinkResource->new CocktailResource(allEntityMap.get(drinkResource.getIdDrink()).getId(),drinkResource.getStrDrink(),drinkResource.getStrGlass(),
        drinkResource.getStrInstructions(),drinkResource.getStrDrinkThumb(),getIngredients(drinkResource))).collect(Collectors.toList());
        }
```

## Shopping List API

### Create a new Shopping List

Refactor the ``ShoppingListController`` to have an autowired reference to the ``ShoppingListService`` and invoke the
appropriate service layer method. Replace the dummy response resources with the actual result of the creation.

### Add Cocktails to Shopping List

Replace the dummy resources and provide a real persistence implementation. This will include a service layer that will
take care of linking cocktails with a specific shopping list.

### Get a Shopping List

Replace the dummy resources and provide a real persistence implementation.

This is also the API where most of our business value is going to happen! Implement the logic to retrieve all the
Cocktails from the given Shopping List and extract the distinct ingredients to include them in the response body.

### Get all Shopping Lists

Replace the dummy resources and provide a real persistence implementation.

## Testing

Refactor your ``MockMvc`` tests to include a mocked ``ShoppingListService`` bean and add appropriate expectations to the
mocks.

Add a new ``ShoppingListServiceTest`` to test our ``ShoppingListService`` methods. This test runs as a simple unit test
without extensive Spring support: simple Mockito mocks will do.

## Commit and tag your work

Commit your work: use the lab name as comment and tag it with the same name. Don't forget to push to Github.