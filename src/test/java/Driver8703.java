import hudson.remoting.PipeTest;
import org.jvnet.hudson.test.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Test bed to reproduce JENKINS-8703.
 *
 * @author Kohsuke Kawaguchi
 */
public class Driver8703 {
    @Issue("JENKINS-8703")
    public static void main(String[] args) throws Throwable {

        ExecutorService es = Executors.newCachedThreadPool();
        List<Future<Object>> flist = new ArrayList<>();
        for (int i=0; i<10000; i++) {
            flist.add(es.submit(() -> {
                Thread.currentThread().setName("testing");
                try {
                    foo();
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                } catch (Throwable t) {
                    t.printStackTrace();
                    throw new Exception(t);
                } finally {
                    Thread.currentThread().setName("done");
                }
            }));
        }

        for (Future<Object> ff : flist) {
            ff.get();
        }

        System.out.println("All done");
        es.shutdown();
    }

    private static void foo() throws Throwable {
        PipeTest t = new PipeTest();
        t.setName("testSaturation");
        t.runBare();
    }
}
