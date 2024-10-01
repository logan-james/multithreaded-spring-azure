package edu.wgu.sample.convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wgu.sample.entity.ReservationEntity;
import edu.wgu.sample.repository.ReservationRepository;
import edu.wgu.sample.repository.RoomRepository;

import java.util.List;

/**
 * Project: D387 sample code
 * Package: edu.wgu.d387_sample_code.convertor
 * <p>
 * User: carolyn.sher
 * Date: 9/16/2022
 * Time: 6:07 PM
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */

@Service
public class ReservationServiceImpl implements ReservationService{

    private ReservationRepository reservationRepository;

    @Autowired
    public  ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository=reservationRepository;
    }
    @Override
    public ReservationEntity findLast() {
        List<ReservationEntity> allReservations= (List<ReservationEntity>) reservationRepository.findAll();
        return allReservations.get(allReservations.size()-1);
    }

    @Override
    public List<ReservationEntity> findAll(){
        return (List<ReservationEntity>) reservationRepository.findAll();
    }
}
