using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL
{
    public class UserDAL : IUserDAL
    {
        private readonly Database _context;
        private IRoleDAL _roleDAL;
        public UserDAL(Database context, IRoleDAL roleDAL)
        {
            _context = context; 
            _roleDAL = roleDAL;
        }
        public void AddNewUser(User user)
        {
            _context.Users.Add(user);
            _context.SaveChanges();
        }

        public User? GetUser(string usernameOrEmail)
        {
            User user = _context.Users.Where(x => x.Username.Equals(usernameOrEmail) || x.Email.Equals(usernameOrEmail)).Include(x => x.Role)
                                                                                                                        .Include(x => x.Country).FirstOrDefault();
            return user; 
        }

        public bool UpdateUserRole(string username, string roleName)
        {
            Role role = _roleDAL.GetRole(username);
            User user = GetUser(username);
            user.RoleId = role.Id;
            if (_context.SaveChanges() == 1)
                return true;
            return false;
        }

        public async Task<bool> UserAlreadyExists(string username)
        {
            return await _context.Users.AnyAsync(user => user.Username.Equals(username));
        }
    }
}
