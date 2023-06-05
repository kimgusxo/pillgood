package com.kit.pillgood.specification;

import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.persistence.dto.SearchingConditionDTO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PillSpecification implements Specification<Pill> {
    private SearchingConditionDTO searchingConditionDTO;

    public PillSpecification(SearchingConditionDTO searchingConditionDTO) {
        this.searchingConditionDTO = searchingConditionDTO;
    }

    @Override
    public Predicate toPredicate(Root<Pill> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (searchingConditionDTO.getPillShape() != null && !searchingConditionDTO.getPillShape().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("pillShape"), searchingConditionDTO.getPillShape()));
        }

        if (searchingConditionDTO.getPillColor() != null && !searchingConditionDTO.getPillColor().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("pillColor"), searchingConditionDTO.getPillColor()));
        }

        if (searchingConditionDTO.getPillFrontWord() != null && !searchingConditionDTO.getPillFrontWord().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("pillFrontWord"), searchingConditionDTO.getPillFrontWord()));
        }

        if (searchingConditionDTO.getPillBackWord() != null && !searchingConditionDTO.getPillBackWord().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("pillBackWord"), searchingConditionDTO.getPillBackWord()));
        }

        return predicate;
    }
}