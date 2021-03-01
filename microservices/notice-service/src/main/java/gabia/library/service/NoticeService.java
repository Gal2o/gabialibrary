package gabia.library.service;

import gabia.library.domain.Notice;
import gabia.library.domain.NoticeRespository;
import gabia.library.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRespository noticeRespository;

    public NoticeDto addNotice(NoticeDto noticeDto){
        ModelMapper modelMapper = new ModelMapper();

        Notice notice = noticeRespository.save(Notice.builder()
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .isImportant(noticeDto.isImportant())
                .build());

        return modelMapper.map(notice, NoticeDto.class);
    }

    public List<NoticeDto> findAll(){
        List<Notice> notices = noticeRespository.findAll();
        List<NoticeDto> result = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for(Notice n : notices){
            NoticeDto noticeDto = modelMapper.map(n, NoticeDto.class);
            result.add(noticeDto);
        }

        return result;
    }

    public NoticeDto findNotice(Long id){
        Notice notice = noticeRespository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 글입니다."));

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(notice, NoticeDto.class);
    }

    @Transactional
    public NoticeDto updateNotice(NoticeDto noticeDto){
        Notice notice = noticeRespository.findById(noticeDto.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 글입니다."));

        notice.updateNotice(noticeDto.getTitle(), noticeDto.getContent(), noticeDto.isImportant());

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(notice, NoticeDto.class);
    }

    public NoticeDto removeNotice(Long id){
        Notice notice = noticeRespository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 글입니다."));

        noticeRespository.deleteById(id);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(notice, NoticeDto.class);
    }

}
