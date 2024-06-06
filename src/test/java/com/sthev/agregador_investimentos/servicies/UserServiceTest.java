package com.sthev.agregador_investimentos.servicies;

import com.sthev.agregador_investimentos.domain.dto.UpdateUserDTO;
import com.sthev.agregador_investimentos.domain.User;
import com.sthev.agregador_investimentos.domain.dto.UserDTO;
import com.sthev.agregador_investimentos.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser{

        @Test
        @DisplayName("Should create a User when successful")
        void createUserWhenSuccess(){
            var user = createUser();
            //arrange
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            var input = createUserDTO();

            //act
            var output = userService.createUser(input);

            //assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throws Exception when create a User when not successful")
        void createUserWhenNotSuccess(){
            //arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());

            var input = new UserDTO("teste",
                    "test@email.com",
                    "12345");
            //act //assertions
            assertThrows(RuntimeException.class, ()-> userService.createUser(input));
        }
    }

    @Nested
    class getUserById{
        @Test
        @DisplayName("should Get UserById When Successful")
        void shouldGetUserByIdWhenSuccess() {
            //arrange
            var user = createUser();
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            //act
            var output = userService.getUserById(user.getUserId().toString());
            //assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("should Get UserById with Successful when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {
            //arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());
            //act
            var output = userService.getUserById(userId.toString());
            //assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class listUsers{
        @Test
        void shouldReturnsAllUsersWithSuccess() {
            //arrange
            var user = createUser();
            doReturn(List.of(user)).when(userRepository)
                    .findAll();
            //act
            var output = userService.getAllUsers();
            //assert
            assertNotNull(output);
            assertEquals(List.of(user).size(), output.size());
        }
    }

    @Nested
    class deleteById{

        @Test
        @DisplayName("should delete an User with success")
        void shouldDeleteUserWithSuccess() {
            //arrange
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            //act
            userService.deleteUser(userId.toString());

            //assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("should Not Delete User When User Not Exists")
        void shouldNotDeleteUserWhenUserNotExists() {
            //arrange
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            //act
            userService.deleteUser(userId.toString());

            //assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());
        }


    }

    @Nested
    class updateUser{
        @Test
        @DisplayName("should update UserById when Username and password is filled")
        void shouldUpdateUserWhenSuccess() {
            //arrange
            var user = createUser();
            var updateUserDTO = updateUserDTO();
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());
            //act
            userService.updateUser(user.getUserId().toString(), updateUserDTO);

            //assert
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateUserDTO.username(), userCaptured.getUsername());
            assertEquals(updateUserDTO.password(), userCaptured.getPassword());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1))
                    .save(user);
        }

        @Test
        @DisplayName("should not update user when user not exists")
        void shouldNotUpdateUserWhenUserNotExists() {
            //arrange
            var updateUserDTO = updateUserDTO();
            var userId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //act
            userService.updateUser(userId.toString(), updateUserDTO);

            //assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0))
                    .save(any());
        }
    }

    private User createUser(){
        return new User(
                UUID.randomUUID(),
                null,
                LocalDateTime.now(),
                "12345",
                "test@email.com",
                "username" );
    }
    private UserDTO createUserDTO(){
        return new UserDTO("test",
                "test2@email.com",
                "54321");
    }
    private UpdateUserDTO updateUserDTO(){
        return new UpdateUserDTO("newName",
                "newPassword");
    }

}