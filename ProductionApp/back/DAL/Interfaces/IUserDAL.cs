using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IUserDAL
    {
        void AddNewUser(User user);
        Task<bool> UserAlreadyExists(string username);

        User? GetUser(string username);
        bool UpdateUserRole(string username, string roleName);
        bool UpdateUser(User user);
        void DeleteUser(int userID); 
    }
}
