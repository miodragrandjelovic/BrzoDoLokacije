using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models.DTO_Components;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class UserService : IUserService
    {
        private readonly IUserDAL userDAL;
        private readonly IHttpContextAccessor httpContextAccessor;

        public UserService(IUserDAL userDAL, IHttpContextAccessor httpContextAccessor)
        {
            this.userDAL = userDAL;
            this.httpContextAccessor = httpContextAccessor;
        }
        public void AddNewUser(User user)
        {
            userDAL.AddNewUser(user);
        }

        public string? GetLoggedUser()
        {
            var loggedUser = httpContextAccessor?.HttpContext?.User?.Identity?.Name;

            return loggedUser;
        }

        public User? GetUser(string usernameOrEmail)
        {
            return userDAL.GetUser(usernameOrEmail);
        }

        public UserDTO? GetUserDTO(string usernameOrEmail)
        {
            return userDAL.GetUserDTO(usernameOrEmail);
        }

        public Task<bool> UserAlreadyExists(string username)
        {
            return userDAL.UserAlreadyExists(username);
        }
    }
}
