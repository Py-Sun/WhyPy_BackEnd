package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>, JpaSpecificationExecutor<Question> {
    // 필요한 추가 메서드를 선언할 수 있습니다.
    List<Question> findByTitleContaining(String keyword);
}