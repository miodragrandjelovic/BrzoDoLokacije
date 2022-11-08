using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IUserUI
    {
        Task<bool> UserAlreadyExists(string username);

        string? GetLoggedUser();

        string? GetUserEmail();

        string? GetRoleFromLoggedUser();
        Role GetUserRole();

        Response UpdateUserRole(string userName, string roleName);

        List<Role> GetAvailableRolesForUser(string user);

        UserDTO GetUser();

        Response UpdateUser(UserDTO user);

        List<UserDTO> GetAllUsers();

        Response ChangeUserPassword(CredentialsDTO credentials);
    }
}
