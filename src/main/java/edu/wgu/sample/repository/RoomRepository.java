package edu.wgu.sample.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.wgu.sample.entity.RoomEntity;



public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

	//RoomEntity findById(Long id);
}
