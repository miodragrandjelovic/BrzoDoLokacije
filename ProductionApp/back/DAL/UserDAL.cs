using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL
{
    public class UserDAL : IUserDAL
    {
        private readonly Database _context;
        public UserDAL(Database context)
        {
            _context = context; 
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

        public void UpdateUserRole(string username, Role role)
        {
            User user = GetUser(username);
            user.RoleId = role.Id;
            _context.SaveChanges(); 
        }

        public async Task<bool> UserAlreadyExists(string username)
        {
            return await _context.Users.AnyAsync(user => user.Username.Equals(username));
        }
    }
}
