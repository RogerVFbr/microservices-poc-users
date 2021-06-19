package com.soundlab.users;

import com.soundlab.users.repositories.UserRepository;
import com.soundlab.users.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private ModelMapper mapper;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test() {
        assertEquals(1, 1);
    }

}
