using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class UserService : IUserService
    {
        private readonly IUserDAL userDAL;

        private readonly IHttpContextAccessor httpContextAccessor;
        private readonly IRoleDAL roleDAL;

        public UserService(IUserDAL userDAL, IHttpContextAccessor httpContextAccessor, IRoleDAL roleDAL)
        {
            this.userDAL = userDAL;
            this.httpContextAccessor = httpContextAccessor;
            this.roleDAL = roleDAL;
        }
        public void AddNewUser(User user)
        {
            userDAL.AddNewUser(user);
        }

        public string? GetLoggedUser()
        {
            var loggedUser = httpContextAccessor?.HttpContext?.User?.Claims.Where(c => c.Type == Constants.Constants.USERNAME).FirstOrDefault();

            return loggedUser.Value;
        }

        public string? GetRoleFromLoggedUser()
        {
            var role = httpContextAccessor.HttpContext.User.Claims.Where(c => c.Type == Constants.Constants.ROLE).FirstOrDefault();

            return role.Value;
        }

        public User? GetUser(string usernameOrEmail)
        {
            return userDAL.GetUser(usernameOrEmail);
        }

        public string? GetUserEmail()
        {
            var userEmail = httpContextAccessor?.HttpContext?.User?.Claims.Where(c => c.Type == Constants.Constants.EMAIL).FirstOrDefault();

            return userEmail.Value;
        }

        public Role GetUserRole()
        {
            return roleDAL.GetUserRole();
        }

        public Task<bool> UserAlreadyExists(string username)
        {
            return userDAL.UserAlreadyExists(username);
        }
    }
}
