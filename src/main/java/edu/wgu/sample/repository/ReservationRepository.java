package edu.wgu.sample.repository;


import org.springframework.data.repository.CrudRepository;

import edu.wgu.sample.entity.ReservationEntity;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
}