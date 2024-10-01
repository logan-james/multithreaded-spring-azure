package edu.wgu.sample.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.wgu.sample.entity.RoomEntity;
import edu.wgu.sample.model.Links;
import edu.wgu.sample.model.Self;
import edu.wgu.sample.model.response.ReservableRoomResponse;
import edu.wgu.sample.rest.ResourceConstants;

@Component
public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse>{

	@Override
	public ReservableRoomResponse convert(RoomEntity source) {
		// TODO Auto-generated method stub
		
		ReservableRoomResponse reservationResponse = new ReservableRoomResponse();
		if(null != source.getId())
			reservationResponse.setId(source.getId());
		reservationResponse.setRoomNumber(source.getRoomNumber());
		reservationResponse.setPrice( Integer.valueOf(source.getPrice()) );
		
		Links links = new Links();
		Self self = new Self();
		self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
		links.setSelf(self);
		
		reservationResponse.setLinks(links);
		
		return reservationResponse;
	}

	
	
}
