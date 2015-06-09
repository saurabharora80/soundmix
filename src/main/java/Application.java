import domain.CommandFactory;

public class Application {
    public static void main(String[] args) {
        CommandFactory.build(args).execute();
    }
}
