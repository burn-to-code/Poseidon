package com.poseidon.domain.DTO;

import com.poseidon.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseUserDtoForList implements ConvertibleDtoFromEntity<User, ResponseUserDtoForList>{

    private Integer id;
    private String username;
    private String fullname;
    private String role;

    public ResponseUserDtoForList() {

    }

    @Override
    public ResponseUserDtoForList fromEntity(User user) {
        return new ResponseUserDtoForList(user.getId(), user.getUsername(), user.getFullname(), user.getRole());
    }
}
