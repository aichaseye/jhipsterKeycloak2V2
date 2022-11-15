package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DemandeMatriculeEtab;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemandeMatriculeEtab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeMatriculeEtabRepository extends JpaRepository<DemandeMatriculeEtab, Long> {}
