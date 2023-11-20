package com.example.graphqljv.specification;

import com.example.graphqljv.model.Game;
import com.example.graphqljv.model.Studio;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class SpecificationBuilder {

    public static Specification<Game> filterByGenreAndByPlatformAndByStudio(String genre, String platform, String studio) {
        return (root, query, criteriaBuilder) -> {
            
            Predicate predicate = criteriaBuilder.isTrue(criteriaBuilder.literal(true));

            if (genre != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.isMember(genre, root.get("genres")));
            }

            if (platform != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.isMember(platform, root.get("platforms")));
            }

            if (studio != null) {
                Join<Game, Studio> studioJoin = root.join("studios");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(studioJoin.get("name"), studio));
            }

            return predicate;
        };
    }
}
