package com.task.reservationmanagementsystem.service;

import com.task.reservationmanagementsystem.entity.Book;
import com.task.reservationmanagementsystem.model.RateCode;
import com.task.reservationmanagementsystem.model.RoomCode;
import com.task.reservationmanagementsystem.repository.BookRepository;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookMailSender bookMailSender;


    public BookService(BookRepository bookRepository, BookMailSender bookMailSender) {
        this.bookRepository = bookRepository;
        this.bookMailSender = bookMailSender;
    }

    public List<Book> getBookByParameters(LocalDate checkIn, LocalDate checkOut, RoomCode roomCode, RateCode rateCode){
        return bookRepository.findAllByCheckInOrCheckOutOrRoomCodeOrRateCode(checkIn, checkOut, roomCode, rateCode);
    }
    public Book saveBook(Book book) throws MessagingException {

        Book savedBook = bookRepository.save(book);
        logger.info("Book id: " + savedBook.getId() + " Saved");
        //bookMailSender.mailSender(savedBook);
        return savedBook;
    }

    public Book updateBook(Book book){
        if (!bookRepository.existsById(book.getId())){
            logger.info("Id: "+book.getId() +" Does not have");
        }

        return bookRepository.save(book);
    }

    public String deleteBook(Book book){

        bookRepository.delete(book);
        return bookRepository.existsById(book.getId()) ? "Book is deleted" : "Failed to delete !";

    }




}
