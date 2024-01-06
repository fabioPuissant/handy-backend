package com.syntiq.handy.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntiq.handy.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto implements Serializable {
    private UUID id;
    @JsonProperty("sub")
    private String sub;


    @JsonProperty("picture")
    private String picture;
    @JsonProperty("email")
    private String email;


    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("name")
    private String name;
    private UserRole role;
}
