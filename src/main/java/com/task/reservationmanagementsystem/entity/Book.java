package com.task.reservationmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.reservationmanagementsystem.model.RateCode;
import com.task.reservationmanagementsystem.model.RoomCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity(name = "Book")
@Table(indexes = {
        @Index(name = "idx_checkIn_date", columnList = "checkIn"),
        @Index(name = "idx_checkOut_date", columnList = "checkOut"),
        @Index(name = "idx_roomCode",columnList = "roomCode"),
        @Index(name = "idx_rateCode",columnList = "rateCode")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @Email
    private String email;

    private String phoneNumber;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate checkIn;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate checkOut;
    private RoomCode roomCode;
    private RateCode rateCode;
    @Positive
    private Integer totalPax;

    public Book() {
    }

    public Book(Long id, String firstName, String lastName, String email, String phoneNumber, LocalDate checkIn, LocalDate checkOut, RoomCode roomCode, RateCode rateCode, Integer totalPax) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomCode = roomCode;
        this.rateCode = rateCode;
        this.totalPax = totalPax;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public RoomCode getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(RoomCode roomCode) {
        this.roomCode = roomCode;
    }

    public RateCode getRateCode() {
        return rateCode;
    }

    public void setRateCode(RateCode rateCode) {
        this.rateCode = rateCode;
    }

    public Integer getTotalPax() {
        return totalPax;
    }

    public void setTotalPax(Integer totalPax) {
        this.totalPax = totalPax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
