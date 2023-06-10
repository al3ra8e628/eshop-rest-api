package com.example.eshop.service.repositories.lsitingrepositories;

import com.example.contract.repositories.ItemsRepository;
import com.example.eshop.service.repositories.jpa.specifications.ItemsSpecifications;
import com.example.modals.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemsListingRepository extends ItemsRepository {
    Page<Item> listAll(ItemsSpecifications itemsSpecifications, Pageable pageable);

}
