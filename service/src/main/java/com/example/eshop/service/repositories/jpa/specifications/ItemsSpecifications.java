package com.example.eshop.service.repositories.jpa.specifications;

import com.example.eshop.service.repositories.jpa.entities.ItemEntity;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;


@And({
        @Spec(path = "name", params = "nameEquals", spec = Equal.class),
        @Spec(path = "name", params = "nameLike", spec = Like.class)
})
public interface ItemsSpecifications extends Specification<ItemEntity> {
}
