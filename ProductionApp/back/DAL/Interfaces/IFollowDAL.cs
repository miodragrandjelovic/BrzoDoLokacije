using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IFollowDAL
    {
        List<User> GetFollowers(string username); 
        List<User> GetFollowing(string username); 
    }
}
