package Main;

import methods.HTTPMethods;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int swValue;
        HTTPMethods method = new HTTPMethods();
        do {
            System.out.println("|      MENU SELECTION      |");
            System.out.println("| Options:                 |");
            System.out.println("|        1. Get            |");
            System.out.println("|        2. Post           |");
            System.out.println("|        3. Put            |");
            System.out.println("|        4. Patch          |");
            System.out.println("|        5. Delete         |");
            System.out.println("|        0. Exit           |");
            swValue = Keyin.inInt(" Select option: ");

            switch (swValue) {
                case 1:
                    System.out.println(method.get());
                    Keyin.inInt(" Press any key to back: ");
                    break;
                case 2:
                    System.out.println(method.post());
                    Keyin.inInt(" Press any key to back: ");
                    break;
                case 3:
                    System.out.println(method.put());
                    Keyin.inInt(" Press any key to back: ");
                    break;
                case 4:
                    System.out.println(method.patch());
                    Keyin.inInt(" Press any key to back: ");
                    break;
                case 5:
                    System.out.println(method.delete());
                    Keyin.inInt(" Press any key to back: ");
                    break;
                case 0:
                    System.out.println("Exit selected");
                    break;
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        } while(swValue != 0);

    }
}
