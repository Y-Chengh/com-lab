package semantic;

import java.util.ArrayList;

public class myList<E> extends ArrayList<E> {
    @Override
    public boolean add(E e){
        System.out.println("add");
        return super.add(e);
    }

    public void add1(){
        System.out.println("aaaa");
    }
}
