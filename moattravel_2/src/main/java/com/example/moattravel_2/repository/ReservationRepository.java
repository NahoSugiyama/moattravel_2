package com.example.moattravel_2.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel_2.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
