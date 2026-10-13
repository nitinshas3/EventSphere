package com.nitin.EventSphere.repositories;

import com.nitin.EventSphere.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
// this used with userprovisionfilter to save the user when they first send the request using jwt etc
