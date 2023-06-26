package dev.profitsoft.data;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document("Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder
public class UserData {

    @Id
    private String _id;

    @Field("user_email")
    @Indexed
    private String email;

    @Field("password")
    private String password;

    @Field("user_role")
    private UserRole userRole;

    @DBRef
    @Field("user_books")
    private List<BookData> userBooks;
}
