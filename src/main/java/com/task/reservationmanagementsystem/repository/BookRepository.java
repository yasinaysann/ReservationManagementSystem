package com.task.reservationmanagementsystem.repository;

import com.task.reservationmanagementsystem.entity.Book;
import com.task.reservationmanagementsystem.model.RateCode;
import com.task.reservationmanagementsystem.model.RoomCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.checkIn= :checkIn or b.checkOut= :checkOut or b.roomCode= :roomCode or b.rateCode= :rateCode order by b.id desc")
    List<Book> findAllByCheckInOrCheckOutOrRoomCodeOrRateCode(@Param("checkIn") LocalDate checkIn,
                                                              @Param("checkOut") LocalDate checkOut,
                                                              @Param("roomCode") RoomCode roomCode,
                                                              @Param("rateCode") RateCode rateCode);
    @Query("select b from Book b where b.checkIn= :checkIn")
    List<Book> findAllByCheckIn(LocalDate checkIn);
    List<Book> findAllByCheckOut(LocalDate checkOut);
    List<Book> findAllByRoomCode(RoomCode roomCode);
    List<Book> findAllByRateCode(RateCode rateCode);
    List<Book> findAllByCheckOutLessThan(LocalDate localDate);



}
