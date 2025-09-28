package ru.exist.ecom.core.application.customer;

import java.util.UUID;

public record CustomerDTO(UUID id, String name, String email, String phoneNumber) {}
