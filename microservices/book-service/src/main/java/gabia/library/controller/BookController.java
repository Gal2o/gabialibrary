package gabia.library.controller;

import gabia.library.config.JwtConfig;
import gabia.library.dto.BookPagingResponseDto;
import gabia.library.dto.BookRequestDto;
import gabia.library.dto.BookResponseDto;
import gabia.library.service.BookService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;
    private final JwtConfig jwtConfig;

    @PostMapping("/books")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody @Valid BookRequestDto.Post bookRequestDto) {

        return ResponseEntity.ok(bookService.addBook(bookRequestDto));
    }

    @GetMapping("/books")
    public ResponseEntity<BookPagingResponseDto> getBooks(@RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(bookService.getBooks(page));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> getBookDetails(@PathVariable("id") Long id) {

        return ResponseEntity.ok(bookService.getBookDetails(id));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("id") Long id, @RequestBody @Valid BookRequestDto.Put bookRequestDto) {

        return ResponseEntity.ok(bookService.updateBook(id, bookRequestDto));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> deleteBook(@PathVariable("id") Long id) {

        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @PutMapping("/books/{id}/rent")
    public ResponseEntity<BookResponseDto> rentBook(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = getJwtFromRequest(request);

        return ResponseEntity.ok(bookService.rentBook(id, getIdentifierFromJwt(jwt)));
    }

    @PutMapping("/books/{id}/extension")
    public ResponseEntity<BookResponseDto> extendRent(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = getJwtFromRequest(request);

        return ResponseEntity.ok(bookService.extendRent(id, getIdentifierFromJwt(jwt)));
    }

    @PutMapping("/books/{id}/return")
    public ResponseEntity<BookResponseDto> returnBook(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = getJwtFromRequest(request);

        return ResponseEntity.ok(bookService.returnBook(id, getIdentifierFromJwt(jwt)));
    }

    // TODO: 아래 jwt 관련 메서드 전부 추후에 공통으로 뺼 것
    public boolean isInValidHeader(String header) {
        return Optional.ofNullable(header)
                .map(val -> !val.startsWith(jwtConfig.getPrefix()))
                .orElse(true);
    }

    private String getPureJwtInHeader(String header) {
        return header.replace(jwtConfig.getPrefix(), "");
    }

    private String getJwtFromRequest(HttpServletRequest request) throws AccessDeniedException {
        String token = request.getHeader(jwtConfig.getHeader());

        if(isInValidHeader(token)) {
            throw new AccessDeniedException("유효하지 않은 토큰입니다.");
        }

        return getPureJwtInHeader(token);
    }

    private String getIdentifierFromJwt(String jwt) {
        return getSubjectFromJwt(jwt);
    }

    private String getSubjectFromJwt(String jwt) {
        return Jwts.parser()                                                                // check expired time
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(jwt)
                .getBody().getSubject();
    }
}
