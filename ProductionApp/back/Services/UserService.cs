using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.JWTManager.Interfaces;
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
        private readonly IJWTManagerRepository jwtManager;

        public UserService(IUserDAL userDAL, IHttpContextAccessor httpContextAccessor, IRoleDAL roleDAL,IEncryptionManager manager,IJWTManagerRepository jwtManager)
        {
            this.userDAL = userDAL;
            this.httpContextAccessor = httpContextAccessor;
            this.roleDAL = roleDAL;
            this.manager = manager;
            this.jwtManager = jwtManager;
        }
        public Response AddNewUser(User user)
        {
            var succeed = userDAL.AddNewUser(user);
            if (!succeed)
                return new Response
                {
                    StatusCode = StatusCodes.Status400BadRequest,
                    Message = "Error while creating user!"
                };
            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = "User added succesffuly!"
            };
        }

        public Response ChangeUserPassword(CredentialsDTO credentials)
        {
            var loggedUser = GetUser(GetLoggedUser());
            // Proverava da li korisnik postoji
            if (loggedUser == null)
                return new Response
                {
                    StatusCode = StatusCodes.Status404NotFound,
                    Message = "User not found!"
                };
            byte[] newPasswordHash, newPasswordSalt;


            // Proverava da li je validna stara lozinka
            if (!manager.DecryptPassword(credentials.OldPassword, loggedUser.Password, loggedUser.PasswordKey))
                return new Response
                {
                    StatusCode = StatusCodes.Status403Forbidden,
                    Message = "Wrong password!"
                };
            // Proverava da li je nova lozinka ista kao stara
            if (manager.DecryptPassword(credentials.NewPassword,loggedUser.Password,loggedUser.PasswordKey))
                return new Response
                {
                    StatusCode= StatusCodes.Status400BadRequest,
                    Message = "Password is same like old password!"
                };

            manager.EncryptPassword(credentials.NewPassword, out newPasswordHash, out newPasswordSalt);
            loggedUser.Password = newPasswordHash;
            loggedUser.PasswordKey = newPasswordSalt;

            var succeed = userDAL.UpdateUser(loggedUser);

            if (!succeed)
                return new Response
                {
                    StatusCode = StatusCodes.Status400BadRequest,
                    Message = "Error while updating password!"
                };


            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = "Password updated successfuly"
            };
        }

        public List<User> GetAllUsers()
        {
            return userDAL.GetAllUsers(GetUser(GetLoggedUser()));
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

        public Response UpdateUser(UserDTO user)
        {
            var loggedUser = userDAL.GetUser(GetLoggedUser());
            if(UserAlreadyExists(user.Username).Result)
            {
                return new Response
                {
                    StatusCode = StatusCodes.Status403Forbidden,
                    Message = "Username already taken!"
                };
            }
            if (loggedUser == null)
                return new Response
                {
                    StatusCode = StatusCodes.Status404NotFound,
                    Message = "User not found!"
                };
            if (!manager.DecryptPassword(user.Password, loggedUser.Password, loggedUser.PasswordKey))
                return new Response
                {
                    StatusCode = StatusCodes.Status403Forbidden,
                    Message = "Wrong password!"
                };

            loggedUser.ProfileImage = Convert.FromBase64String(user.ProfileImage);
            loggedUser.Username = user.Username;
            loggedUser.FirstName = user.FirstName;
            loggedUser.LastName = user.LastName;
            loggedUser.Email = user.Email;

            var response = userDAL.UpdateUser(loggedUser);
            if (!response)
                return new Response
                {
                    StatusCode = StatusCodes.Status400BadRequest,
                    Message = "Error while updating user!"
                };
            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = jwtManager.GenerateToken(loggedUser)
            };
        }

        public Response UpdateUserRole(string userName,string roleName)
        {
            if (GetLoggedUser().Equals(userName))
                return new Response
                {
                    StatusCode = StatusCodes.Status400BadRequest,
                    Message = "Cannot change your own role!"
                };
            var succeed = userDAL.UpdateUserRole(userName, roleName);
            if (!succeed)
                return new Response
                {
                    StatusCode = StatusCodes.Status400BadRequest,
                    Message = "Error while updating user role!"
                };
            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = "User role updated succesffuly!"
            };
        }

        public Task<bool> UserAlreadyExists(string username)
        {
            return userDAL.UserAlreadyExists(username);
        }
    }
}
