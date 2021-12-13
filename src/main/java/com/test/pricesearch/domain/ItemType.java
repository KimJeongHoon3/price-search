package com.test.pricesearch.domain;

public enum ItemType {
    FRUIT("과일"), VEGETABLE("채소");

    private final String typeName;

    ItemType(String typeName){
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static ItemType getItemType(String typeName){
        if(typeName.equals(FRUIT.getTypeName())){
            return FRUIT;
        }else if(typeName.equals(VEGETABLE.getTypeName())){
            return VEGETABLE;
        }else{
            throw new IllegalArgumentException("not defined item type : "+typeName);
        }
    }
}
