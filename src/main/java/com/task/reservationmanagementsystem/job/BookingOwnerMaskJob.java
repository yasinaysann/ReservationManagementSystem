package com.task.reservationmanagementsystem.job;

import com.task.reservationmanagementsystem.entity.Book;
import com.task.reservationmanagementsystem.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
@Component
public class BookingOwnerMaskJob {
    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(BookingOwnerMaskJob.class);


    public BookingOwnerMaskJob(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Scheduled(cron = "0 0/60 * * * ?")
    public void bookingOwnerMaskJobExecute(){
        logger.info("Job is start");
        List<Book> checkOutBooks = bookRepository.findAllByCheckOutLessThan(LocalDate.now());
        checkOutBooks.forEach(book -> {
            book.setFirstName(book.getFirstName().replaceAll(".","*"));
            book.setLastName(book.getLastName().replaceAll(".","*"));
        });

        bookRepository.saveAll(checkOutBooks);

        logger.info("Names and surnames of departing guests masked !");
    }
}
