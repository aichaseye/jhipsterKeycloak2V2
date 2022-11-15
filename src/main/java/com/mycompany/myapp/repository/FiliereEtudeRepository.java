package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FiliereEtude;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FiliereEtude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FiliereEtudeRepository extends JpaRepository<FiliereEtude, Long> {}
