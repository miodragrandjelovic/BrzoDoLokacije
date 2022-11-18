using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IFollowUI
    {
        public List<FollowDTO>? GetFollowers(); 
        public List<FollowDTO>? GetFollowing();
        Response AddFollow(string followingUsername);
        Response DeleteFollow(string followingUsername);
        Response IsFollowed(string followingUsername);
    }
}
