package com.example.demo.dto.res;

import com.example.demo.dto.BookDto;
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
