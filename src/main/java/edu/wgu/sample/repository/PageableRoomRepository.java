package edu.wgu.sample.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import edu.wgu.sample.entity.RoomEntity;

import java.time.LocalDate;

/*
public interface PageableRoomRepository extends PagingAndSortingRepository<RoomEntity, Long> {
	
	Page<RoomEntity> findById(Long id, Pageable page);
	Page<RoomEntity> findAvailableRooms(LocalDate checkin, LocalDate checkout, Pageable page);

}
*/