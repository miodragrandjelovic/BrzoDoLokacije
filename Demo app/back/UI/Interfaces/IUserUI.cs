namespace PyxisKapriBack.UI.Interfaces
{
    public interface IUserUI
    {
        void AddNewUser(User user);
        Task<bool> UserAlreadyExists(string username);

        User? GetUser(string usernameOrEmail);

        string? GetLoggedUser();

        string? GetUserEmail();

        string? GetRoleFromLoggedUser();
        Role GetUserRole();
    }
}
