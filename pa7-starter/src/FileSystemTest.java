import static org.junit.Assert.*;

import org.junit.*;

public class FileSystemTest {
    @Test
    public void test_inputFile() {
        FileSystem FS = new FileSystem("input.txt");
        System.out.println(FS.outputNameTree().toString());
        System.out.println(FS.outputDateTree().toString());

        assertEquals("[mySample.txt, mySample1.txt]",
        FS.findFileNamesByDate("2021/02/01").toString());

    }

    @Test 
    public void test_basic(){
        FileSystem FS = new FileSystem();       
        FS.add("hello.txt","/home","2021/01/01");
        FS.add("hello.txt","/user","2021/01/01");
        FS.add("Nihao.txt","/home","2023/05/01");

    }


}