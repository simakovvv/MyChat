package UserModels.Hibernate.Services;
import UserModels.ClientModel;
import UserModels.Hibernate.DAO.ClientModelDao;
import UserModels.Hibernate.UseridEntity ;


import java.util.List;
public class UserService {

    private ClientModelDao usersDao = new ClientModelDao();
    public UserService() {
    }
    public UseridEntity findUser(int id) {
        return usersDao.findById(id);
    }


    public boolean isRegister(ClientModel clientModel){
        List<UseridEntity> allUsers = usersDao.findAll();
        for (UseridEntity user:allUsers) {
            if(clientModel.getName().equals(user.getName()) && clientModel.getPasword().equals(user.getPassword())){

                return true;
            }
        }

        return false;
    }
    public void saveUser(ClientModel clientModel) {

        UseridEntity user = new UseridEntity();
        user.setName(clientModel.getName());
        user.setPassword(clientModel.getPasword());
        user.setEmail(clientModel.getEMail());
        user.setTimeOfStartSession(clientModel.getTimeOfStartSession());
        user.setRegister((byte) 0);
        user.setIsValid((byte) 1);
        user.setIsOnline((byte) 0);
        usersDao.save(user);
    }
    public void deleteUser(UseridEntity user) {
        usersDao.delete(user);
    }
    public void updateUser(UseridEntity user) {
        usersDao.update(user);
    }
    public List<UseridEntity> findAllUsers() {
        return usersDao.findAll();
    }


 }

