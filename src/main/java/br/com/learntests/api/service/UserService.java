package br.com.learntests.api.service;

import br.com.learntests.api.domain.User;

public interface UserService {

    User findById(Integer id);

}
