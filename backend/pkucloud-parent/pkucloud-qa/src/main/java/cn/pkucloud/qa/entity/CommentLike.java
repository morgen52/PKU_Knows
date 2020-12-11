package cn.pkucloud.qa.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CommentLike {
    @Id
    private String _id;
}
