import common.Base;
import common.Collector;
import common.Url;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CollectorTest extends Base {

    @BeforeEach
    public void setUp() {
        createChromeDriver();
        openUrl(Url.OKTAGON);
    }

    @Test
    public void collector() throws IOException, InterruptedException {
        Collector collector = new Collector();
        collector.collect();
    }

    @AfterEach
    public void close() {
        quitDriver();
    }
}
