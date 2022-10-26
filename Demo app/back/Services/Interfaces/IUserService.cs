using PyxisKapriBack.Models.DTO_Components;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface IUserService
    {
        void AddNewUser(User user);
        Task<bool> UserAlreadyExists(string username);

        User? GetUser(string usernameOrEmail);

        string? GetLoggedUser();
        
        string? GetUserEmail();
    }
}
