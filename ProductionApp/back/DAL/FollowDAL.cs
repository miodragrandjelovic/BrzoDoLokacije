using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class FollowDAL : IFollowDAL
    {
        private Database _context; 
        private IUserDAL _iUserDAL;
        public FollowDAL(Database context, IUserDAL userDAL)
        {
            _context = context;
            _iUserDAL = userDAL;
        }
        // trazimo one naloge gde je zapracen ovaj user 
        public List<User> GetFollowers(string username)
        {
            User user = _iUserDAL.GetUser(username);
            if (user == null)
                return null;
            return _context.Follow.Where(follow => follow.FollowingId == user.Id)
                                                   .Include(follow => follow.Follower)
                                                   .Select(follow => follow.Follower)
                                                   .ToList();
           // return _context.Follow.Where(follow => follow.Following.Username.Equals(username)).ToList();
        }

        // trazimo one naloge koji su zapraceni od ovog korisnika
        public List<User> GetFollowing(string username)
        {
            User user = _iUserDAL.GetUser(username);
            if (user == null)
                return null;
            return _context.Follow.Where(follow => follow.FollowerId == user.Id)
                                                   .Include(follow => follow.Following)
                                                   .Select(follow => follow.Following)
                                                   .ToList();
            //return _context.Follow.Where(follow => follow.Follower.Username.Equals(username)).ToList();
        }
    }
}
