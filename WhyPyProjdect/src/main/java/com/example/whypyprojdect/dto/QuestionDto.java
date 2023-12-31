package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.Question;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;

@Data
public class QuestionDto {
    private int id;
    private int category;
    private String title;
    private int level;
    private String answer;
    private Boolean solved;
    private String contents;
    private String example;

    public void setQId(int id){this.id = id;}
    public void setQCategory(int category) {
        this.category = category;
    }

    public void setQTitle(String title) {
        this.title = title;
    }

    public void setQLevel(int level) {
        this.level = level;
    }

    public void setQAnswer(String answer){this.answer = answer;}

    public void setQSolved(Boolean solved) {this.solved = solved;}
    public void setQContents(String contents){this.contents = contents;}

    public void setQExample(String example){this.example = example;}

    public static QuestionDto fromEntity(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setCategory(question.getCategory());
        questionDto.setTitle(question.getTitle());
        questionDto.setLevel(question.getLevel());
        questionDto.setAnswer(question.getAnswer());
        questionDto.setContents(question.getContents());
        questionDto.setExample(question.getExample());
        questionDto.setQSolved(question.getSolved());
        // 나머지 필드도 동일하게 설정

        return questionDto;
    }

}
