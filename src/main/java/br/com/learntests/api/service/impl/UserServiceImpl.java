package br.com.learntests.api.service.impl;

import br.com.learntests.api.domain.User;
import br.com.learntests.api.domain.dto.UserDTO;
import br.com.learntests.api.repository.UserRepository;
import br.com.learntests.api.service.UserService;
import br.com.learntests.api.service.exception.DataIntegratyViolationException;
import br.com.learntests.api.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User findById(Integer id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Override
    public User create(UserDTO userDTO) {
        this.findByEmail(userDTO);
        return this.userRepository.save(this.modelMapper.map(userDTO, User.class));
    }

    private void findByEmail(UserDTO userDTO){
        Optional<User> user = this.userRepository.findByEmail(userDTO.getEmail());
        if(user.isPresent() && !(user.get().getId().equals(userDTO.getId()))){
            throw new DataIntegratyViolationException("Email já cadastrado no sistema");
        }
    }

    @Override
    public User update(UserDTO userDTO) {
        this.findByEmail(userDTO);
        return userRepository.save(this.modelMapper.map(userDTO, User.class));
    }

    @Override
    public void delete(Integer id) {
        this.findById(id);
        this.userRepository.deleteById(id);
    }
}
