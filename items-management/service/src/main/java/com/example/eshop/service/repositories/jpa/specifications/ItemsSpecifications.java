package com.example.eshop.service.repositories.jpa.specifications;

import com.example.eshop.service.repositories.jpa.entities.ItemEntity;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "creationDateTime", params = "creationDateTime.from", spec = GreaterThanOrEqual.class),
        @Spec(path = "creationDateTime", params = "creationDateTime.to", spec = LessThanOrEqual.class),

        @Spec(path = "isInStock", params = "isInStock", spec = Equal.class),

        @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class),

        @Spec(path = "priceAmount", params = "priceAmount", spec = Equal.class),
        @Spec(path = "priceAmount", params = "priceAmount.from", spec = GreaterThanOrEqual.class),
        @Spec(path = "priceAmount", params = "priceAmount.to", spec = LessThanOrEqual.class),

        @Spec(path = "priceCurrency", params = "priceCurrency", spec = Equal.class),
        @Spec(path = "category", params = "category", spec = Equal.class),
        @Spec(path = "description", params = "description", spec = Like.class),

        @Spec(path = "description", params = "description.isNull", spec = Null.class),
})
public interface ItemsSpecifications extends Specification<ItemEntity> {
}


