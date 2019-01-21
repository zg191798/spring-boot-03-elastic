package com.atguigu.springboot.repository;

import com.atguigu.springboot.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author zhangge
 * @date 2019/1/21 - 21:28
 */
public interface BookRepository extends ElasticsearchRepository<Book, Integer> {

    public List<Book> findByBookNameLike(String bookName);
}
