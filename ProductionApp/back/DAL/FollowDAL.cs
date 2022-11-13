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

        public bool AddFollow(string followerUsername, string followingUsername)
        {
            try
            {
                User follower = null;
                User following = null;

                if (followingUsername.Equals(followerUsername))
                {
                    Console.WriteLine("Same follower and following username"); 
                    return false; 
                }

                Follow follow = GetFollow(followerUsername, followingUsername, out follower, out following);
                if(follow != null)
                {
                    Console.WriteLine("Follow exists");
                    return false; 
                }
                follow = new Follow
                {
                    Follower = follower,
                    Following = following
                };
                _context.Follow.Add(follow);
                _context.SaveChanges();
                return true; 
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message); 
                return false; 
            }
        }

        public bool DeleteFollow(string followerUsername, string followingUsername)
        {
            try
            {
                if (followingUsername.Equals(followerUsername))
                {
                    Console.WriteLine("Same follower and following username");
                    return false;
                }

                Follow follow = GetFollow(followerUsername, followingUsername);
                if (follow == null)
                {
                    Console.WriteLine("Follow doesn't exist");
                    return false;
                }

                _context.Follow.Remove(follow);
                _context.SaveChanges();
                return true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return false;
            }
        }

        public Follow GetFollow(string followerUsername, string followingUsername, out User follower, out User following)
        {
            var _follower = _iUserDAL.GetUser(followerUsername);
            var _following = _iUserDAL.GetUser(followingUsername);

            if ((_follower == null) || (_following == null))
                throw new Exception("Follower or Following username not found");

            follower = _follower; 
            following = _following;
            return _context.Follow.Where(follow => (follow.FollowerId == _follower.Id) && (follow.FollowingId == _following.Id))
                                  .FirstOrDefault();
        }

        public Follow GetFollow(string followerUsername, string followingUsername)
        {
            var _follower = _iUserDAL.GetUser(followerUsername);
            var _following = _iUserDAL.GetUser(followingUsername);

            if ((_follower == null) || (_following == null))
                throw new Exception("Follower or Following username not found");

            return _context.Follow.Where(follow => (follow.Follower.Username.Equals(followerUsername)) && (follow.Following.Username.Equals(followingUsername)))
                                  .FirstOrDefault(); 
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
