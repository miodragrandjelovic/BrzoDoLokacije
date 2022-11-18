using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.DTOComponents;
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

        public Response AddFollow(string usernameFollower, string usernameFollowing)
        {
            bool succeed = _iFollowDAL.AddFollow(usernameFollower, usernameFollowing); 
            return ResponseService.GetResponse(succeed, "Follow added!", "Add follow failed!");
        }

        public Response DeleteFollow(string usernameFollower, string usernameFollowing)
        {
            bool succeed = _iFollowDAL.DeleteFollow(usernameFollower, usernameFollowing);
            return ResponseService.GetResponse(succeed, "Follow deleted", "Delete follow failed!"); 
        }

        public List<User> GetFollowers(string username)
        {
            return _iFollowDAL.GetFollowers(username);
        }

        public List<User> GetFollowing(string username)
        {
            return _iFollowDAL.GetFollowing(username); 
        }
        public Response IsFollowed(string followerUsername, string followingUsername)
        {
            try
            {
                bool succeed = _iFollowDAL.IsFollowed(followerUsername, followingUsername);
                return ResponseService.CreateOkResponse("IsFollowed executed succesfully");
            }
            catch(Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message); 
            }
        }
    }
}
