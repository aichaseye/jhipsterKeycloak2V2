package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NiveauEtudeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NiveauEtude.class);
        NiveauEtude niveauEtude1 = new NiveauEtude();
        niveauEtude1.setId(1L);
        NiveauEtude niveauEtude2 = new NiveauEtude();
        niveauEtude2.setId(niveauEtude1.getId());
        assertThat(niveauEtude1).isEqualTo(niveauEtude2);
        niveauEtude2.setId(2L);
        assertThat(niveauEtude1).isNotEqualTo(niveauEtude2);
        niveauEtude1.setId(null);
        assertThat(niveauEtude1).isNotEqualTo(niveauEtude2);
    }
}
