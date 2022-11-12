using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface IFollowService
    {
        public List<User> GetFollowers(string username);
        public List<User> GetFollowing(string username); 
    }
}
