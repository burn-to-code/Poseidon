package com.poseidon.domain.DTO;

import com.poseidon.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseUserDto implements ConvertibleDtoFromEntity<User, ResponseUserDto>{

    private Integer id;
    private String username;
    private String fullname;
    private String role;

    public ResponseUserDto() {

    }

    @Override
    public ResponseUserDto fromEntity(User user) {
        return new ResponseUserDto(user.getId(), user.getUsername(), user.getFullname(), user.getRole());
    }
}
