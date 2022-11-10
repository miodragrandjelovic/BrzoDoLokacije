using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class FollowDAL : IFollowDAL
    {
        private Database _context; 
        public FollowDAL(Database context)
        {
            _context = context;
        }
        // trazimo one naloge gde je zapracen ovaj user 
        public List<Follow> GetFollowers(string username)
        {
            return _context.Follow.Where(follow => follow.Following.Username.Equals(username)).ToList();
        }

        // trazimo one naloge koji su zapraceni od ovog korisnika
        public List<Follow> GetFollowing(string username)
        {
            return _context.Follow.Where(follow => follow.Follower.Username.Equals(username)).ToList();
        }
    }
}
