package model;

import lombok.*;

@Getter @Builder
public class Question {
    private String author;
    private String title;
    private String content;
}
