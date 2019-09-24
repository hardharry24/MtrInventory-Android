package models;

public class SingletonUser {
    private static SingletonUser instance;
    private static User user;
    public static SingletonUser getInstance()
    {
        if (instance == null)
            instance = new SingletonUser();
        return instance;
    }

    public void setUser(User u)
    {
        user  = u;
    }

    public User getUser()
    {
        return user;
    }


}
