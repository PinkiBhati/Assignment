import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Q7 implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(10);
        return "hello from "+Thread.currentThread().getName();
    }

    public static void main(String args[]){

        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Future<String>> list = new ArrayList<Future<String>>();
        Callable<String> callable = new Q7();
        for(int i=0; i< 5; i++){

            Future<String> future = service.submit(callable);
            list.add(future);
        }
        for(Future<String> fut : list){
            try {
                System.out.println(fut.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        service.shutdown();

    }

}