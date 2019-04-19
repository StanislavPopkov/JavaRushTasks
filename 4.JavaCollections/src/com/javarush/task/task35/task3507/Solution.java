package com.javarush.task.task35.task3507;

import com.javarush.task.task35.task3507.data.Cat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        String path = pathToAnimals;
        Set<Animal> set = new HashSet<>();
        File file = new File(path);
        File[] files = file.listFiles();

        for(File f: files){
            try {
                String packageName = Solution.class.getPackage().getName() + ".data.";
                //Class ourObject = Class.forName(packageName+f.getName().replace(".class", ""));
                Class ourObject = new OurLoader().loadClass(f.toPath(), packageName);
                //Class<?>[] interfaces = ourObject.getClass().getInterfaces();
                //Constructor[] constructors = ourObject.getConstructors();
                Animal animal = (Animal) ourObject.newInstance();
                set.add(animal);
            }catch (Exception e){
                continue;
            }
        }

        return set;
    }
    static class OurLoader extends ClassLoader{
        public Class<?> loadClass(Path path, String packageName) {
            try {
                String className = packageName + "." + path.getFileName().toString().replace(".class", "");
                byte[] b = Files.readAllBytes(path);
                return defineClass(null, b, 0, b.length); //here main magic
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
