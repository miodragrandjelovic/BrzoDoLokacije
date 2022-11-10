using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IFollowDAL
    {
        List<Follow> GetFollowers(string username); 
        List<Follow> GetFollowing(string username); 
    }
}
