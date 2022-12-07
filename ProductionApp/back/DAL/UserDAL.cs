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
                                                                                                                        .Include(x => x.Followers)
                                                                                                                        .Include(x => x.Followers)
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

            if((user == null) || (role == null))
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

        public double GetAverageGradeForAllPosts(string username)
        {
            var posts = _context.Posts.Where(post => post.User.Username.Equals(username)).Select(post => post.Id); 
            if (posts == null)
                return 0;

            var likes = _context.Likes.Where(like => posts.Contains(like.PostId));
            if (likes == null)
                return 0; 

            var sum = likes.Sum(like => like.Grade);
            var count = likes.Count();

            if (sum == 0)
                return 0;
            if (count == 0)
                return 0;
            return sum / count;
        }

        public int GetDifferentLocations(string username)
        {
            int countLocations = _context.Posts.Where(post => post.User.Username.Equals(username))
                                          .Select(post => post.FullLocation)
                                          .Distinct()
                                          .Count();
            return countLocations; 
        }
    }
}
