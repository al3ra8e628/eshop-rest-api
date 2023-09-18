package com.example.contract.requests;

import com.example.modals.ItemCategory;
import com.example.modals.ItemUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.money.Money;

import java.util.List;

@Data
@EqualsAndHashCode
public class UpdateItemInStockStatusRequest {
    private Long id;
    private Boolean isInStock;

}
