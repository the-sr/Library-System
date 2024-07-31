package com.example.demo.payloads.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PagewiseRes<T> {
    private List<T> res;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private boolean isLast;
}
