package edu.wgu.sample.convertor;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.wgu.sample.entity.ReservationEntity;
import edu.wgu.sample.entity.RoomEntity;
import edu.wgu.sample.model.request.ReservationRequest;
import edu.wgu.sample.repository.ReservationRepository;

@Component
public class ReservationRequestToReservationEntityConverter implements Converter<ReservationRequest, ReservationEntity> {
private final ApplicationContext context;

    public ReservationRequestToReservationEntityConverter(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public ReservationEntity convert(ReservationRequest source) {

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCheckin(source.getCheckin());
        reservationEntity.setCheckout(source.getCheckout());
        RoomService repository=context.getBean(RoomServiceImpl.class);
        RoomEntity associatedRoom=repository.findById(source.getRoomId());

        reservationEntity.setRoomEntity(associatedRoom);
        if (null != source.getId())
            reservationEntity.setId(source.getId());

        return reservationEntity;
    }
}