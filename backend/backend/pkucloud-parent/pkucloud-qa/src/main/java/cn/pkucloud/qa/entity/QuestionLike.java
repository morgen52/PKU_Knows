package cn.pkucloud.qa.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class QuestionLike {
    @Id
    private String _id;
}
