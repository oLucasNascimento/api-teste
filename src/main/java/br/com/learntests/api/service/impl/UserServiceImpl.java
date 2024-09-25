package br.com.learntests.api.service.impl;

import br.com.learntests.api.domain.User;
import br.com.learntests.api.repository.UserRepository;
import br.com.learntests.api.service.UserService;
import br.com.learntests.api.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<User> findAll(){
        return this.userRepository.findAll();
    }
}
