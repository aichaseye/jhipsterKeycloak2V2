package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeMatriculeEtabTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeMatriculeEtab.class);
        DemandeMatriculeEtab demandeMatriculeEtab1 = new DemandeMatriculeEtab();
        demandeMatriculeEtab1.setId(1L);
        DemandeMatriculeEtab demandeMatriculeEtab2 = new DemandeMatriculeEtab();
        demandeMatriculeEtab2.setId(demandeMatriculeEtab1.getId());
        assertThat(demandeMatriculeEtab1).isEqualTo(demandeMatriculeEtab2);
        demandeMatriculeEtab2.setId(2L);
        assertThat(demandeMatriculeEtab1).isNotEqualTo(demandeMatriculeEtab2);
        demandeMatriculeEtab1.setId(null);
        assertThat(demandeMatriculeEtab1).isNotEqualTo(demandeMatriculeEtab2);
    }
}
