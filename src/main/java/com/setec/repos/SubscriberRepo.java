package com.setec.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.setec.entities.Subscriber;

@Repository
public interface SubscriberRepo extends JpaRepository<Subscriber, Long> {
	Optional<Subscriber> findByEmail(String email);
}
