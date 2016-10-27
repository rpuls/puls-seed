package security;

import facades.UserFacade;
import javax.persistence.Persistence;


public class UserFacadeFactory {
    private static  IUserFacade instance = new UserFacade(Persistence.createEntityManagerFactory("pu"));
    public static IUserFacade getInstance(){
        return instance;
    }
}
