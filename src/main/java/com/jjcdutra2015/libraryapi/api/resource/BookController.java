package com.jjcdutra2015.libraryapi.api.resource;

import com.jjcdutra2015.libraryapi.api.dto.BookDTO;
import com.jjcdutra2015.libraryapi.exception.ApiErrors;
import com.jjcdutra2015.libraryapi.model.entity.Book;
import com.jjcdutra2015.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService service;
    private ModelMapper modelMapper;

    public BookController(BookService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto) {
        Book entity = modelMapper.map(dto, Book.class);
        entity = service.save(entity);
        return modelMapper.map(entity, BookDTO.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleExceptionValidation(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErrors(bindingResult);
    }
}
