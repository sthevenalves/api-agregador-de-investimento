package com.sthev.agregador_investimentos.controllers;

import com.sthev.agregador_investimentos.domain.dto.AccountDto;
import com.sthev.agregador_investimentos.domain.dto.AccountResponseDto;
import com.sthev.agregador_investimentos.domain.dto.UpdateUserDTO;
import com.sthev.agregador_investimentos.domain.User;
import com.sthev.agregador_investimentos.domain.dto.UserDTO;
import com.sthev.agregador_investimentos.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser (@RequestBody UserDTO dto){
        var userId = this.userService.createUser(dto);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        var user = this.userService.getUserById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody UpdateUserDTO data){
        userService.updateUser(id, data);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount (@PathVariable("userId") String userId,
                                    @RequestBody AccountDto accountDto){
        userService.createAccount(userId, accountDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDto>> listAccount (@PathVariable("userId") String userId){
        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

}
