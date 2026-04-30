package com.senagust.helpdesk.service;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.UpdateUserRequest;
import com.senagust.helpdesk.dto.UserResponse;
import com.senagust.helpdesk.exception.ConflictException;
import com.senagust.helpdesk.exception.NotFoundException;
import com.senagust.helpdesk.mapper.UserMapper;
import com.senagust.helpdesk.model.Customer;
import com.senagust.helpdesk.model.User;
import com.senagust.helpdesk.model.UserType;
import com.senagust.helpdesk.repository.UserRepository;
import com.senagust.helpdesk.service.impl.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Nested
    class Create {
        @Test
        void shouldCreateAnUser_whenItIsValid() {
            CreateUserRequest request = new CreateUserRequest();
            request.setEmail("test@email.com");
            request.setUserType(UserType.CUSTOMER);
            when(userRepository.existsByEmail("test@email.com")).thenReturn(false);

            userService.create(request);

            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(captor.capture());
            User saved = captor.getValue();
            assertThat(saved.getEmail()).isEqualTo("test@email.com");
            assertThat(saved).isInstanceOf(Customer.class);
            assertThat(saved.isActive()).isEqualTo(true);
        }

        @Test
        void shouldThrowConflictException_whenEmailAlreadyExists() {
            CreateUserRequest request = new CreateUserRequest();
            request.setEmail("conflict@email.com");

            when(userRepository.existsByEmail("conflict@email.com")).thenReturn(true);

            Assertions.assertThatThrownBy(() -> userService.create(request))
                    .isInstanceOf(ConflictException.class)
                    .hasMessageContaining("already in use");

            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    class getById {
        @Test
        void shouldReturnUser_whenUserExists() {
            UUID id = UUID.randomUUID();
            User user = new Customer();
            user.setId(id);
            UserResponse expectedResponse = new UserResponse();
            when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.of(user));
            when(userMapper.toUserResponse(user)).thenReturn(expectedResponse);

            UserResponse result = userService.getById(id);

            assertThat(result).isEqualTo(expectedResponse);
        }

        @Test
        void shouldThrowNotFoundException_whenUserDoesNotExist() {
            UUID id = UUID.randomUUID();

            when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> userService.getById(id)).isInstanceOf(NotFoundException.class).hasMessageContaining("User not found with id: " + id);
        }
    }

    @Nested
    class getAll {
        @Test
        void shouldReturnAllAvailableUsers_whenThereIsUsers() {
            User user = new Customer();
            user.setFirstName("testable user");
            UserResponse userResponse = new UserResponse();
            userResponse.setFirstName(user.getFirstName());

            when(userRepository.findAllByIsActiveTrue()).thenReturn(List.of(user));
            when(userMapper.toUserResponse(user)).thenReturn(userResponse);

            List<UserResponse> result = userService.getAll();

            assertThat(result).contains(userResponse);
        }
    }

    @Nested
    class deleteById {
        @Test
        void shouldDeleteAnActiveUser_whenItIsActive() {
            UUID id = UUID.randomUUID();
            User user = new Customer();
            user.setEmail("test@test.com");
            when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.of(user));

            userService.deleteById(id);

            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(captor.capture());
            User savedUser = captor.getValue();
            assertThat(savedUser.isActive()).isEqualTo(false);
            assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        }

        @Test
        void shouldThrowNotFoundException_whenItIsNotActive() {
            UUID id = UUID.randomUUID();
            when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> userService.deleteById(id)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    class updateById {
        @Test
        void shouldUpdateAnUser_whenUpdateAnActiveUser() {
            UUID id = UUID.randomUUID();
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.setFirstName("new first name");
            updateUserRequest.setLastName("new last name");
            var helper = new Customer();
            when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.of(helper));
            when(userMapper.toUserResponse(helper)).thenReturn(new UserResponse());

            UserResponse user = userService.updateById(id, updateUserRequest);

            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(captor.capture());
            var updatedUser = captor.getValue();
            assertThat(updatedUser.getFirstName()).isEqualTo(updateUserRequest.getFirstName());
            assertThat(updatedUser.getLastName()).isEqualTo(updateUserRequest.getLastName());
        }

        @Test
        void shouldNotUpdateAnUser_whenUpdateAnUnactiveUser() {
            UUID id = UUID.randomUUID();
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.setFirstName("first name");
            updateUserRequest.setLastName("last name");

            when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> userService.updateById(id, updateUserRequest)).isInstanceOf(NotFoundException.class);


        }
    }
}
