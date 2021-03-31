package kz.kazgisa.mapper;

import kz.kazgisa.data.dto.UserDto;
import kz.kazgisa.data.entity.user.User;
import org.springframework.beans.BeanUtils;

public class UserMapper {
    public static UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
