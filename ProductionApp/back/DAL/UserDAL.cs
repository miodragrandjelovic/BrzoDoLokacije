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
        public bool AddNewUser(User user)
        {
            if (GetUser(user.Username) != null)
                return false;
            if (user == null)
                return false;
            _context.Users.Add(user);
            _context.SaveChanges();
            return true;
        }

        public User? GetUser(string usernameOrEmail)
        {
            User user = _context.Users.Where(x => x.Username.Equals(usernameOrEmail) || x.Email.Equals(usernameOrEmail)).Include(x => x.Role)
                                                                                                                        .Include(x => x.Country)
                                                                                                                        .FirstOrDefault();
            return user;
        }

        public bool UpdateUser(User user)
        {
            if (user == null)
                return false;
            _context.Users.Update(user);
            _context.SaveChanges();
            return true;
        }

        public bool DeleteUser(int userID)
        {
            User user = GetUser(userID);
            if (user != null)
                return false;

            _context.Users.Remove(user);
            _context.SaveChanges();
            return true;
        }
        public bool UpdateUserRole(string username, string roleName)
        {
            Role role = _roleDAL.GetRole(roleName);
            User user = GetUser(username);

            if ((user == null) || (role == null))
                return false;
            user.RoleId = role.Id;
            return UpdateUser(user);
        }

        public async Task<bool> UserAlreadyExists(string username)
        {
            return await _context.Users.AnyAsync(user => user.Username.Equals(username));
        }

        public User GetUser(int userID)
        {
            return _context.Users.Where(user => user.Id == userID).FirstOrDefault();
        }

        public List<User> GetAllUsers(User user)
        {
            return _context.Users.Where(u => u.Id != user.Id).ToList();
        }

        public List<Connection> GetUserConnections(string username)
        {
            var user = _context.Users.Where(user => user.Username.Equals(username)).Include(user => user.Connections).FirstOrDefault();

            return user.Connections.ToList();
        }

        public Connection GetConnectionById(string connectionId)
        {
            return _context.Connections.Where(connection => connection.Equals(connectionId)).FirstOrDefault();
        }

        public bool AddNewConnection(Connection connection)
        {
            _context.Connections.Add(connection);
            int num = _context.SaveChanges();
            if(num > 0)
                return true;

            return false;

        }
    }
}
