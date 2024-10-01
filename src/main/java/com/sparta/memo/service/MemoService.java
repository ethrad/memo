package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;

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
        return memoRepository.findAll();
    }

    public Long updateMemo(Long id, MemoRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if (memo != null) {
            // memo 내용 수정
            memoRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public Long deleteMemo(Long id) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if (memo != null) {
            memoRepository.delete(id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
