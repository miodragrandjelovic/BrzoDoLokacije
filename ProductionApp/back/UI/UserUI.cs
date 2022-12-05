using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class UserUI : IUserUI
    {
        private readonly IUserService userService;
        private readonly IFileService fileService;

        public UserUI(IUserService userService,IFileService fileService)
        {
            this.userService = userService;
            this.fileService = fileService;
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
                Username = user.Username,
                FirstName = user.FirstName,
                LastName = user.LastName,
                Email = user.Email,
                ProfileImagePath = Path.Combine(user.FolderPath, user.FileName),
                UserFollowersCount = user.Followers != null ? user.Followers.Count : 0,
                UserFollowingCount = user.Following != null ? user.Following.Count : 0,
            };

            return userDTO;
        }

        public UserDTO GetUser(string username)
        {
            var user = userService.GetUser(username);
            if (user == null)
                return null;

            var userDTO = new UserDTO
            {
                Username = user.Username,
                FirstName = user.FirstName,
                LastName = user.LastName,
                Email = user.Email,
                ProfileImagePath = Path.Combine(user.FolderPath, user.FileName),
                UserFollowersCount = user.Followers != null ? user.Followers.Count : 0,
                UserFollowingCount = user.Following != null ? user.Following.Count : 0,
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
                    FirstName = user.FirstName,
                    LastName = user.LastName,
                    Email = user.Email,
                    ProfileImagePath = Path.Combine(user.FolderPath, user.FileName)
                }) ;
            }

            return allUsers;
        }

        public Response UpdateUser(UpdateUserDataDTO user)
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

        public Response UpdateProfileImage(UpdateUserImageDTO profileImage)
        {
            return userService.UpdateProfileImage(profileImage);
        }
    }
}
