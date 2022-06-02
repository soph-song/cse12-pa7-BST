import static org.junit.Assert.*;

import org.junit.*;

public class FileSystemTest {
    @Test
    public void test_basic() {
        FileSystem FS = new FileSystem("input.txt");
        System.out.println(FS.outputNameTree().toString());
        System.out.println(FS.outputDateTree().toString());
    }


}