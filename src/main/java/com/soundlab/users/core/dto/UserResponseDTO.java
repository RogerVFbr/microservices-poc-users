package com.soundlab.users.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Value;

@JsonPropertyOrder({ "id", "name" })
@Value
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserResponseDTO {
    private Long id;
    private String name;

    @JsonCreator
    public static UserResponseDTO factory(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name) {
        return new UserResponseDTO(id, name);
    }
}
