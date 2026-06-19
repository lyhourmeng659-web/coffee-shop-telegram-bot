package com.setec.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.setec.entities.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long>{

}
