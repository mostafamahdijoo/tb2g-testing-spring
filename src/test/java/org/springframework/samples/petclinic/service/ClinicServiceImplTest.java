package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    ClinicServiceImpl clinicService;

    @Test
    void findPetTypes() {
        //given
        List<PetType> petTypes = new LinkedList<>();
        PetType petType1 = new PetType();
        petType1.setName("petType1");
        PetType petType2 = new PetType();
        petType2.setName("petType2");
        petTypes.add(petType1);
        petTypes.add(petType2);

        given(petRepository.findPetTypes()).willReturn(petTypes);

        //when
        Collection<PetType> returnedPetTypes = clinicService.findPetTypes();

        //then
        then(petRepository).should().findPetTypes();
        assertNotNull(returnedPetTypes);
        assertThat(returnedPetTypes.size()).isEqualTo(2);
    }
}