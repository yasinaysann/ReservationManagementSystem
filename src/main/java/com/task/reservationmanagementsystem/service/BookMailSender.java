package com.task.reservationmanagementsystem.service;

import com.task.reservationmanagementsystem.entity.Book;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class BookMailSender {

    private final Logger logger = LoggerFactory.getLogger(BookMailSender.class);

    JavaMailSender javaMailSender;

    public BookMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Async
    public void mailSender(Book book) throws MessagingException, MessagingException {

        logger.info("Sending Booking Details");

        String subject = "Booking Information";
        String content = """
                 Dear [[name]],<br>
                 Checkin Date: [[checkIn]]
                 Checkout Date: [[checkOut]]
                 Room Type: [[roomType]]
                 Total Pax: [[totalPax]]<br>
       
                 Thank you for reservation,<br>
                 Ticket Management System""";

        String url = "http://localhost:8080/api/auth";

        content = content.replace("[[name]]", book.getFirstName());
        content = content.replace("[[checkin]]", book.getCheckIn().toString());
        content = content.replace("[[checkout]]", book.getCheckOut().toString());
        content = content.replace("[[roomTtype]]",book.getRoomCode().toString());
        content = content.replace("[[totalPax]]",book.getTotalPax().toString());
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(book.getEmail());


        mimeMessageHelper.setText(content,true);
        //mimeMailMessage.setContent(content,"text/html; charset=utf-8");
        javaMailSender.send(mimeMailMessage);

    };


}
