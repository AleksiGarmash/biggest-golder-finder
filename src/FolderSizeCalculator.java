import java.io.File;
import java.util.*;
import java.util.concurrent.RecursiveTask;


/**Многопоточный класс работы с папками/файлами**/
public class FolderSizeCalculator extends RecursiveTask<Long> {

    private Node node;

    public FolderSizeCalculator(Node node) {
        this.node = node;
    }

    @Override
    protected Long compute() {
        File folder = node.getFolder();
        if (folder.isFile()){
            long length = folder.length();
            node.setSize(length);
            return length;
        }

        long sum = 0;
        List<FolderSizeCalculator> subTasks = new LinkedList<>();

        File[] files = folder.listFiles();
        for (File file : files) {
            Node child = new Node(file, node.getLimit());
            FolderSizeCalculator task = new FolderSizeCalculator(child);
            task.fork(); // асинхронный запуск потоков
            subTasks.add(task);
            node.addChild(child);
        }

        for (FolderSizeCalculator task : subTasks){
            sum += task.join(); // выполнение задачи, прибавляется результат
        }

        node.setSize(sum);

        return sum;
    }
}
