package ua.com.alevel;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileManagerMethods {


    public static LinkedList<File> createFileList(File file) {
        LinkedList<File> files = new LinkedList<>(Arrays.asList(file.listFiles()));
        return files;
    }


    public static <T> TreeMap<File, T> explore(LinkedList<File> files, TreeMap<File, T> fileMap) {
        for (File file : files) {
            if (file.isFile()) {
                fileMap.put(file, (T) file.getName());
            } else if (file.isDirectory()) {
                Map<File, T> childMap = new TreeMap<>();
                fileMap.put(file, (T) childMap);
                secondExplore(fileMap);
            }
        }
        return fileMap;
    }

    private static <T> void secondExplore(Map<File,T> childMap) {
        for (Map.Entry<File, T> entry : childMap.entrySet()) {
            if (entry.getKey().isDirectory()) {
                TreeMap<File, T> secondExploreMap = (TreeMap<File, T>) entry.getValue();
                explore(createFileList(entry.getKey()), secondExploreMap);
            }
        }
    }



    public static void showResult(TreeMap<File, Object> resultDeep) throws IOException {
        for(Map.Entry<File, Object> entry : resultDeep.entrySet()){
            if(entry.getKey().isFile()){
                System.out.println(entry.getKey().getCanonicalPath() + "\n");
            }else if(entry.getKey().isDirectory()){
                System.out.println("-->" + entry.getKey().getCanonicalPath() + "\\\n");
                showResult((TreeMap<File, Object>) entry.getValue());
            }
        }
    }

//
//    public static LinkedList<File> getResultList(TreeMap<File, Object> resultDeep) throws IOException {
//        LinkedList<File> fileList = new LinkedList<>();
//        for(Map.Entry<File, Object> entry : resultDeep.entrySet()){
//            if(entry.getKey().isFile()){
//                fileList.add(entry.getKey());
//            }else if(entry.getKey().isDirectory()){
//                fileList.add(entry.getKey());
//                getResultList((TreeMap<File, Object>) entry.getValue());
//            }
//        }
//        return fileList;
//    }

//    public static String changeRootDir(String dir, TreeMap<File, Object> resultDeep){
//        for(Map.Entry<File, Object> entry : resultDeep.entrySet()){
//           if(entry.getKey().isDirectory() && entry.getKey().getAbsolutePath().equals(dir)){
//               System.out.println("New Current Directory" + entry.getKey().getPath());
//               String result= entry.getKey().getPath();
//                return  result ;
//            }
//        }
//        throw new RuntimeException("Directory is not found ");
//    }




    //Method is used if specified the root folder of the device.
//    public static String changeRootDir(String dir, LinkedList<File> list){
//        for(File file : list){
//            if(file.isDirectory() && file.getPath().equals(dir)){
//                System.out.println("New Current Directory" + new File(dir).getAbsolutePath());
//                String result = new File(dir).getAbsolutePath();
//                return  result ;
//            }
//        }
//        throw new RuntimeException("Directory is not found ");
//    }




    public static File createDirectory(String currentDirectory, String filename) {
        File file = buildFile(currentDirectory, filename);
        if (file.mkdir()) {
            System.out.println("Directory is created");
        } else
            System.out.println("Directory cannot be created");
        return file;
    }


    public static void createFile(String rootPath) throws IOException {
        String name = FileManagerMethods.getFileName();
        File file = buildFile(rootPath, name);
        try {
            boolean result = file.createNewFile();
            if (result) {
                System.out.println("File is created");
            } else {
                System.out.println("File is already exists");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static File buildFile(String rootPath, String name) {
        String path = rootPath + "\\" + name;
        File file = new File(path);
        return file;
    }



    public static String getFileName() {
        System.out.println("Please enter the name of the file to create one.");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        return name;
    }

    public static String getRoot() {
        System.out.println("Please enter your root directory.");
        Scanner scanner = new Scanner(System.in);
        String root = scanner.nextLine();
        return root;
    }
    public static String menuMode(){
        System.out.println("Welcome to file manager, here you can Create File or Create Directory,Check Current Location and Change Directory .");
        System.out.println("Also you can get the hierarchy structure for certain directory.");
        System.out.println("Choose the function from list and enter it or enter 'Finish' to end the app.");
        System.out.println("Function's list: {Create Directory, Create File, Get File Tree, Change Directory}");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        return command;
    }

    public static void methodsImpl(String command,String root) throws IOException {
        if(command.equals("Create Directory")){
            String fileName = getFileName();
            createDirectory(root,fileName);
            methodsImpl(menuMode(),root);
        }

        if(command.equals("Create File")){
            try {
                createFile(root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            methodsImpl(menuMode(),root);
        }
        if(command.equals("Get File Tree")){
            System.out.println("New Root Directory:\t" +  root + "\n" );
            TreeMap<File, Object> mapFirstExplore = new TreeMap<>();
            LinkedList<File> fileLinkedList = createFileList(new File(root));
            TreeMap<File, Object> mapFirstExploreResult = explore(fileLinkedList, mapFirstExplore);
            showResult(mapFirstExploreResult);
            methodsImpl(menuMode(),root);
        }
        if(command.equals("Change Directory")){
            String newRoot = getNewRoot();
            methodsImpl(menuMode(),newRoot);
        }
        else if(command.equals("Current Location")){
            System.out.println("Current Directory:\t" +  root + "\n" );
        }else if(command.equals("Finish")){
            System.exit(1);
        }

    }

    private static String getNewRoot() {
        System.out.println("Enter new root Directory");
        Scanner scanner = new Scanner(System.in);
        String newRoot = scanner.nextLine();
        return newRoot;
    }


}
