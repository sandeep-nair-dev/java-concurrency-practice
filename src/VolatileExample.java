///**
// * <h3><b>Volatile Keyword</b></h3>
// * <p> Whenever a thread runs, it keeps a copy of the variables that it encounters and stores it in the Thread's local cache and everytime required,
// * it accesses from its cache to reduce time. Hence in our example the writer has updated the value but since the reader is fetching the value from its local cache,
// * it is unable to know about the updated value of the flag.</p>
// * <p> The Volatile keyword is used to indicate that a variable's value will be modified by different threads.
// * It ensures that changes to a variable are always visible to other threads by preventing the caching of the variable and making sure that any change to the variable is
// * immediately propagated to the main memory, preventing thread caching issues.</p>
// */
//public class VolatileExample {
//    public static void main(String[] args) {
//        SharedResource sharedResource = new SharedResource();
//         Thread writer = new Thread(()->{
//             try {
//                 Thread.sleep(1000);
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//             }
//             sharedResource.setFlagTrue();
//         });
//
//         Thread reader  = new Thread(sharedResource::printIfFlagIsTrue);
//
//         writer.start();
//         reader.start();
//         throw new RuntimeException()
//    }
//}
//
//class SharedResource{
//    private volatile boolean flag=false;
//
//    public void printIfFlagIsTrue(){
//        while(!flag){
//            //do nothing
////            System.out.println("Flag is false");          // due to this sometimes it might give correct o/p, because sout requried lock on print.out and it may make the thread to fetch value again from main memory instead of cache, but we can't be sure about that
//        }
//        System.out.println("Flag is true");
//    }
//
//    public void setFlagTrue(){
//        flag=true;
//        System.out.println("Flag set true by writer.");
//    }
//}