using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Models.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using System.Security.Claims;

namespace PyxisKapriBack.Services
{
    public class UserService : IUserService
    {
        private readonly IUserDAL userDAL;

        private readonly IHttpContextAccessor httpContextAccessor;
        private readonly IRoleDAL roleDAL;
        private readonly IEncryptionManager manager;

        public UserService(IUserDAL userDAL, IHttpContextAccessor httpContextAccessor, IRoleDAL roleDAL,IEncryptionManager manager)
        {
            this.userDAL = userDAL;
            this.httpContextAccessor = httpContextAccessor;
            this.roleDAL = roleDAL;
            this.manager = manager;
        }
        public void AddNewUser(User user)
        {
            userDAL.AddNewUser(user);
        }

        public List<Role> GetAvailableRolesForUser(string user)
        {
            var loggedUser = userDAL.GetUser(user);
            return roleDAL.GetAvailableRolesForUser(loggedUser);
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

        public bool UpdateUser(UserDTO user)
        {
            
            var loggedUser = userDAL.GetUser(GetLoggedUser());

            if (!manager.DecryptPassword(user.Password, loggedUser.Password, loggedUser.PasswordKey))
                return false;

            //loggedUser.ProfileImage = user.ProfileImage
            loggedUser.Username = user.Username;
            loggedUser.FirstName = user.FirstName;
            loggedUser.LastName = user.LastName;
            loggedUser.Email = user.Email;

            var response = userDAL.UpdateUser(loggedUser);
            return response;
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
