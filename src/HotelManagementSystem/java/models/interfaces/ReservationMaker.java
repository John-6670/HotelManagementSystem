package models.interfaces;

import models.reservation.Reservation;

import java.io.IOException;
import java.util.Map;

public interface ReservationMaker {
    Reservation makeReservation(Map data) throws IOException;
}
