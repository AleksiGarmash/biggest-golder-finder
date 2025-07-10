import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {



        String folderPath = "C:\\Users\\Алексей\\Desktop";
        int limit = 50 * 1024 * 1024;

        File file = new File(folderPath);
        Node root = new Node(file, limit);

        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        ForkJoinPool pool = new ForkJoinPool(); // позволяет запускать множество потоков
        pool.invoke(calculator);

        System.out.println(root.toString());
    }
}
