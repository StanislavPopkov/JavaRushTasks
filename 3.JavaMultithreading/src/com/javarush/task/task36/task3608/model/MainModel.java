package com.javarush.task.task36.task3608.model;

import com.javarush.task.task36.task3608.bean.User;
import com.javarush.task.task36.task3608.model.service.UserService;
import com.javarush.task.task36.task3608.model.service.UserServiceImpl;

import java.util.List;

public class MainModel implements Model {
    private ModelData modelData = new ModelData();
    private UserService userService = new UserServiceImpl();

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    private List<User> getAllUsers() {
        List<User> all = userService.getUsersBetweenLevels(1, 100);
        List<User> filtredAll = userService.filterOnlyActiveUsers(all);
        return filtredAll;
    }

    @Override
    public void loadUsers() {
        List<User> users = getAllUsers();
        modelData.setUsers(users);
        modelData.setDisplayDeletedUserList(false);
    }

    public void loadDeletedUsers() {
        List<User> users = userService.getAllDeletedUsers();
        modelData.setUsers(users);
        modelData.setDisplayDeletedUserList(true);
    }

    public void loadUserById(long userId) {
        //List<User> users = getAllUsers();
        User user = userService.getUsersById(userId);
        modelData.setActiveUser(user);
    }

    public void deleteUserById(long id){
        User user = userService.deleteUser(id);
        modelData.setUsers(getAllUsers());
        modelData.setDisplayDeletedUserList(false);
    }
    public void changeUserData(String name, long id, int level){
        User user = userService.createOrUpdateUser(name, id, level);
        modelData.setUsers(getAllUsers());
        modelData.setDisplayDeletedUserList(false);
    }
}
