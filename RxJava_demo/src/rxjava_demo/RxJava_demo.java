
package rxjava_demo;

import io.reactivex.Observable;
//import io.reactivex.functions.Consumer;

public class RxJava_demo {

    public static void main(String[] args) {
        System.out.println("Test");
        Observable.just("String").subscribe(System.out::print);
    }
}
