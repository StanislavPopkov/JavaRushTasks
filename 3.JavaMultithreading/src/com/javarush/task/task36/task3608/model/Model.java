package com.javarush.task.task36.task3608.model;

public interface Model {
    public ModelData getModelData();
    public void loadUsers();

    void loadDeletedUsers();
    void loadUserById(long userId);
    void deleteUserById(long id);
    void changeUserData(String name, long id, int level);
}
