package com.example.demo.services;

import com.example.demo.dto.req.ResetPassReq;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.res.PagewiseRes;

import java.util.List;

public interface UserService {
    String save(UserDto userDto);

    UserDto findById(long id);

    List<UserDto> getAllUsers();

    PagewiseRes<UserDto> getAllUsersPagewise(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    String deleteById(long id);

    UserDto updateById(long id, UserDto userDto);

    UserDto findByEmail(String email);

    String changePassword(ResetPassReq passReq);

    List<UserDto> findAllActiveUsers();

    List<UserDto> findAllInactiveUsers();
}
