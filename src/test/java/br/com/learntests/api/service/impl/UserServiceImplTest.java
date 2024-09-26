package br.com.learntests.api.service.impl;

import br.com.learntests.api.domain.User;
import br.com.learntests.api.domain.dto.UserDTO;
import br.com.learntests.api.repository.UserRepository;
import br.com.learntests.api.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Lucas";
    public static final String MAIL = "luquinha@mail.com";
    public static final String PASSWORD = "123";

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
        when(this.repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        User response = this.service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(this.repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
        try{
            this.service.findById(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        this.user = new User(ID, NAME, MAIL, PASSWORD);
        this.userDTO = new UserDTO(ID, NAME, MAIL, PASSWORD);
        this.optionalUser = Optional.of(new User(ID, NAME, MAIL, PASSWORD));
    }
}