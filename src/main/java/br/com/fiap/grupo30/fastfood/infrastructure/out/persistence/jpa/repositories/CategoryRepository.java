package br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories;

import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {}
