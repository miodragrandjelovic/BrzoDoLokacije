namespace PyxisKapriBack.Services.Interfaces
{
    public interface IUserService
    {
        void AddNewUser(User user);
        Task<bool> UserAlreadyExists(string username);

        User? GetUser(string username); 
    }
}
