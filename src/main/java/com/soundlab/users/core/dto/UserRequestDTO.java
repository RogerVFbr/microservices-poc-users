package com.soundlab.users.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserRequestDTO {
    @JsonProperty("name")
    @Schema(description = "Nome de usuário.", required = true)
    private String name;
}
