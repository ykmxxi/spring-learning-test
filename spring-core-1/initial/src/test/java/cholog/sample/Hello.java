package cholog.sample;

public class Hello {

    String name;
    Printer printer;

    public Hello() {
    }

    public Hello(final String name, final Printer printer) {
        this.name = name;
        this.printer = printer;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrinter(final Printer printer) {
        this.printer = printer;
    }

    public String sayHello() {
        return "Hello " + this.name;
    }

    public void print() {
        this.printer.print(sayHello());
    }
}
