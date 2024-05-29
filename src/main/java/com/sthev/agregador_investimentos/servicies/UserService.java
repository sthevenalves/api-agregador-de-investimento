package com.sthev.agregador_investimentos.servicies;

import com.sthev.agregador_investimentos.domain.UpdateUserDTO;
import com.sthev.agregador_investimentos.domain.User;
import com.sthev.agregador_investimentos.domain.UserDTO;
import com.sthev.agregador_investimentos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UUID createUser(UserDTO dto) {
        User user = new User(dto);
        user.setCreationTimeStamp(LocalDateTime.now());
        user.setUpdateTimeStamp(null);
        return (this.userRepository.save(user)).getUserId();
    }

    public Optional<User> getUserById(String id){
        return this.userRepository.findById(UUID.fromString(id));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser (String userId){
        var id = UUID.fromString(userId);
        if(userRepository.existsById(id)){
            this.userRepository.deleteById(id);
        }
    }
    public void updateUser(String userId, UpdateUserDTO data){
        var id = UUID.fromString(userId);
        var userEntity = (userRepository.findById(id));
        if(userEntity.isPresent()){
            var user = userEntity.get();

            if(data.username() != null)
                user.setUsername(data.username());

            if(data.password() != null)
                user.setPassword(data.password());

            userRepository.save(user);
        }
    }
}
