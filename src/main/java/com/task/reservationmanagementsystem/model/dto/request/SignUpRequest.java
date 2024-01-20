package com.task.reservationmanagementsystem.model.dto.request;

import java.util.Set;

public record SignUpRequest(String username, String name, String surname, String email, Set<String> role, String password) {
}
