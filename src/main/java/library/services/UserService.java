package library.services;

import java.util.List;

import library.dto.PasswordDto;
import library.dto.UserDto;
import library.dto.PageWiseResDto;

public interface UserService {
    UserDto save(UserDto req);

    UserDto findById(long id);

    List<UserDto> getAllUsers(Boolean status);

    PageWiseResDto<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, Boolean status);

    String deleteById(long id);

    UserDto updateById(UserDto req);

    String forgotPassword(PasswordDto req);

    PasswordDto validateOTP(PasswordDto req);

    String changePassword(PasswordDto req);

}
