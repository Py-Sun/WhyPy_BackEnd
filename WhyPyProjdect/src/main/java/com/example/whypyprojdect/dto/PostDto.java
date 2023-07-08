package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.Post;
import lombok.Data;

@Data
public class PostDto {
    private int postId;
    private String createDate;
    private String updateDate;
    private String title;
    private String contents;

    // 생성자, getter, setter 등 필요한 메서드 추가

    public void setPostId(int postId) {this.postId = postId;}

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(String updateDate) {this.updateDate = updateDate; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {this.contents = contents;}

    public PostDto(String title, String contents, String createDate, String updateDate) {
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .title(title)
                .contents(contents)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}