package library.dto.res;

import library.dto.BookDto;
import library.dto.UserBookDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookHistoryRes {
    private BookDto book;
    private UserBookDto userBookDto;
}