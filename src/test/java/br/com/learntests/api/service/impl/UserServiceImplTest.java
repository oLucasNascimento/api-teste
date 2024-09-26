package br.com.learntests.api.service.impl;

import br.com.learntests.api.domain.User;
import br.com.learntests.api.domain.dto.UserDTO;
import br.com.learntests.api.repository.UserRepository;
import br.com.learntests.api.service.exception.DataIntegrityViolationException;
import br.com.learntests.api.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Lucas";
    public static final String MAIL = "luquinha@mail.com";
    public static final String PASSWORD = "123";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(this.repository.findById(Mockito.anyInt())).thenReturn(this.optionalUser);
        User response = this.service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(this.repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try {
            this.service.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {

        when(this.service.findAll()).thenReturn(List.of(this.user));

        List<User> users = this.service.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(User.class, users.get(0).getClass());

        assertEquals(ID, users.get(0).getId());
        assertEquals(NAME, users.get(0).getName());
        assertEquals(MAIL, users.get(0).getEmail());
        assertEquals(PASSWORD, users.get(0).getPassword());

    }

    @Test
    void whenCreateThenReturnSucess() {
        when(this.repository.save(any())).thenReturn(this.user);

        User response = this.service.create(this.userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataViolationException() {
        when(this.repository.findByEmail(anyString())).thenReturn(this.optionalUser);

        try {
            optionalUser.get().setId(2);
            this.service.create(userDTO);
        } catch (Exception ex){
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Email já cadastrado no sistema", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSucess() {
        when(this.repository.save(any())).thenReturn(this.user);

        User response = this.service.update(this.userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    void whenUpdateThenReturnAnDataViolationException() {
        when(this.repository.findByEmail(anyString())).thenReturn(this.optionalUser);

        try {
            optionalUser.get().setId(2);
            this.service.update(userDTO);
        } catch (Exception ex){
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Email já cadastrado no sistema", ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        when(this.repository.findById(anyInt())).thenReturn(this.optionalUser);
        doNothing().when(this.repository).deleteById(anyInt());

        this.service.delete(ID);
        verify(this.repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithObjectNotFoundException(){
        when(this.repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
           this.service.delete(ID);
        } catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }

    }

    private void startUser() {
        this.user = new User(ID, NAME, MAIL, PASSWORD);
        this.userDTO = new UserDTO(ID, NAME, MAIL, PASSWORD);
        this.optionalUser = Optional.of(new User(ID, NAME, MAIL, PASSWORD));
    }
}