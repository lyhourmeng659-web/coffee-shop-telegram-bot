package com.setec.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.setec.entities.Booked;

@Repository
public interface BookedRepo extends JpaRepository<Booked, Integer>{

}
