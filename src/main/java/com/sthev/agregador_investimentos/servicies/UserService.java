package com.sthev.agregador_investimentos.servicies;

import com.sthev.agregador_investimentos.domain.Account;
import com.sthev.agregador_investimentos.domain.BillingAddress;
import com.sthev.agregador_investimentos.domain.dto.AccountDto;
import com.sthev.agregador_investimentos.domain.dto.AccountResponseDto;
import com.sthev.agregador_investimentos.domain.dto.UpdateUserDTO;
import com.sthev.agregador_investimentos.domain.User;
import com.sthev.agregador_investimentos.domain.dto.UserDTO;
import com.sthev.agregador_investimentos.repositories.AccountRepository;
import com.sthev.agregador_investimentos.repositories.BillingAddressRepository;
import com.sthev.agregador_investimentos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BillingAddressRepository billingAddressRepository;

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

    public void createAccount(String userId, AccountDto accountDto){
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        //Entity -> DTO
        var account = new Account(
                UUID.randomUUID(),
                accountDto.description(),
                new ArrayList<>(),
                user,
                null
        );
        var accountCreated = accountRepository.save(account);

        //Entity -> DTO
        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                accountDto.street(),
                accountDto.number()
        );
        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDto> listAccounts (String userId){
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccount()
                .stream()
                .map(ac -> new AccountResponseDto(ac.getAccountId().toString(),ac.getDescription()))
                .toList();
    }
}
