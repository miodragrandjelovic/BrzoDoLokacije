using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class UserService : IUserService
    {
        private readonly IUserDAL _userDAL;

        public UserService(IUserDAL userDAL)
        {
            _userDAL = userDAL;
        }
        public void AddNewUser(User user)
        {
            _userDAL.AddNewUser(user);
        }

        public User? GetUser(string username)
        {
            return _userDAL.GetUser(username);
        }

        public Task<bool> UserAlreadyExists(string username)
        {
            return _userDAL.UserAlreadyExists(username);
        }
    }
}
