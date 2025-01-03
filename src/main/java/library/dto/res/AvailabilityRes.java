package library.dto.res;

import library.dto.BookDto;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityRes {
    private BookDto book;
    private boolean isAvailable;
}