package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DemandeMatriculeApp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemandeMatriculeApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeMatriculeAppRepository extends JpaRepository<DemandeMatriculeApp, Long> {}
