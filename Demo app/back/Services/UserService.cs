using PyxisKapriBack.DAL.Interfaces;
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

        public User? GetUser(string username)
        {
            return userDAL.GetUser(username);
        }

        public Task<bool> UserAlreadyExists(string username)
        {
            return userDAL.UserAlreadyExists(username);
        }
    }
}
