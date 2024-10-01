package edu.wgu.sample.convertor;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.wgu.sample.entity.ReservationEntity;
import edu.wgu.sample.model.response.ReservationResponse;

@Component
public class ReservationEntityToReservationResponseConverter implements Converter<ReservationEntity, ReservationResponse> {

    @Override
    public ReservationResponse convert(ReservationEntity source) {

        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setCheckin(source.getCheckin());
        reservationResponse.setCheckout(source.getCheckout());

        if (null != source.getRoomEntity())
            reservationResponse.setId(source.getRoomEntity().getId());

        return reservationResponse;
    }
}