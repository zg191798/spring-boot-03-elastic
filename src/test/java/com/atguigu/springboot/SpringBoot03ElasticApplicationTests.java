package com.atguigu.springboot;

import com.atguigu.springboot.bean.Article;
import com.atguigu.springboot.bean.Book;
import com.atguigu.springboot.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot03ElasticApplicationTests {

    @Autowired
    JestClient jestClient;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void test02(){
        Book book = new Book();
        bookRepository.index(book);

        for (Book b : bookRepository.findByBookNameLike("游")){
            System.out.println(b);
        }
    }

    @Test
    public void contextLoads() {
        //1.给Es中索引保存一个文档
        Article article = new Article();
        article.setId(1);
        article.setTitle("好消息");
        article.setAuthor("zhangsan");
        article.setContent("Hello World");

        // 构建一个索引功能
        Index index = new Index.Builder(article).index("atguigu").type("news").build();

        try {
            // 执行
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void search() {
        String json = "{\n" +
                "   \"query\":{\n"+
                "   \"match\":{\n" +
                "   \"content\":\"hello\"\n" +
                "       }\n"+
                "      }\n"+
                "}";

        // 构建搜索功能
        Search search = new Search.Builder(json).addIndex("atguigu").addType("news").build();

        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

