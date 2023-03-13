create table COCKTAIL_SHOPPING_LIST
(
    COCKTAIL_ID      UUID,
    SHOPPING_LIST_ID uuid,
    constraint "COCKTAIL_SHOPPING_LIST_pk"
        unique (COCKTAIL_ID, SHOPPING_LIST_ID)
);

