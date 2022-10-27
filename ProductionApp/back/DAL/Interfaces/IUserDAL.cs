using PyxisKapriBack.DTOComponents;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IUserDAL
    {
        void AddNewUser(User user);
        Task<bool> UserAlreadyExists(string username);

        User? GetUser(string username);
    }
}
