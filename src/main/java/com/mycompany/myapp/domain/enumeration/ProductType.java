package com.mycompany.myapp.domain.enumeration;

/**
 * The ProductType enumeration.
 */
public enum ProductType {
    RAWMATERIAL("RawMaterial"),
    PRODUCT("Product"),
    BOTH("Both"),
    NONE("None");

    private final String value;

    ProductType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
