package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeMatriculeAppTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeMatriculeApp.class);
        DemandeMatriculeApp demandeMatriculeApp1 = new DemandeMatriculeApp();
        demandeMatriculeApp1.setId(1L);
        DemandeMatriculeApp demandeMatriculeApp2 = new DemandeMatriculeApp();
        demandeMatriculeApp2.setId(demandeMatriculeApp1.getId());
        assertThat(demandeMatriculeApp1).isEqualTo(demandeMatriculeApp2);
        demandeMatriculeApp2.setId(2L);
        assertThat(demandeMatriculeApp1).isNotEqualTo(demandeMatriculeApp2);
        demandeMatriculeApp1.setId(null);
        assertThat(demandeMatriculeApp1).isNotEqualTo(demandeMatriculeApp2);
    }
}
