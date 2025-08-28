package com.poseidon.domain.DTO;

import com.poseidon.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseUserDtoForUpdate implements ConvertibleDtoFromEntity<User, ResponseUserDtoForUpdate>{

    private Integer id;
    private String username;
    private String fullname;
    private String role;
    private String password;

    public ResponseUserDtoForUpdate() {

    }

    @Override
    public ResponseUserDtoForUpdate fromEntity(User user) {
        return new ResponseUserDtoForUpdate(user.getId(), user.getUsername(), user.getFullname(), user.getRole(), user.getPassword());
    }
}
