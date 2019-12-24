package com.communitydemo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious = true;
    private boolean showFirstPage = true;
    private boolean showNext = true;
    private boolean showEndPage = true;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }
        this.page = page;

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0,page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        if (page == 1) {
            showPrevious = false;
        }
        if (page == totalPage) {
            showNext = false;
        }
        if (pages.contains(1)) {
            showFirstPage = false;
        }
        if (pages.contains(totalPage)) {
            showEndPage = false;
        }
    }
}
