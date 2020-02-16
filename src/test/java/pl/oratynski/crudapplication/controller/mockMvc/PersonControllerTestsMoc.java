package pl.oratynski.crudapplication.controller.mockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.oratynski.crudapplication.model.Person;
import pl.oratynski.crudapplication.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTestsMoc {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @Test
    public void givenExistingId_whenFindById_thenReturnPerson() throws Exception {
        when(personRepository.findById(123L)).thenReturn(Optional.of(getPerson(123L, "Jack")));

        mockMvc.perform(get("/person/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.age", is(21)))
                .andExpect(jsonPath("$.firstName", is("Jack")))
                .andExpect(jsonPath("$.lastName", is("Smith")));

        verify(personRepository, times(1)).findById(123L);
    }

    @Test
    public void givenNotExistingId_whenFindById_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/person/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenPerson_whenAdd_thenReturnNewPerson() throws Exception {
        Person newPerson = getPerson(10L, "Alex");
        when(personRepository.save(any(Person.class))).thenReturn(newPerson);

        mockMvc.perform(post("/person")
                .content(OBJECT_MAPPER.writeValueAsString(newPerson))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.age", is(21)))
                .andExpect(jsonPath("$.firstName", is("Alex")))
                .andExpect(jsonPath("$.lastName", is("Smith")));
    }

    @Test
    public void whenFindAll_thenReturnPersonList() throws Exception {
        when(personRepository.findAll()).thenReturn(getPersonList());

        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void whenFindAll_thenReturnEmptyList() throws Exception {
        when(personRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenPerson_whenUpdate_thenReturnUpdatedPerson() throws Exception {
        Person updated = getPerson(1L, "Jacob");
        when(personRepository.save(any(Person.class))).thenReturn(updated);

        Person toUpdate = getPerson(1L, "Thomas");
        mockMvc.perform(put("/person/1")
                .content(OBJECT_MAPPER.writeValueAsString(toUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.age", is(21)))
                .andExpect(jsonPath("$.firstName", is("Jacob")))
                .andExpect(jsonPath("$.lastName", is("Smith")));
    }

    @Test
    public void givenPerson_whenDelete_thenReturnId() throws Exception {
        doNothing().when(personRepository).deleteById(1L);

        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    private List<Person> getPersonList() {
        return List.of(
                getPerson(1L, "Oliver"),
                getPerson(2L, "Jack"),
                getPerson(3L, "Harry")
        );
    }

    private Person getPerson(Long id, String firstName) {
        return Person.builder()
                .id(id)
                .age(21)
                .firstName(firstName)
                .lastName("Smith")
                .build();
    }
}
