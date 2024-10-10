package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long> {

    // 모두 찾기
    // ModifiedAt을 기준으로 내림차순
    List<Memo> findAllByOrderByModifiedAtDesc();
    List<Memo> findAllByUsername(String username);

}
