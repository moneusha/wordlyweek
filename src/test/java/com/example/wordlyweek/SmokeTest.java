package com.example.wordlyweek;

import com.example.wordlyweek.controller.MagazineController;
import com.example.wordlyweek.controller.WriterController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private WriterController writerController;

    @Autowired
    private MagazineController magazineController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(writerController).isNotNull();
        assertThat(magazineController).isNotNull();
    }
}
