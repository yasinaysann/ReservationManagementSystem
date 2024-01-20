package com.task.reservationmanagementsystem;

import com.task.reservationmanagementsystem.entity.Role;
import com.task.reservationmanagementsystem.model.Roles;
import com.task.reservationmanagementsystem.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class ReservationManagementSystemApplication implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public ReservationManagementSystemApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ReservationManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role role = new Role();
        role.setName(Roles.ROLE_ADMIN);

        Role role1 = new Role();
        role1.setName(Roles.ROLE_USER);



        roleRepository.save(role);
        roleRepository.save(role1);
    }
}
