package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonnelEtabTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonnelEtab.class);
        PersonnelEtab personnelEtab1 = new PersonnelEtab();
        personnelEtab1.setId(1L);
        PersonnelEtab personnelEtab2 = new PersonnelEtab();
        personnelEtab2.setId(personnelEtab1.getId());
        assertThat(personnelEtab1).isEqualTo(personnelEtab2);
        personnelEtab2.setId(2L);
        assertThat(personnelEtab1).isNotEqualTo(personnelEtab2);
        personnelEtab1.setId(null);
        assertThat(personnelEtab1).isNotEqualTo(personnelEtab2);
    }
}
