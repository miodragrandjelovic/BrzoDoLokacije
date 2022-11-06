using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IUserDAL
    {
        bool AddNewUser(User user);
        Task<bool> UserAlreadyExists(string username);

        User? GetUser(string username);
        User GetUser(int userID); 
        bool UpdateUserRole(string username, string roleName);
        bool UpdateUser(User user);
        bool DeleteUser(int userID); 
    }
}
