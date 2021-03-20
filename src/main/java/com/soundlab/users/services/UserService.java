package com.soundlab.users.services;

import com.soundlab.users.core.User;
import com.soundlab.users.core.dto.UserRequestDTO;
import com.soundlab.users.exceptions.UserNotFoundException;
import com.soundlab.users.repositories.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends AbstractGenericService<UserRepository, User>{
    private final ModelMapper mapper;

    protected UserService(UserRepository repository,
                          @Qualifier("genericMapper") ModelMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    public <T> List<T> findAll(Class<T> type) {
        return repository.findBy(type);
    }

    public <T> Optional<T> findById(Long id, Class<T> type) {
        return repository.findById(id, type);
    }

    public User save(UserRequestDTO request) {
        return repository.save(mapper.map(request, User.class));
    }

    public void update(Long id, UserRequestDTO data) {
        User original = findById(id, User.class).orElseThrow(() -> new UserNotFoundException(id));
        User updated = mapper.map(data, User.class);
        updated.setId(original.getId());
        repository.save(original);
    }
}
