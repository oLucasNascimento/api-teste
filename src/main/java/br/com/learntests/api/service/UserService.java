package br.com.learntests.api.service;

import br.com.learntests.api.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();

}
