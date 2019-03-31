package com.aztu68.spring.greenwich.genesis.repository;

import com.aztu68.spring.greenwich.genesis.model.Book;
import com.aztu68.spring.greenwich.genesis.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListRepository extends JpaRepository<Book, Long> {

    List<Book> findByReader(Reader reader);
}
