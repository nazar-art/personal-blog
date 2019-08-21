package net.lelyak.edu.web;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Slf4j
@Getter
@Setter
public class PageWrapper<T> {

    @Value("${number.of.posts.per.page}")
    private Integer postsNumber;

    private Page<T> page;
    private List<PageItem> items;
    private int currentNumber;
    private String url;

    public PageWrapper(Page<T> page, String url) {
        this.page = page;
        this.url = url;
        items = Lists.newArrayList();

        if (null == postsNumber) {
            postsNumber = 5;
        }

        currentNumber = page.getNumber() + 1; //start from 1 to match page.page

        int start, size;
        if (page.getTotalPages() <= postsNumber) {
            start = 1;
            size = page.getTotalPages();
        } else {
            if (currentNumber <= postsNumber - postsNumber / 2) {
                start = 1;
                size = postsNumber;
            } else if (currentNumber >= page.getTotalPages() - postsNumber / 2) {
                start = page.getTotalPages() - postsNumber + 1;
                size = postsNumber;
            } else {
                start = currentNumber - postsNumber / 2;
                size = postsNumber;
            }
        }

        for (int i = 0; i < size; i++) {
            items.add(new PageItem(start + i, (start + i) == currentNumber));
        }
    }

    public int getNumber() {
        return currentNumber;
    }

    public List<T> getContent() {
        return page.getContent();
    }

    public int getSize() {
        return page.getSize();
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

    public boolean isFirstPage() {
        return page.isFirst();
    }

    public boolean isLastPage() {
        return page.isLast();
    }

    public boolean isHasPreviousPage() {
        return page.hasPrevious();
    }

    public boolean isHasNextPage() {
        return page.hasNext();
    }

    @Getter
    @AllArgsConstructor
    public class PageItem {
        private int number;
        private boolean current;

        public boolean isCurrent() {
            return this.current;
        }
    }
}
