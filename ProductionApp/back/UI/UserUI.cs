using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class UserUI : IUserUI
    {
        private readonly IUserService userService;

        public UserUI(IUserService userService)
        {
            this.userService = userService;
        }
        public List<Role> GetAvailableRolesForUser(string user)
        {
            return userService.GetAvailableRolesForUser(user);
        }

        public string? GetLoggedUser()
        {
            return userService.GetLoggedUser();
        }

        public string? GetRoleFromLoggedUser()
        {
            return userService.GetRoleFromLoggedUser();
        }

        public UserDTO? GetUser()
        {
            var user = userService.GetUser(GetLoggedUser());
            if (user == null)
                return null;

            var userDTO = new UserDTO
            {
                ProfileImage = user.ProfileImage == null ? string.Empty : Convert.ToBase64String(user.ProfileImage),
                Username = user.Username,
                FirstName = user.FirstName,
                LastName = user.LastName,
                Email = user.Email,
            };

            return userDTO;
        }

        public string? GetUserEmail()
        {
            return userService.GetUserEmail();
        }

        public Role GetUserRole()
        {
            return userService.GetUserRole();
        }

        public List<UserDTO> GetAllUsers()
        {
            var users = userService.GetAllUsers();

            var allUsers = new List<UserDTO>();
            foreach (var user in users)
            {
                allUsers.Add(new UserDTO
                {
                    Username = user.Username,
                    FirstName= user.FirstName,
                    LastName = user.LastName,
                    ProfileImage = Convert.ToBase64String(user.ProfileImage),
                    Email = user.Email
                });
            }

            return allUsers;
        }

        public Response UpdateUser(UserDTO user)
        {
            return userService.UpdateUser(user);
        }

        public Response UpdateUserRole(string userName, string roleName)
        {
            return userService.UpdateUserRole(userName, roleName); 
        }

        public Task<bool> UserAlreadyExists(string username)
        {
            return userService.UserAlreadyExists(username);
        }

        public Response ChangeUserPassword(CredentialsDTO credentials)
        {
            return userService.ChangeUserPassword(credentials);
        }
    }
}
