package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @Mock
    Map<String, Object> model;

    @InjectMocks
    VetController vetController;

    List<Vet> vetList = new LinkedList<>();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        //given
        Vet v1 = new Vet();
        v1.setFirstName("vet1");
        Vet v2 = new Vet();
        v2.setFirstName("vet2");
        vetList.add(v1);
        vetList.add(v2);

        given(clinicService.findVets()).willReturn(vetList);

        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    @Test
    void testControllerShowVetList() throws Exception {
        mockMvc.perform(get("/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
    }

    @Test
    void showVetList() {
        //when
        String viewName = vetController.showVetList(model);

        //then
        then(clinicService).should().findVets();
        then(model).should().put(anyString(), any());
        assertThat(viewName).isEqualToIgnoringCase("vets/vetList");
        //assertThat(((Vets) model.get("vets")).getVetList().size()).isEqualTo(2);
    }

    @Test
    void showResourcesVetList() {
        //when
        Vets vets = vetController.showResourcesVetList();

        //then
        then(clinicService).should().findVets();
        assertNotNull(vets);
        assertThat(vets.getVetList()).hasSize(2);
    }
}