package org.avillar.gymtracker.exercisesapi.common.adapter.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseSearchCriteria;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseUsesEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupExerciseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class CriteriaExerciseDaoImpl implements CriteriaExerciseDao {

  @PersistenceContext private EntityManager entityManager;

  @Override
  public List<ExerciseEntity> getAllFullExercises(final ExerciseSearchCriteria criteria) {
    final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<ExerciseEntity> criteriaQuery =
        criteriaBuilder.createQuery(ExerciseEntity.class);
    final Root<ExerciseEntity> root = criteriaQuery.from(ExerciseEntity.class);

    applyJoins(root, criteria.userId());

    final List<Predicate> predicates = createPredicates(criteria, root);

    criteriaQuery.where(predicates.toArray(new Predicate[0]));

    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  private void applyJoins(Root<ExerciseEntity> root, UUID userId) {
    final Join<ExerciseEntity, MuscleGroupExerciseEntity> mgeJoin =
        root.join("muscleGroupExercises", JoinType.LEFT);
    final Join<MuscleGroupExerciseEntity, MuscleGroupEntity> mgJoin =
        mgeJoin.join("muscleGroup", JoinType.LEFT);
    mgJoin.join("muscleSupGroups", JoinType.LEFT);
    root.join("muscleSubGroups", JoinType.LEFT);
    root.join("loadType", JoinType.LEFT);
    //Join<ExerciseEntity, ExerciseUsesEntity> usesJoin = root.join("exerciseUses", JoinType.LEFT);
   // usesJoin.on(entityManager.getCriteriaBuilder().equal(usesJoin.get("userId"), userId));
    Fetch<ExerciseEntity, ExerciseUsesEntity> fetch = root.fetch("exerciseUses", JoinType.LEFT);
    Join<ExerciseEntity, ExerciseUsesEntity> usesJoin = (Join<ExerciseEntity, ExerciseUsesEntity>) fetch;
    usesJoin.on(entityManager.getCriteriaBuilder().equal(usesJoin.get("userId"), userId));
  }

  private List<Predicate> createPredicates(
      ExerciseSearchCriteria criteria, Root<ExerciseEntity> root) {
    final List<Predicate> predicates = new ArrayList<>();

    predicates.add(isPrivateFromCurrentUserOrPublic(criteria.userId(), root));

    if (StringUtils.isNotEmpty(criteria.name())) {
      predicates.add(isNameContainedInExerciseName(criteria.name(), root));
    }

    if (!CollectionUtils.isEmpty(criteria.loadTypes())) {
      predicates.add(isLoadTypeMatch(criteria.loadTypes(), root));
    }

    return predicates;
  }

  private Predicate isPrivateFromCurrentUserOrPublic(UUID userId, Root<ExerciseEntity> root) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    return criteriaBuilder.or(
        criteriaBuilder.and(
            criteriaBuilder.equal(root.get("owner"), userId),
            criteriaBuilder.equal(root.get("accessType"), AccessTypeEnum.PRIVATE)),
        criteriaBuilder.equal(root.get("accessType"), AccessTypeEnum.PUBLIC));
  }

  private Predicate isNameContainedInExerciseName(String name, Root<ExerciseEntity> root) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    return criteriaBuilder.like(
        criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  private Predicate isLoadTypeMatch(List<UUID> loadTypeIds, Root<ExerciseEntity> root) {
    return root.get("loadType").get("id").in(loadTypeIds);
  }
}
