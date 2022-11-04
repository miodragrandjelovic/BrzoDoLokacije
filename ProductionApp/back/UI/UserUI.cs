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
                ProfileImage = null,
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

        public Response UpdateUser(UserDTO user)
        {
            var response = new Response();
            var succeed = userService.UpdateUser(user);

            if (!succeed)
            {
                response.StatusCode = StatusCodes.Status400BadRequest;
                response.Message = "Error updating user!";
            }
            else
            {
                response.StatusCode = StatusCodes.Status200OK;
                response.Message = "User update succesffuly!";
            }

            return response;

        }

        public Response UpdateUserRole(string userName, string roleName)
        {
            var response = new Response();
            var succeed = userService.UpdateUserRole(userName,roleName);
            if (!succeed)
            {
                response.StatusCode = StatusCodes.Status400BadRequest;
                response.Message = "Cannot update user role!";
            }
            else
            {
                response.StatusCode = StatusCodes.Status200OK;
                response.Message = "User role update succesffuly!";
            }

            return response;
        }

        public Task<bool> UserAlreadyExists(string username)
        {
            return userService.UserAlreadyExists(username);
        }
    }
}
