package facades;

import security.IUserFacade;
import entity.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

    /*When implementing your own database for this seed, you should NOT touch any of the classes in the security folder
    Make sure your new facade implements IUserFacade and keeps the name UserFacade, and that your Entity User class implements 
    IUser interface, then security should work "out of the box" with users and roles stored in your database */
    private final Map<String, IUser> users = new HashMap<>();

    EntityManagerFactory emf;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UserFacade(EntityManagerFactory emf) {
        this.emf = emf;
        //Test Users
        User user = new User("user", "test");
        user.addRole("User");
        users.put(user.getUserName(), user);
        User admin = new User("admin", "test");
        admin.addRole("Admin");
        users.put(admin.getUserName(), admin);

        User both = new User("user_admin", "test");
        both.addRole("User");
        both.addRole("Admin");
        users.put(both.getUserName(), both);
    }

    @Override
    public IUser getUserByUserId(String id) {
        return users.get(id);
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String userName, String password) {
        try {
            IUser user = users.get(userName);
            if (user != null && PasswordStorage.verifyPassword(password, user.getPassword())) {
                return user.getRolesAsStrings();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
            //throw e;
            return null;
        }
    }

    public User addUser(User usr) {
        EntityManager em = getEntityManager();
        try {
            String pass = usr.getPassword();
            pass = PasswordStorage.createHash(pass);
            usr.setPassword(pass);
            em.getTransaction().begin();
            em.persist(usr);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error" + e);
            //throw e;
        } finally {
            em.close();
        }
        return usr;
    }

}
