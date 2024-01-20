package com.task.reservationmanagementsystem.controller;

import com.task.reservationmanagementsystem.entity.Book;
import com.task.reservationmanagementsystem.model.RateCode;
import com.task.reservationmanagementsystem.model.RoomCode;
import com.task.reservationmanagementsystem.service.BookService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/book")
@PreAuthorize("hasRole('ADMIN')")
public class BookController {
    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

   @GetMapping
   public ResponseEntity<List<Book>> getBookByParameters(@RequestParam(value = "checkIn",required = false) LocalDate checkIn,
                                                   @RequestParam(value = "checkOut", required = false)LocalDate checkOut,
                                                   @RequestParam(value = "roomCode", required = false)RoomCode roomCode,
                                                   @RequestParam(value = "rateCode", required = false)RateCode rateCode){

        return ResponseEntity.ok(bookService.getBookByParameters(checkIn,checkOut,roomCode,rateCode));
   }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) throws MessagingException {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @PatchMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBook(@RequestBody Book book){

        return ResponseEntity.ok(bookService.deleteBook(book));
    }

}
