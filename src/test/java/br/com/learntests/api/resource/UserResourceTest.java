package br.com.learntests.api.resource;

import br.com.learntests.api.domain.User;
import br.com.learntests.api.domain.dto.UserDTO;
import br.com.learntests.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Lucas";
    public static final String MAIL = "luquinha@mail.com";
    public static final String PASSWORD = "123";

    private User user;
    private UserDTO userDTO;

    @InjectMocks
    private UserResource userResource;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(this.userService.findById(anyInt())).thenReturn(this.user);
        when(this.modelMapper.map(any(),any())).thenReturn(this.userDTO);

        ResponseEntity<UserDTO> response = this.userResource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(MAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());

    }

    @Test
    void thenFindAllThenReturnSuccess() {
        when(this.userService.findAll()).thenReturn(List.of(this.user));
        when(this.modelMapper.map(any(), any())).thenReturn(this.userDTO);

        ResponseEntity<List<UserDTO>> response = this.userResource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(0).getClass());

        assertEquals(ID, response.getBody().get(0).getId());
        assertEquals(NAME, response.getBody().get(0).getName());
        assertEquals(MAIL, response.getBody().get(0).getEmail());
        assertEquals(PASSWORD, response.getBody().get(0).getPassword());
    }

    @Test
    void whenCreateUserThenReturnCreated() {
        when(this.userService.create(any())).thenReturn(this.user);

        ResponseEntity<UserDTO> response = this.userResource.createUser(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));

    }

    @Test
    void whenUpdateThenReturnSucces() {
    when(this.userService.update(userDTO)).thenReturn(user);
    when(this.modelMapper.map(any(),any())).thenReturn(userDTO);

    ResponseEntity<UserDTO> response = this.userResource.update(ID,userDTO);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(UserDTO.class, response.getBody().getClass());
    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertEquals(ID, response.getBody().getId());
    assertEquals(NAME, response.getBody().getName());
    assertEquals(MAIL, response.getBody().getEmail());
    assertEquals(PASSWORD, response.getBody().getPassword());

    }

    @Test
    void deleteWithSucces() {
        doNothing().when(this.userService).delete(anyInt());

        ResponseEntity<UserDTO> response = this.userResource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        verify(this.userService, times(1)).delete(anyInt());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    private void startUser() {
        this.user = new User(ID, NAME, MAIL, PASSWORD);
        this.userDTO = new UserDTO(ID, NAME, MAIL, PASSWORD);
    }

}