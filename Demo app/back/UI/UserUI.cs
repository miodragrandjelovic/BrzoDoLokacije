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
        public void AddNewUser(User user)
        {
            userService.AddNewUser(user);
        }

        public string? GetLoggedUser()
        {
            return userService.GetLoggedUser();
        }

        public string? GetRoleFromLoggedUser()
        {
            return userService.GetRoleFromLoggedUser();
        }

        public User? GetUser(string usernameOrEmail)
        {
            throw new NotImplementedException();
        }

        public string? GetUserEmail()
        {
            return userService.GetUserEmail();
        }

        public Role GetUserRole()
        {
            return userService.GetUserRole();
        }

        public Task<bool> UserAlreadyExists(string username)
        {
            return userService.UserAlreadyExists(username);
        }
    }
}
