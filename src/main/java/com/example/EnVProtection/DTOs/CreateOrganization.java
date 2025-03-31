package com.example.EnVProtection.DTOs;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganization {
    private String name;

    private String description;

    private Owner owner;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Owner {
        private Long id;
    }
}
