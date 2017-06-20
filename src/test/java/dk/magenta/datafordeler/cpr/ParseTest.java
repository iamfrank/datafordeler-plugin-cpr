package dk.magenta.datafordeler.cpr;

import dk.magenta.datafordeler.cpr.parsers.PersonParser;
import dk.magenta.datafordeler.cpr.records.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by lars on 14-06-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ParseTest {

    @Autowired
    private PersonParser personParser;

    public static final String RECORDTYPE_START = "000";
    public static final String RECORDTYPE_SLUT = "999";

    @Test
    public void testParse() {
        InputStream testData = ParseTest.class.getResourceAsStream("/cprdata.txt");
        List<Record> records = personParser.parse(testData, "utf-8");
        for (Record record : records) {
            System.out.println(record);
        }

        try (Stream<String> stream = Files.lines(Paths.get("/cprdata.txtt"), Charset.forName(StandardCharsets.ISO_8859_1.name()))) {
            stream.filter(ln -> !ln.startsWith(RECORDTYPE_START) || !ln.startsWith(RECORDTYPE_SLUT))
            .forEach(System.out::println);
        } catch (IOException ex) {
            // do something with exception
        }
    }

    protected void processRecordLine(String line){

    }
}
