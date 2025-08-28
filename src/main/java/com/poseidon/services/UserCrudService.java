package com.poseidon.services;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;
import com.poseidon.services.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class UserCrudService extends AbstractCrudService<User> {

    public UserCrudService (UserRepository repository) {
        super(repository);
    }
}
