package com.group9.ems.repository;

import com.group9.ems.entity.Registration;
import com.group9.ems.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByEventIdAndUserId(Long eventId, Long userId);
    long countByEventId(Long eventId);
    List<Registration> findByEventId(Long eventId);
    List<Registration> findByUser(User user);
    Optional<Registration> findByEventIdAndUserId(Long eventId, Long userId);
}