package cn.pkucloud.qa.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("favorite")
public class Favorite {
}
