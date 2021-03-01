package gabia.library.controller;

import gabia.library.dto.NoticeDto;
import gabia.library.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/notices")
    public ResponseEntity<NoticeDto> addNotice(@RequestBody @Valid NoticeDto noticeDto){
        return ResponseEntity.ok(noticeService.addNotice(noticeDto));
    }

    @GetMapping("/notices")
    public ResponseEntity<List<NoticeDto>> findAll(){
        return ResponseEntity.ok(noticeService.findAll());
    }

    @GetMapping("/notices/{id}")
    public ResponseEntity<NoticeDto> findNotice(@PathVariable("id") Long id){
        return ResponseEntity.ok(noticeService.findNotice(id));
    }

    @PutMapping("/notices/{id}")
    public ResponseEntity<NoticeDto> updateNotice(@RequestBody @Valid NoticeDto noticeDto){
        return ResponseEntity.ok(noticeService.updateNotice(noticeDto));
    }

    @DeleteMapping("/notices/{id}")
    public ResponseEntity<NoticeDto> removeNotice(@PathVariable("id") Long id){
        return ResponseEntity.ok(noticeService.removeNotice(id));
    }
}
