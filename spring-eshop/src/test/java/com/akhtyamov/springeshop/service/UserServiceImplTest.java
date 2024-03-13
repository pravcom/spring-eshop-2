package com.akhtyamov.springeshop.service;

import com.akhtyamov.springeshop.dao.UserRepository;
import com.akhtyamov.springeshop.domain.User;
import com.akhtyamov.springeshop.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class UserServiceImplTest {
    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @BeforeAll
    static void beforeAll(){
        System.out.println("Before all test");
    }

    @BeforeEach
    void setUp(){
        System.out.println("Before each test");
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userRepository = Mockito.mock(UserRepository.class);

        userService = new UserServiceImpl(userRepository,passwordEncoder);
    }

    @AfterEach
    void afterEach(){
        System.out.println("After each test");
    }
    @AfterAll
    static void afterAll(){
        System.out.println("After all test");
    }

    @Test
    void checkFindByName(){
        //have
        String name = "Petr";
        User expectedUser = User.builder()
                .id(1L)
                .name(name)
                .build();

        Mockito.when(userRepository.findFirstByName(Mockito.anyString())).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void checkFindByNameExact(){
        //have
        String name = "petr";
        User expectedUser = User.builder()
                .id(1L)
                .name(name)
                .build();

        Mockito.when(userRepository.findFirstByName(Mockito.eq(name))).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);
        User rndUser = userService.findByName(UUID.randomUUID().toString());

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser,actualUser);

        Assertions.assertNull(rndUser);

    }

    @Test
    void checkSaveIncorrectPassword(){
        //have
        UserDTO userDTO = UserDTO.builder()
                .password("password")
                .matchingPassword("another")
                .build();

        //execute
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.save(userDTO);
            }
        });
    }

    @Test
    void checkSave(){
        //have
        UserDTO userDTO = UserDTO.builder()
                .username("name")
                .email("mail")
                .password("password")
                .matchingPassword("password")
                .build();

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        //execute
        boolean result = userService.save(userDTO);

        //check
        Assertions.assertTrue(result);
        Mockito.verify(passwordEncoder).encode(Mockito.anyString());
        Mockito.verify(userRepository).save(Mockito.any());
    }

}
