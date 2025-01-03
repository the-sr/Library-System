package library.services;

import java.util.List;

import library.dto.UserDto;
import library.dto.req.ResetPassReq;
import library.dto.res.PagewiseRes;

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

    String forgotPassword(String email);

    String validateOTP(int otp, String email);

    String resetPassword(String email, String password);
}
