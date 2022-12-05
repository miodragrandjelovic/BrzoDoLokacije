using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface IFollowService
    {
        public List<User> GetFollowers(string username);
        public List<User> GetFollowing(string username);
        public Response DeleteFollow(string usernameFollower, string usernameFollowing);
        public Response AddFollow(string usernameFollower, string usernameFollowing);
        public Response IsFollowed(string followerUsername, string followingUsername);

        public List<User> SearchFollowers(string username, string search);
        public List<User> SearchFollowing(string username, string search); 
    }
}
