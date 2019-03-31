package com.aztu68.spring.greenwich.genesis.controller;

import com.aztu68.spring.greenwich.genesis.configuration.AmazonProperties;
import com.aztu68.spring.greenwich.genesis.model.Book;
import com.aztu68.spring.greenwich.genesis.model.Reader;
import com.aztu68.spring.greenwich.genesis.repository.ReadingListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class ReadListController {

    private ReadingListRepository readingListRepository;
    private AmazonProperties amazonProperties;

    public ReadListController(
            ReadingListRepository readingListRepository,
            AmazonProperties amazonProperties) {

        this.readingListRepository = readingListRepository;
        this.amazonProperties = amazonProperties;
    }

    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public void fail() {

        throw new RuntimeException();
    }

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
//    public String error() {
//
//        return "error";
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String readersBooks(
            Reader reader,
            Model model) {

        var optReadingList =
                Optional.ofNullable(readingListRepository.findByReader(reader));
        optReadingList.ifPresent(
                (readingList) -> model.addAttribute("books", readingList));

        model.addAttribute("reader", reader);
        model.addAttribute("amazonID", amazonProperties.getAssociateId());

        return "readingList";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addToReadingList(
            Reader reader,
            Book book) {

        book.setReader(reader);
        readingListRepository.save(book);

        return "redirect:/";
    }
}
