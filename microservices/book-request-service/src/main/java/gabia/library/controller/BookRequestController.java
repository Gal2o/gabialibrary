package gabia.library.controller;

import gabia.library.dto.BookRequestDto;
import gabia.library.service.BookRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@RestController
public class BookRequestController {

    private final BookRequestService bookRequestService;

    @PostMapping("/request-list")
    public ResponseEntity<BookRequestDto> addBookRequest(@RequestBody @Valid BookRequestDto bookRequestDto){
        return null;
    }

    @GetMapping("/request-list")
    public ResponseEntity<BookRequestDto> getBookRequest(){
        return bookRequestService.getBookRequest();
    }

    @GetMapping("/request-list/{title}")
    public ResponseEntity<BookRequestDto> getBookByNaverApi(@PathVariable("title") String title) {
        return null;
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
