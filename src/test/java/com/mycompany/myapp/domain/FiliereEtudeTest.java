package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FiliereEtudeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FiliereEtude.class);
        FiliereEtude filiereEtude1 = new FiliereEtude();
        filiereEtude1.setId(1L);
        FiliereEtude filiereEtude2 = new FiliereEtude();
        filiereEtude2.setId(filiereEtude1.getId());
        assertThat(filiereEtude1).isEqualTo(filiereEtude2);
        filiereEtude2.setId(2L);
        assertThat(filiereEtude1).isNotEqualTo(filiereEtude2);
        filiereEtude1.setId(null);
        assertThat(filiereEtude1).isNotEqualTo(filiereEtude2);
    }
}
