package ua.com.alevel;

import java.io.IOException;

public class App {


    public static void main(String[] args) throws IOException {
        String rootPath = FileManagerMethods.getRoot();
        FileManagerMethods.methodsImpl(FileManagerMethods.menuMode(),rootPath);

    }


}
