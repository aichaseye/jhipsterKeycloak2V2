package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NiveauEtude;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NiveauEtude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NiveauEtudeRepository extends JpaRepository<NiveauEtude, Long> {}
