package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
//        String sep = System.getProperty("file.separator");
        File file = new File(packageName);
        File[] nameMass = file.listFiles();
        for(File fileName : nameMass) {
            String packageName = Solution.class.getPackage().getName() + ".data.second.";
            Class ourClazz = new OurLoader().loadClass(fileName.toPath(), packageName);
            //Class ourClazz = Class.forName(packageName+fileName.getName().replace(".class", ""));
//            String str = fileName.getAbsolutePath();
//            String[] splitMass = str.split("\\.");
//            str = splitMass[1].replace("\\", ".");
//            str = str.substring(str.indexOf(".")+1);
            hiddenClasses.add(ourClazz);
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        //String rightKey = key.substring(0, 1).toUpperCase() + key.substring(1);
        try {
            for(Class clazz : hiddenClasses){
                List<Class> classList = Arrays.asList(clazz.getInterfaces());
                String clazzStr = clazz.getSimpleName().toLowerCase();
                if(clazzStr.startsWith(key) & classList.contains(HiddenClass.class)) {
                    Constructor[] constructors = clazz.getDeclaredConstructors();
                    for (Constructor con : constructors) {
                        if (con.getParameterTypes().length == 0) {
                            con.setAccessible(true);
                            return (HiddenClass) con.newInstance();
                        }
                    }
                }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    static class OurLoader extends ClassLoader{
        public Class<?> loadClass(Path path, String packageName) {
            if(path.getFileName().toString().endsWith(".class")) {
                try {
                    String className = packageName + "." + path.getFileName().toString().replace(".class", "");
                    byte[] b = Files.readAllBytes(path);
                    return defineClass(null, b, 0, b.length); //here main magic
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
            return null;
        }
    }
}

