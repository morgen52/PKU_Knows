package cn.pkucloud.qa.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class FavoriteAnswer extends Answer {
    private String fid;

    public FavoriteAnswer(String fid, Answer answer) {
        super(
                answer.get_id(),
                answer.getStatus(),
                answer.getUid(),
                answer.getQid(),
                answer.getUser(),
                answer.getTxt(),
                answer.getImg(),
                answer.getLike(),
                answer.getDislike(),
                answer.getComment(),
                answer.getFavorite(),
                answer.getReport(),
                answer.getCreateTime());
        this.fid = fid;
    }
}
