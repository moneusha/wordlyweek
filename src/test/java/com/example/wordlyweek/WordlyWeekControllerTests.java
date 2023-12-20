package com.example.wordlyweek;

import com.example.wordlyweek.model.Writer;
import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.repository.WriterJpaRepository;
import com.example.wordlyweek.repository.MagazineJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = { "/schema.sql", "/data.sql" })
public class WordlyWeekControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private MagazineJpaRepository magazineJpaRepository;

        @Autowired
        private WriterJpaRepository writerJpaRepository;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private HashMap<Integer, Object[]> magazinesHashMap = new HashMap<>(); // Magazine
        {
                magazinesHashMap.put(1, new Object[] { "Fantasy Tales", "2023-10-05", new Integer[] { 1 } });
                magazinesHashMap.put(2, new Object[] { "Journalist Weekly", "2023-09-15", new Integer[] { 1, 2 } });
                magazinesHashMap.put(3,
                                new Object[] { "Classic Literature Monthly", "2023-10-15", new Integer[] { 3, 4 } });
                magazinesHashMap.put(4, new Object[] { "Modern Writers Digest", "2023-09-20", new Integer[] { 4 } });
                magazinesHashMap.put(5, new Object[] { "Mystery Weekly", "2023-11-01", new Integer[] { 4 } }); // POST
                magazinesHashMap.put(6, new Object[] { "Dystopia Digest", "2023-12-01", new Integer[] { 5 } }); // PUT
        }

        private HashMap<Integer, Object[]> writersHashMap = new HashMap<>(); // Writer
        {
                writersHashMap.put(1,
                                new Object[] { "John Doe", "Famous writer of fantasy tales", new Integer[] { 1, 2 } });
                writersHashMap.put(2,
                                new Object[] { "Jane Smith", "Renowned journalist and editor", new Integer[] { 2 } });
                writersHashMap.put(3,
                                new Object[] { "Emily Brontë", "Author of Wuthering Heights", new Integer[] { 3 } });
                writersHashMap.put(4, new Object[] { "Ernest Hemingway",
                                "Nobel Prize-winning author known for works like The Old Man and the Sea",
                                new Integer[] { 3, 4 } });
                writersHashMap.put(5, new Object[] { "Agatha Christie",
                                "Best-selling mystery writer known for works like Murder on the Orient Express",
                                new Integer[] { 4, 5 } }); // POST
                writersHashMap.put(6, new Object[] { "George Orwell", "Renowned for novels like 1984 and Animal Farm",
                                new Integer[] { 5 } }); // PUT
        }

        @Test
        @Order(1)
        public void testGetMagazines() throws Exception {
                mockMvc.perform(get("/magazines")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                                .andExpect(jsonPath("$[0].magazineId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].magazineName",
                                                Matchers.equalTo(magazinesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(1)[2])[0])))

                                .andExpect(jsonPath("$[1].magazineId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].magazineName",
                                                Matchers.equalTo(magazinesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].magazineId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].magazineName",
                                                Matchers.equalTo(magazinesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(3)[2])[1])))

                                .andExpect(jsonPath("$[3].magazineId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].magazineName",
                                                Matchers.equalTo(magazinesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(2)
        public void testGetWriters() throws Exception {
                mockMvc.perform(get("/magazines/writers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                                .andExpect(jsonPath("$[0].writerId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].writerName", Matchers.equalTo(writersHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].bio", Matchers.equalTo(writersHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(1)[2])[1])))

                                .andExpect(jsonPath("$[1].writerId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].writerName", Matchers.equalTo(writersHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].bio", Matchers.equalTo(writersHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(2)[2])[0])))

                                .andExpect(jsonPath("$[2].writerId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].writerName", Matchers.equalTo(writersHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].bio", Matchers.equalTo(writersHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(3)[2])[0])))

                                .andExpect(jsonPath("$[3].writerId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].writerName", Matchers.equalTo(writersHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].bio", Matchers.equalTo(writersHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[3].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(3)
        public void testGetMagazineNotFound() throws Exception {
                mockMvc.perform(get("/magazines/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(4)
        public void testGetMagazineById() throws Exception {
                mockMvc.perform(get("/magazines/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.magazineId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.magazineName", Matchers.equalTo(magazinesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(1)[2])[0])));

                mockMvc.perform(get("/magazines/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.magazineId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.magazineName", Matchers.equalTo(magazinesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/magazines/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.magazineId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.magazineName", Matchers.equalTo(magazinesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(3)[2])[1])));

                mockMvc.perform(get("/magazines/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.magazineId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.magazineName", Matchers.equalTo(magazinesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(4)[2])[0])));

        }

        @Test
        @Order(5)
        public void testGetWriterNotFound() throws Exception {
                mockMvc.perform(get("/magazines/writers/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(6)
        public void testGetWriterById() throws Exception {
                mockMvc.perform(get("/magazines/writers/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.writerId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.writerName", Matchers.equalTo(writersHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.bio", Matchers.equalTo(writersHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/magazines/writers/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.writerId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.writerName", Matchers.equalTo(writersHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.bio", Matchers.equalTo(writersHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/magazines/writers/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.writerId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.writerName", Matchers.equalTo(writersHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.bio", Matchers.equalTo(writersHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(3)[2])[0])));

                mockMvc.perform(get("/magazines/writers/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.writerId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.writerName", Matchers.equalTo(writersHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.bio", Matchers.equalTo(writersHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(7)
        public void testPostMagazine() throws Exception {
                String content = "{\n    \"magazineName\": \"" + magazinesHashMap.get(5)[0]
                                + "\",\n    \"publicationDate\": \""
                                + magazinesHashMap.get(5)[1]
                                + "\",\n    \"writers\": [\n        {\n            \"writerId\": "
                                + ((Integer[]) magazinesHashMap.get(5)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/magazines")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.magazineId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.magazineName", Matchers.equalTo(magazinesHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(5)[2])[0])));
        }

        @Test
        @Order(8)
        public void testAfterPostMagazine() throws Exception {
                mockMvc.perform(get("/magazines/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.magazineId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.magazineName", Matchers.equalTo(magazinesHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(5)[2])[0])));
        }

        @Test
        @Order(9)
        @Transactional
        public void testDbAfterPostMagazine() throws Exception {
                Magazine magazine = magazineJpaRepository.findById(5).get();

                assertEquals(magazine.getMagazineId(), 5);
                assertEquals(magazine.getMagazineName(), magazinesHashMap.get(5)[0]);
                assertEquals(magazine.getPublicationDate(), magazinesHashMap.get(5)[1]);
                assertEquals(magazine.getWriters().get(0).getWriterId(), ((Integer[]) magazinesHashMap.get(5)[2])[0]);

                Writer writer = writerJpaRepository.findById(((Integer[]) magazinesHashMap.get(5)[2])[0]).get();

                int i;
                for (i = 0; i < writer.getMagazines().size(); i++) {
                        if (writer.getMagazines().get(i).getMagazineId() == 5) {
                                break;
                        }
                }
                if (i == writer.getMagazines().size()) {
                        throw new AssertionError("Assertion Error: Writer " + writer.getWriterId()
                                        + " has no wordlyweek with magazineId 5");
                }
        }

        @Test
        @Order(10)
        public void testPostWriterBadRequest() throws Exception {
                String content = "{\n    \"writerName\": \"" + writersHashMap.get(5)[0] + "\",\n    \"bio\": \""
                                + writersHashMap.get(5)[1]
                                + "\",\n    \"magazines\": [\n        {\n            \"magazineId\": "
                                + ((Integer[]) writersHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"magazineId\": " + 48
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/magazines/writers")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(11)
        public void testPostWriter() throws Exception {
                String content = "{\n    \"writerName\": \"" + writersHashMap.get(5)[0] + "\",\n    \"bio\": \""
                                + writersHashMap.get(5)[1]
                                + "\",\n    \"magazines\": [\n        {\n            \"magazineId\": "
                                + ((Integer[]) writersHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"magazineId\": "
                                + ((Integer[]) writersHashMap.get(5)[2])[1]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/magazines/writers")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.writerId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.writerName", Matchers.equalTo(writersHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.bio", Matchers.equalTo(writersHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(12)
        public void testAfterPostWriter() throws Exception {
                mockMvc.perform(get("/magazines/writers/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.writerId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.writerName", Matchers.equalTo(writersHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.bio", Matchers.equalTo(writersHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(13)
        @Transactional
        public void testDbAfterPostWriter() throws Exception {
                Writer writer = writerJpaRepository.findById(5).get();

                assertEquals(writer.getWriterId(), 5);
                assertEquals(writer.getWriterName(), writersHashMap.get(5)[0]);
                assertEquals(writer.getBio(), writersHashMap.get(5)[1]);
                assertEquals(writer.getMagazines().get(0).getMagazineId(), ((Integer[]) writersHashMap.get(5)[2])[0]);
                assertEquals(writer.getMagazines().get(1).getMagazineId(), ((Integer[]) writersHashMap.get(5)[2])[1]);

                Magazine magazine = magazineJpaRepository.findById(((Integer[]) writersHashMap.get(5)[2])[0]).get();

                int i;
                for (i = 0; i < magazine.getWriters().size(); i++) {
                        if (magazine.getWriters().get(i).getWriterId() == 5) {
                                break;
                        }
                }
                if (i == magazine.getWriters().size()) {
                        throw new AssertionError("Assertion Error: Magazine " + magazine.getMagazineId()
                                        + " has no writer with writerId 5");
                }

                magazine = magazineJpaRepository.findById(((Integer[]) writersHashMap.get(5)[2])[1]).get();
                for (i = 0; i < magazine.getWriters().size(); i++) {
                        if (magazine.getWriters().get(i).getWriterId() == 5) {
                                break;
                        }
                }
                if (i == magazine.getWriters().size()) {
                        throw new AssertionError("Assertion Error: Magazine " + magazine.getMagazineId()
                                        + " has no writer with writerId 5");
                }
        }

        @Test
        @Order(14)
        public void testPutMagazineNotFound() throws Exception {
                String content = "{\n    \"magazineName\": \"" + magazinesHashMap.get(6)[0]
                                + "\",\n    \"publicationDate\": \""
                                + magazinesHashMap.get(6)[1]
                                + "\",\n    \"writers\": [\n        {\n            \"writerId\": "
                                + ((Integer[]) magazinesHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/magazines/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(15)
        public void testPutMagazine() throws Exception {
                String content = "{\n    \"magazineName\": \"" + magazinesHashMap.get(6)[0]
                                + "\",\n    \"publicationDate\": \""
                                + magazinesHashMap.get(6)[1]
                                + "\",\n    \"writers\": [\n        {\n            \"writerId\": "
                                + ((Integer[]) magazinesHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/magazines/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.magazineId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.magazineName", Matchers.equalTo(magazinesHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(16)
        public void testAfterPutMagazine() throws Exception {
                mockMvc.perform(get("/magazines/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.magazineId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.magazineName", Matchers.equalTo(magazinesHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(17)
        @Transactional
        public void testDbAfterPutMagazine() throws Exception {
                Magazine magazine = magazineJpaRepository.findById(5).get();

                assertEquals(magazine.getMagazineId(), 5);
                assertEquals(magazine.getMagazineName(), magazinesHashMap.get(6)[0]);
                assertEquals(magazine.getPublicationDate(), magazinesHashMap.get(6)[1]);
                assertEquals(magazine.getWriters().get(0).getWriterId(), ((Integer[]) magazinesHashMap.get(6)[2])[0]);

                Writer writer = writerJpaRepository.findById(((Integer[]) magazinesHashMap.get(6)[2])[0]).get();

                int i;
                for (i = 0; i < writer.getMagazines().size(); i++) {
                        if (writer.getMagazines().get(i).getMagazineId() == 5) {
                                break;
                        }
                }
                if (i == writer.getMagazines().size()) {
                        throw new AssertionError("Assertion Error: Writer " + writer.getWriterId()
                                        + " has no wordlyweek with magazineId 5");
                }
        }

        @Test
        @Order(18)
        public void testPutWriterNotFound() throws Exception {
                String content = "{\n    \"writerName\": \"" + writersHashMap.get(6)[0] + "\",\n    \"bio\": \""
                                + writersHashMap.get(6)[1]
                                + "\",\n    \"magazines\": [\n        {\n            \"magazineId\": "
                                + ((Integer[]) writersHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/magazines/writers/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(19)
        public void testPutWriterBadRequest() throws Exception {
                String content = "{\n    \"writerName\": \"" + writersHashMap.get(6)[0] + "\",\n    \"bio\": \""
                                + writersHashMap.get(6)[1]
                                + "\",\n    \"magazines\": [\n        {\n            \"magazineId\": "
                                + 48 + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/magazines/writers/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(20)
        public void testPutWriter() throws Exception {
                String content = "{\n    \"writerName\": \"" + writersHashMap.get(6)[0] + "\",\n    \"bio\": \""
                                + writersHashMap.get(6)[1]
                                + "\",\n    \"magazines\": [\n        {\n            \"magazineId\": "
                                + ((Integer[]) writersHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/magazines/writers/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.writerId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.writerName", Matchers.equalTo(writersHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.bio", Matchers.equalTo(writersHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(21)
        public void testAfterPutWriter() throws Exception {

                mockMvc.perform(get("/magazines/writers/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.writerId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.writerName", Matchers.equalTo(writersHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.bio", Matchers.equalTo(writersHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(22)
        @Transactional
        public void testDbAfterPutWriter() throws Exception {
                Writer writer = writerJpaRepository.findById(5).get();

                assertEquals(writer.getWriterId(), 5);
                assertEquals(writer.getWriterName(), writersHashMap.get(6)[0]);
                assertEquals(writer.getBio(), writersHashMap.get(6)[1]);
                assertEquals(writer.getMagazines().get(0).getMagazineId(), ((Integer[]) writersHashMap.get(6)[2])[0]);

                Magazine magazine = magazineJpaRepository.findById(((Integer[]) writersHashMap.get(6)[2])[0]).get();

                int i;
                for (i = 0; i < magazine.getWriters().size(); i++) {
                        if (magazine.getWriters().get(i).getWriterId() == 5) {
                                break;
                        }
                }
                if (i == magazine.getWriters().size()) {
                        throw new AssertionError("Assertion Error: Magazine " + magazine.getMagazineId()
                                        + " has no writer with writerId 5");
                }
        }

        @Test
        @Order(23)
        public void testDeleteWriterNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/magazines/writers/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(24)
        public void testDeleteWriter() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/magazines/writers/5");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(25)
        @Transactional
        @Rollback(false)
        public void testAfterDeleteWriter() throws Exception {
                mockMvc.perform(get("/magazines/writers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                                .andExpect(jsonPath("$[0].writerId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].writerName", Matchers.equalTo(writersHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].bio", Matchers.equalTo(writersHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(1)[2])[1])))

                                .andExpect(jsonPath("$[1].writerId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].writerName", Matchers.equalTo(writersHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].bio", Matchers.equalTo(writersHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(2)[2])[0])))

                                .andExpect(jsonPath("$[2].writerId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].writerName", Matchers.equalTo(writersHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].bio", Matchers.equalTo(writersHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(3)[2])[0])))

                                .andExpect(jsonPath("$[3].writerId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].writerName", Matchers.equalTo(writersHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].bio", Matchers.equalTo(writersHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[3].magazines[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(4)[2])[1])));

                Magazine magazine = magazineJpaRepository.findById(((Integer[]) writersHashMap.get(6)[2])[0]).get();

                for (Writer writer : magazine.getWriters()) {
                        if (writer.getWriterId() == 5) {
                                throw new AssertionError("Assertion Error: Writer " + writer.getWriterId()
                                        + " and Magazine 5 are still linked");
                        }
                }
        }

        @Test
        @Order(26)
        public void testDeleteMagazineNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/magazines/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(27)
        public void testDeleteMagazine() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/magazines/5");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(28)
        public void testAfterDeleteMagazine() throws Exception {
                mockMvc.perform(get("/magazines")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                                .andExpect(jsonPath("$[0].magazineId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].magazineName",
                                                Matchers.equalTo(magazinesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(1)[2])[0])))

                                .andExpect(jsonPath("$[1].magazineId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].magazineName",
                                                Matchers.equalTo(magazinesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].magazineId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].magazineName",
                                                Matchers.equalTo(magazinesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(3)[2])[1])))

                                .andExpect(jsonPath("$[3].magazineId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].magazineName",
                                                Matchers.equalTo(magazinesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].publicationDate",
                                                Matchers.equalTo(magazinesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].writers[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(29)
        public void testGetMagazineByWriterId() throws Exception {
                mockMvc.perform(get("/writers/1/magazines")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/writers/2/magazines")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/writers/3/magazines")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(3)[2])[0])));

                mockMvc.perform(get("/writers/4/magazines")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[*].magazineId",
                                                hasItem(((Integer[]) writersHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(30)
        public void testGetWriterByMagazineId() throws Exception {
                mockMvc.perform(get("/magazines/1/writers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(1)[2])[0])));

                mockMvc.perform(get("/magazines/2/writers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/magazines/3/writers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(3)[2])[1])));

                mockMvc.perform(get("/magazines/4/writers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].writerId",
                                                hasItem(((Integer[]) magazinesHashMap.get(4)[2])[0])));

        }

        @AfterAll
        public void cleanup() {
                jdbcTemplate.execute("drop table writer_magazine");
                jdbcTemplate.execute("drop table writer");
                jdbcTemplate.execute("drop table magazine");
        }

}