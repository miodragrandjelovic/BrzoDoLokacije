using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class FollowService : IFollowService
    {
        public IFollowDAL _iFollowDAL; 

        public FollowService(IFollowDAL iFollowDAL)
        {
            _iFollowDAL = iFollowDAL;
        }

        public List<User> GetFollowers(string username)
        {
            return _iFollowDAL.GetFollowers(username);
        }

        public List<User> GetFollowing(string username)
        {
            return _iFollowDAL.GetFollowing(username);
        }
    }
}
