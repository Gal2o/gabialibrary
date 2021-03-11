package gabia.library.controller;

import gabia.library.dto.BookRequestDto;
import gabia.library.dto.NaverBook;
import gabia.library.service.BookRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@RestController
public class BookRequestController {

    private final BookRequestService bookRequestService;

    @PostMapping("/request-list")
    public ResponseEntity<BookRequestDto> addBookRequest(@RequestBody @Valid BookRequestDto bookRequestDto){
        return null;
    }

    @GetMapping("/request-list/{title}")
    public ResponseEntity<NaverBook> getBookByNaverApi(@PathVariable("title") String title) {
        return ResponseEntity.ok(bookRequestService.getBookByNaverApi(title));
    }

    @PutMapping("/request-list/{id}/confirm")
    public ResponseEntity<BookRequestDto> confirmBookRequest(@PathVariable("id") Long id){
        return null;
    }

    @PutMapping("/request-list/{id}/cancel")
    public ResponseEntity<BookRequestDto> cancelBookRequest(@PathVariable("id") Long id){
        return null;
    }
}
