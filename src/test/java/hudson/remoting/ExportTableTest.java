package hudson.remoting;

import junit.framework.TestCase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertThrows;

/**
 * @author Kohsuke Kawaguchi
 */
public class ExportTableTest extends TestCase {
    public void testDiagnosis() throws Exception {
        try {
            ExportTable.EXPORT_TRACES = true;
            ExportTable e = new ExportTable();

            int i = e.export(Object.class, "foo");
            assertEquals("foo", e.get(i));

            e.unexportByOid(i);

            final ExecutionException x = assertThrows(ExecutionException.class, () -> e.get(i));
            StringWriter sw = new StringWriter();
            x.printStackTrace(new PrintWriter(sw));
            assertTrue(sw.toString().contains("Object was recently deallocated"));
            assertTrue(sw.toString().contains("ExportTable.export"));
        } finally {
            ExportTable.EXPORT_TRACES = false;
        }
    }
}
