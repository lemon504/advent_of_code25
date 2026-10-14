public class Tuple<T> {

    private T first;
    private T second;

    public Tuple(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T first(){return first;}
    public T second() {return second;}

    public void first(T val) {this.first = val;}
    public void second(T val) {this.second = val;}

    @Override
    public String toString(){
        return "["+first+", "+second+"]";
    }
}
