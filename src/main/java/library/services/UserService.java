package library.services;

import java.util.List;

import library.dto.UserDto;
import library.dto.req.ResetPassReq;
import library.dto.res.PagewiseRes;

public interface UserService {
    UserDto save(UserDto req);

    UserDto findById(long id);

    List<UserDto> getAllUsers(Boolean status);

    PagewiseRes<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, Boolean status);

    String deleteById(long id);

    UserDto updateById(UserDto req);

    UserDto findByEmail(String email);

}
