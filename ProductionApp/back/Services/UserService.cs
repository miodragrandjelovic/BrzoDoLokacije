using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using System.Security.Claims;

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
            var loggedUser = httpContextAccessor?.HttpContext?.User?.Identity?.Name;

            return loggedUser;
        }

        public string? GetRoleFromLoggedUser()
        {
            var role = httpContextAccessor.HttpContext?.User?.Claims?.Where(c => c.Type == ClaimTypes.Role).FirstOrDefault().Value;

            return role;
        }

        public User? GetUser(string usernameOrEmail)
        {
            return userDAL.GetUser(usernameOrEmail);
        }

        public string? GetUserEmail()
        {
            var userEmail = httpContextAccessor?.HttpContext?.User?.Claims.Where(c => c.Type == ClaimTypes.Email).FirstOrDefault().Value;

            return userEmail;
        }

        public Role GetUserRole()
        {
            return roleDAL.GetUserRole();
        }

        public bool UpdateUserRole(string userName,string roleName)
        {   
            return userDAL.UpdateUserRole(userName, roleName);
        }

        public Task<bool> UserAlreadyExists(string username)
        {
            return userDAL.UserAlreadyExists(username);
        }
    }
}
