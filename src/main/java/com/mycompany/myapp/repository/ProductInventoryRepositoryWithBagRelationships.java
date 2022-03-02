package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductInventory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProductInventoryRepositoryWithBagRelationships {
    Optional<ProductInventory> fetchBagRelationships(Optional<ProductInventory> productInventory);

    List<ProductInventory> fetchBagRelationships(List<ProductInventory> productInventories);

    Page<ProductInventory> fetchBagRelationships(Page<ProductInventory> productInventories);
}
