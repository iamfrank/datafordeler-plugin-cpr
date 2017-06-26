package dk.magenta.datafordeler.cpr;

import dk.magenta.datafordeler.cpr.records.Record;
import dk.magenta.datafordeler.cpr.records.RecordFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lanre.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RecordFactoryTest {
    protected Logger logger = Logger.getLogger(RecordFactoryTest.class);

    //We use the following list to hold the list of records we're interested in extracting from the document.
    private static final ArrayList<String> interested = new ArrayList<String>() {{
        add("001");
        add("002");
        add("003");
    }};
    // The record names to pass for use in the RecordFactory
    private static HashMap<String, String> recordTypeNames = new HashMap<String, String>(){{
        put("001", "personaldatarecord" ); put("002", "currentaddressrecord"); put("003", "validatedaddressrecord");
    }};

    private List<Record> records;

    @Autowired
    RecordFactory recordFactory;

    @Test
    public void testMakeRecords(){
        String file_path = "/Users/lanre/Projects/garr/datafordeler/plugins/CprPlugin/src/test/resources/test_data.txt";
        logger.info("\n\n\n=====> Reading file from path: "+file_path+" <=====\n\n\n");
        try (Stream<String> stream = Files.lines(Paths.get(file_path), Charset.forName(StandardCharsets.ISO_8859_1.name()) ) ) {
            logger.info("\n\n\n=====> Starting tests <=====\n\n\n");
            records = stream.filter(ln -> interested(ln.substring(0, 3)))
            .map(item -> (getRecord(item)))
            .collect(Collectors.toList());
            //TODO Store records or perhaps group them by pnr into a hash map.
            assert(records.size() > 0);
        } catch (Exception ex) {
            // do something with exception
            logger.error("\n\n\n=====> Error detected <=====\n");
            ex.printStackTrace();

        }
    }

    private Record getRecord(String line){
        Supplier<String> recordtype = () -> recordTypeNames.get(StringUtils.substring(line, 0, 3));
        return recordFactory.createRecord(recordtype.get(), line);
    }
    /**
     * This method just checks the hash map to see whether the line key is something that we want.
     * @param recordKey
     * @return
     */
    private boolean interested(String recordKey){
        return interested.contains(recordKey);
    }
}
