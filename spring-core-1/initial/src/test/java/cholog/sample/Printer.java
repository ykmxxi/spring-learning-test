package cholog.sample;

public class Printer {

    public void print(final String str) {
        System.out.println(str);
    }

    @Override
    public String toString() {
        return "Hello Spring";
    }
}
