package dev.profitsoft.data;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document("Books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder
public class BookData {

    @Id
    private String _id;

    @Field("author_name")
    private String authorName;

    @Field("book_name")
    private String bookName;

    @Field("book_text")
    private String bookText;

    @DBRef
    @Field("book_owners")
    private List<UserData> bookOwners;

}
