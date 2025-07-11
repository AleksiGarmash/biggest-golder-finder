import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {

        ParametersBag bag = new ParametersBag(args);

        String folderPath = bag.getPath();
        long limit = bag.getLimit();

        File file = new File(folderPath);
        Node root = new Node(file, limit);

        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        ForkJoinPool pool = new ForkJoinPool(); // позволяет запускать множество потоков
        pool.invoke(calculator);

        System.out.println(root.toString());
    }
}
