package org.example.tapahtuma.repositories;

import org.example.tapahtuma.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); ;
}
// Compare this snippet from src/main/java/harjoituskalenteri/repositories/EventRepository.java: