package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SerieEtudeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SerieEtude.class);
        SerieEtude serieEtude1 = new SerieEtude();
        serieEtude1.setId(1L);
        SerieEtude serieEtude2 = new SerieEtude();
        serieEtude2.setId(serieEtude1.getId());
        assertThat(serieEtude1).isEqualTo(serieEtude2);
        serieEtude2.setId(2L);
        assertThat(serieEtude1).isNotEqualTo(serieEtude2);
        serieEtude1.setId(null);
        assertThat(serieEtude1).isNotEqualTo(serieEtude2);
    }
}
