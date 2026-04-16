package com.objetcol.collectobjet.repository;

import com.objetcol.collectobjet.model.Objet;
import com.objetcol.collectobjet.model.StatutObjet;
import com.objetcol.collectobjet.model.TypeObjet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetRepository extends JpaRepository<Objet, Long> {

    Page<Objet> findByStatut(StatutObjet statut, Pageable pageable);

    Page<Objet> findByType(TypeObjet type, Pageable pageable);

    Page<Objet> findByTypeAndStatut(TypeObjet type, StatutObjet statut, Pageable pageable);

    List<Objet> findByProprietaireId(Long proprietaireId);

    Page<Objet> findByCategorieId(Long categorieId, Pageable pageable);

    @Query("SELECT o FROM Objet o WHERE " +
            "(:keyword IS NULL OR LOWER(o.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:type IS NULL OR o.type = :type) " +
            "AND (:statut IS NULL OR o.statut = :statut) " +
            "AND (:categorieId IS NULL OR o.categorie.id = :categorieId)")
    Page<Objet> rechercher(
            @Param("keyword") String keyword,
            @Param("type") TypeObjet type,
            @Param("statut") StatutObjet statut,
            @Param("categorieId") Long categorieId,
            Pageable pageable
    );
}