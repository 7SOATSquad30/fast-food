package br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {}
