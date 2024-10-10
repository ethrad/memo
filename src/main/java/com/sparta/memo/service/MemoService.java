package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemoService {
    // Bean class에만 autowired를 사용할 수 있다

    private final MemoRepository memoRepository;

    // lombok 사용해서 주입하는 방법
    // MemoService에 @RequiredArgsConstructor를 달아 사용 가능
    // private final MemoRepository memoRepository; -> final 달면 자동으로 됨

    /*// 필드 주입
    // 추천은 안 함
    @Autowired
    private final MemoRepository memoRepository;*/

    /*// 메소드 주입
    @Autowired
    public void setDi(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }*/

    // 생성자 주입 -> 주로 사용
    // 생성자가 1개일 때는 @Autowired 생략 가능
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(saveMemo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        // DB 조회
        return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional // 변경 감지 하려면 있어야 함
    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        Memo memo = findMemo(id);

        // 변경 감지 되어서 자동으로 변경됨
        memo.update(requestDto);

        return id;
    }

    public Long deleteMemo(Long id) {
        Memo memo = findMemo(id);

        memoRepository.delete(memo);

        return id;
    }

    private Memo findMemo(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
